

package com.boundless.benchmark.jmeter;

import com.boundless.benchmark.IBenchmarkComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom Ingold
 */
public class JMeterRunner implements IBenchmarkComponent{
    
    final static Logger logger = LoggerFactory.getLogger(JMeterRunner.class);
    
    private String jmeterLocation;
    
    private String propertiesFile;
    
    private String testFile;
    
    private final static String id = "jemeterrunner";
    
    private void runJMeter() throws InterruptedException, IOException
    {
        String jmeter = jmeterLocation+File.separator+"apache-jmeter-2.11"+File.separator+"bin"+File.separator;
        String os = System.getProperty("os.name");
        if(os.contains("windows"))
        {
            jmeter += "jmeter.bat";
        }
        else
        {
            jmeter += "jmeter.sh";
        }
        
        File test = new File(testFile);
        File jm = new File(jmeter);
        //just load the jmeter specific properties and set them on the command line
        //for some reason the referencing the property file isn't working
        StringBuilder command = new StringBuilder(jm.getAbsolutePath()).append(" -n  -t ").append(test.getAbsoluteFile());
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFile));
        List<String> args = new ArrayList<String>();
        
        for(Object obj: props.keySet())
        {
            String key  = (String)obj;
            if(key.startsWith("ps.jmeter"))
            {
                command.append(" -J").append(key).append("=").append(props.getProperty(key));                        
            }
        }

        logger.info("Running JMeter tests....");
        /*Process jproc = new ProcessBuilder(command.toString())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start();        
        */
        Process jproc = Runtime.getRuntime().exec(command.toString());
        
        int result = jproc.waitFor();
        if(result != 0)
        {
            logger.error("JMeter execution failed with code {}", result);
        }
        
    }

    public String getId() 
    {
    
        return id;
        
    }

    public Object process() throws Exception 
    {
        this.runJMeter();
        return null;
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

    /**
     * @return the testFile
     */
    public String getTestFile() {
        return testFile;
    }

    /**
     * @param testFile the testFile to set
     */
    public void setTestFile(String testFile) {
        this.testFile = testFile;
    }
    
}
