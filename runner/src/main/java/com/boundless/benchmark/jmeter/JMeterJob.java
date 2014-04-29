/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boundless.benchmark.jmeter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Wrapper class to run JMeter Process on another thread
 *
 * @author Tom
 */
public class JMeterJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(JMeterJob.class);

    private ProducerTemplate template;

    private String args;
    private List<String> argList;

    public void run() {
        String id = UUID.randomUUID().toString();
        logger.info("Setting up job {}", id);
        try {
            template.sendBodyAndHeader("seda:jobStatus", id, "status", JobStatus.STARTED);
            logger.info(args);

            ProcessBuilder pb = new ProcessBuilder();
            pb.command(argList);
            pb.inheritIO();
            Process jproc = pb.start();
            //int result = jproc.waitFor();            

            while (!Thread.interrupted()) {
                Thread.sleep(2000);
                try {
                    
                    this.eatStream(jproc.getErrorStream());
                    this.eatStream(jproc.getInputStream());
                    
                    int result = jproc.exitValue();

                    if (result != 0) {
                        logger.error("JMeter execution failed with code {}", result);
                        template.sendBodyAndHeader("seda:jobStatus", id, "status", JobStatus.FAILED);
                        return;
                    } else {

                        template.sendBodyAndHeader("seda:jobStatus", id, "status", JobStatus.COMPLETED);
                        return;
                    }
                } catch (Exception ex) 
                {
                    template.sendBodyAndHeader("seda:jobStatus", id, "status", JobStatus.RUNNING);
                }
            }
        } catch (Exception ex) {
            logger.error("Exception running JMeter job: {}", ex.getMessage());
            ex.printStackTrace();
            template.sendBodyAndHeader("seda:jobStatus", id, "status", JobStatus.FAILED);

        }

    }

    private void eatStream(InputStream stream)
    {
        try{
        while(stream.read() != -1)
        {
            ///nom nom nom
        }
        }
        catch(IOException ex)
        {
            //don't care
        }
        
    }
    
    /**
     * @return the argList
     */
    public List<String> getArgList() {
        return argList;
    }

    /**
     * @param argList the argList to set
     */
    public void setArgList(List<String> argList) {
        this.argList = argList;
    }

    public enum JobStatus {

        UNKNOWN,
        STARTED,
        RUNNING,
        COMPLETED,
        FAILED;
    }

    /**
     * @return the template
     */
    public ProducerTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(ProducerTemplate template) {
        this.template = template;
    }

    /**
     * @return the args
     */
    public String getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(String args) {
        this.args = args;
    }

}
