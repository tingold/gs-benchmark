

package com.boundless.benchmark.data;

import com.boundless.benchmark.data.results.SimpleStatistic;
import com.boundless.benchmark.data.results.SingleTestOutput;
import com.boundless.benchmark.data.results.TestRunOutput;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Tom
 */
public class ResultProcessor extends TimerTask
{
    private static final Logger logger = LoggerFactory.getLogger(ResultProcessor.class);
    private final Map<String,Map<String,SummaryStatistics>> statMap = new HashMap<String,Map<String,SummaryStatistics>>();
    private final Map<String, AtomicInteger> errorMap = new HashMap<String, AtomicInteger>();
    private final Map<String, AtomicInteger> successMap = new HashMap<String, AtomicInteger>();    
    private final Map<String, AtomicInteger> dataTypes = new HashMap<String, AtomicInteger>();
    private final Map<String, Map<String,AtomicInteger>> dataTypeMap = new HashMap<String, Map<String,AtomicInteger>>();
    private final Map<String, SingleTestOutput> outputMap = new HashMap<String, SingleTestOutput>();
    
    @Produce
    private ProducerTemplate template;
    
    //for keeping track
    private final AtomicInteger messageCount = new AtomicInteger(0);
    private long lastRecieved = 0;
    
    
    //accross all tests   
    private final AtomicInteger totalSuccesses = new AtomicInteger(0);
    private final AtomicInteger totalErrors = new  AtomicInteger(0);
    //the time the tests started and stoped in unix time accross all tests
    //as reported by JMeter
    //we can use these to figure out the total number of requests execute in a
    //given amount of time
    private long startTime = 0;
    private long stopTime = 0;
    
    
    private final Timer timer;
    
    public ResultProcessor()
    {
    
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 10000, 10000);
                
    }
       
    
    
    public synchronized void processStat(List<JTFLine> lines)
    {
        for(JTFLine line: lines)
        {
            processStat(line);
        }
    }       
    
    public synchronized void processStat(JTFLine line)  
    {
        
        //process times               
        this.lastRecieved = System.currentTimeMillis();
        if(startTime == 0)
        {
            startTime = line.getTimestamp();
        }
        else
        {
            //if we have an earlier time than the one we just got, do nothing
            //if it's older, we keep it
            if(startTime > line.getTimestamp())
            {
                startTime = line.getTimestamp();
            }            
        }    
        if(stopTime == 0)
        {
            stopTime = line.getTimestamp();
        }
        else
        {
            if(stopTime < line.getTimestamp())
            {
                stopTime = line.getTimestamp();
            }
        }
        
        //counting
        messageCount.incrementAndGet();
        if(messageCount.intValue()%1000 == 0)
        {
            logger.info("Processed {} stats", messageCount);
            
        }
        logger.debug("Got stat");
        if(!statMap.containsKey(line.getLabel()))
        {
            this.initMaps(line.getLabel());
        }
        Map<String,SummaryStatistics> subMap = statMap.get(line.getLabel());
       
        
        if(line.getSuccess())
        {
            this.totalSuccesses.incrementAndGet();
            this.successMap.get(line.getLabel()).incrementAndGet();
        }
        else
        {
            this.totalErrors.incrementAndGet();
            this.errorMap.get(line.getLabel()).incrementAndGet();
        }
        //master data type map
        if(!dataTypes.containsKey(line.getDataType()))
        {
            dataTypes.put(line.getDataType(),new AtomicInteger(0));
        }
        dataTypes.get(line.getDataType()).getAndIncrement();
        //sub map type
        if(!dataTypeMap.containsKey(line.getLabel()))
        {
            dataTypeMap.put(line.getLabel(), new HashMap<String,AtomicInteger>());
        }
        if(!dataTypeMap.get(line.getLabel()).containsKey(line.getDataType()))
        {
            dataTypeMap.get(line.getLabel()).put(line.getDataType(), new AtomicInteger());
        }
        dataTypeMap.get(line.getLabel()).get(line.getDataType()).incrementAndGet();
        
        
        subMap.get("elapsed").addValue(line.getElapsedTime());
        subMap.get("size").addValue(line.getByteSize());
        subMap.get("latency").addValue(line.getLatency());
        outputMap.get(line.getLabel()).addDate(line.getTimestamp());
        
     
    }
    
    
    private void initMaps(String key)
    {
         //create all the stats for a given key
        Map<String, SummaryStatistics> subMap = new HashMap<String, SummaryStatistics>();        
        subMap.put("elapsed", new SummaryStatistics());
        subMap.put("size", new SummaryStatistics());
        subMap.put("latency", new SummaryStatistics());
        statMap.put(key, subMap);
        successMap.put(key, new AtomicInteger(0));
        errorMap.put(key, new AtomicInteger(0));
        outputMap.put(key, new SingleTestOutput());
    }

    
    public void printStats()
    {
        Gson gson = new GsonBuilder().serializeNulls()
                .setDateFormat(DateFormat.FULL)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .serializeSpecialFloatingPointValues()
                .setPrettyPrinting()
                .create();
    
        TestRunOutput tro = new TestRunOutput();
        tro.setTestRunCompleted(new Date());
        tro.setTestDuration(Math.abs(startTime-stopTime));
        tro.setTotalTests(this.statMap.size());
        tro.setTotalRequests(this.totalErrors.intValue()+this.totalSuccesses.intValue());
        tro.setTotalErrors(this.totalErrors.intValue());
        tro.setTotalSuccesses(this.totalSuccesses.intValue());
        tro.setDataTypes(this.dataTypes);
        
        for(String key: statMap.keySet())
        {
            Map<String, SummaryStatistics> subMap = statMap.get(key);
        
            SingleTestOutput ro = outputMap.get(key);
            ro.setName(key);
            ro.getRequests().setErrors(this.errorMap.get(key).intValue());
            ro.getRequests().setSuccesses(this.successMap.get(key).intValue());
            ro.setDataFormats(this.dataTypeMap.get(key));
            
            SimpleStatistic elapsed = new SimpleStatistic();
            elapsed.setName("elapsed");
            elapsed.setMean(subMap.get("elapsed").getMean());
            elapsed.setMax(subMap.get("elapsed").getMax());
            elapsed.setMin(subMap.get("elapsed").getMin());
            elapsed.setSdev(subMap.get("elapsed").getStandardDeviation());
            elapsed.setSampleSize(subMap.get("elapsed").getN());
            
            SimpleStatistic latency = new SimpleStatistic();
            latency.setName("latency");
            latency.setMean(subMap.get("latency").getMean());
            latency.setMax(subMap.get("latency").getMax());
            latency.setMin(subMap.get("latency").getMin());
            latency.setSdev(subMap.get("latency").getStandardDeviation());
            latency.setSampleSize(subMap.get("latency").getN());
            
            SimpleStatistic size = new SimpleStatistic();
            size.setName("size");
            size.setMean(subMap.get("size").getMean());
            size.setMax(subMap.get("size").getMax());
            size.setMin(subMap.get("size").getMin());
            size.setSdev(subMap.get("size").getStandardDeviation());
            size.setSampleSize(subMap.get("size").getN());
            
            ro.getStats().add(size);
            ro.getStats().add(elapsed);
            ro.getStats().add(latency);
            
            tro.getTests().add(ro);
        }
        
        File output = new File("output.js");
        String json = gson.toJson(tro);
        try
        {
            FileOutputStream fos = new FileOutputStream(output);
            String jsonp = "loadData("+  json + ");";
            fos.write(jsonp.getBytes());
            fos.flush();
            fos.close();
            if(Desktop.isDesktopSupported())
            {
                File html = new File("../viewer/resultTemplate.html");
                Desktop.getDesktop().browse(html.getCanonicalFile().toURI());
            }
        }
        catch(IOException ex)
        {
            logger.error("Error writing results to file: {}", ex.getMessage());
            logger.error("printing to log file instead");
            logger.error(json);
        }
        
    }

    /**
     * This function, called from a timer, will check if it 
     * has been more than 10 seconds since the last message was processed.
     * If so, it will initiate printing of the stats and then the camel context 
     * shutdown 
     */
    @Override
    public void run() 
    {
        logger.info("Checking...");
        if(lastRecieved != 0)
        {
            long delta = System.currentTimeMillis() - lastRecieved;
            if(delta > 10000)
            {
                logger.warn("It's been 10 seconds since last message recieved, initiating shutdown");
                this.printStats();
                template.sendBody("seda:cleanUp","Cleanup Time!");
                timer.cancel();
            }
        }
    }
    
    
      
}
