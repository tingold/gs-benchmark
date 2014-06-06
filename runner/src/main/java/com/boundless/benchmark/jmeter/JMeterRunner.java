package com.boundless.benchmark.jmeter;

import com.boundless.benchmark.IBenchmarkComponent;
import com.boundless.benchmark.data.DataFinder;
import com.boundless.benchmark.data.DataPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom Ingold
 */
public class JMeterRunner implements IBenchmarkComponent, CamelContextAware {

    final static Logger logger = LoggerFactory.getLogger(JMeterRunner.class);

    private String jmeterLocation;

    private String propertiesFile;

    private CamelContext cc;

    private final static String id = "jemeterrunner";

    public void process(Exchange exchng) throws Exception {

        List<DataPackage> cache = DataFinder.getCache();
        logger.info("looking at {} cached data packages", cache.size());
        for (DataPackage dp : cache) {
            logger.info("looking through dataPackage {}", dp.getName());
            if (dp.getEnabled()) {
                logger.info("dataPackage {} is enabled", dp.getName());
                for (File file : dp.getTests()) {
                    logger.info("loading file {}", file.getName());
                    this.runJMeter(file);
                }
            } else {
                logger.info("Skipping {} as it is marked 'disabled'", dp.getName());
            }

        }
    }

    private void runJMeter(File testfile) throws InterruptedException, IOException {
        String jmeter = jmeterLocation + File.separator + "apache-jmeter-2.11" + File.separator + "bin" + File.separator;
        String os = System.getProperty("os.name");
        if (os.contains("windows")) {
            jmeter += "jmeter.bat";
        } else {
            jmeter += "jmeter.sh";
        }

        File jm = new File(jmeter);
        //just load the jmeter specific properties and set them on the command line
        //for some reason referencing the property file isn't working
        //make sure its executable
        jm.setExecutable(true);

        List<String> args = new ArrayList<String>();
        args.add(jm.getCanonicalPath());
        args.add("-n");
        args.add("-t");
        args.add(testfile.getAbsolutePath());

        //StringBuilder command = new StringBuilder(jm.getCanonicalPath()).append(" -n  -t ").append(testfile.getAbsoluteFilePath());
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFile));

        for (Object obj : props.keySet()) {
            String key = (String) obj;
            if (key.startsWith("ps.jmeter")) {
                //command.append(" -J").append(key).append("=").append(props.getProperty(key));
                args.add("-J" + key + "=" + props.getProperty(key));
            }
        }
        //command.append(" -J").append("ps.report.name=").append(String.valueOf(System.currentTimeMillis())).append(".jtl");
        
        //args.add("-J"+"ps.report.name="+outfileName+".jtl");
        args.add("-Jjmeter.save.saveservice.data_type=true");
        args.add("-Jjmeter.save.saveservice.label=true");
        args.add("-Jjmeter.save.saveservice.response_code=true");
        args.add("-Jjmeter.save.saveservice.response_data=true");
        args.add("-Jjmeter.save.saveservice.response_message=true");
        args.add("-Jjmeter.save.saveservice.successful=true");
        args.add("-Jjmeter.save.saveservice.thread_name=true");
        args.add("-Jjmeter.save.saveservice.time=true");
        args.add("-Jjmeter.save.saveservice.response_data.on_error=true");

        args.add("-l");

        JMeterJob job = new JMeterJob();
        String outfileName = testfile.getName()+job.getId()+".jtl";
        args.add(outfileName);
        

        
        job.setArgList(args);
        //job.setArgs(command.toString());
        job.setTemplate(cc.createProducerTemplate());

        Thread thread = new Thread(job);
        thread.start();
        logger.info("Started JMeter job on thread {}", thread.getName());

    }

    public String getId() {

        return id;

    }

    /**
     * @return the jmeterLocation
     */
    public String getJmeterLocation() {
        return jmeterLocation;
    }

    /**
     * @param jmeterLocation the jmeterLocation to set
     */
    public void setJmeterLocation(String jmeterLocation) {
        this.jmeterLocation = jmeterLocation;
    }

    /**
     * @return the propertiesFile
     */
    public String getPropertiesFile() {
        return propertiesFile;
    }

    /**
     * @param propertiesFile the propertiesFile to set
     */
    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public void setCamelContext(CamelContext cc) {
        this.cc = cc;
    }

    public CamelContext getCamelContext() {
        return cc;
    }

}
