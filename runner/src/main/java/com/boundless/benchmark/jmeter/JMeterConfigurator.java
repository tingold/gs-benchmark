/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.jmeter;

import com.boundless.benchmark.AbstractBenchmarkComponent;
import java.io.File;
import java.io.FilenameFilter;
import net.lingala.zip4j.core.ZipFile;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tom Ingold
 */
public class JMeterConfigurator extends AbstractBenchmarkComponent{

    private static final Logger logger = LoggerFactory.getLogger(JMeterConfigurator.class);
    
    private String jmeterLocation;
    
    /**
     * Checks to make sure that  
     * @return
     * @throws Exception 
     */
    @Override
   public void process(Exchange exchange) throws Exception {
    
       logger.debug("Getting called!");
       
        exchange.setOut(exchange.getIn());
        exchange.getOut().copyFrom(exchange.getIn());
        
        File jm = new File(jmeterLocation+File.separator+"apache-jmeter-2.11"+File.separator+"bin"+File.separator+"jmeter");
        if(jm.exists())
        {
            return;
        }
        File f = new File(jmeterLocation);
        
        
        if(f.isDirectory())
        {
            File[] dirContents = f.listFiles();
            if(dirContents.length == 0)
            {
                logger.error("Looks like out JMeter dir is empty...throwing exception!");
                throw new Exception("Can't find JMeter!");
            }
            String[] jmeter = f.list(new JMeterFilter());
            if(jmeter.length != 0)
            {
                logger.info("JMeter zip: {}",jmeter[0]);
                ZipFile zip = new ZipFile(jmeterLocation+File.separator+jmeter[0]);                
                zip.extractAll(jmeterLocation);
            
            }
        }        
 
    }

    /**
     * @return the jmeterDir
     */
    public String getJmeterLocation() {
        return jmeterLocation;
    }

    /**
     * @param jmeterLocation
     */
    public void setJmeterLocation(String jmeterLocation) {
        this.jmeterLocation = jmeterLocation;
    }

   
    
    class JMeterFilter implements FilenameFilter
    {

        public boolean accept(File dir, String name) 
        {
             return name.equalsIgnoreCase("apache-jmeter-2.11.zip");            
        }
    
    }
    
}
