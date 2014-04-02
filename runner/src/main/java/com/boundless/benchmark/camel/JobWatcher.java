/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.camel;

import com.boundless.benchmark.jmeter.JMeterJob;
import com.boundless.benchmark.jmeter.JMeterJob.JobStatus;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.InOnly;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class watches to make sure that every JMeter job that starts is finished 
 * before firing the processingCompleted event
 * 
 * @author Tom Ingold
 */
public class JobWatcher 
{
    private static final Logger logger = LoggerFactory.getLogger(JobWatcher.class);
    
    @org.apache.camel.Produce
    private ProducerTemplate template;
    
    private final Map<String, JMeterJob.JobStatus> statusMap = new HashMap<String, JMeterJob.JobStatus>();
    
    @InOnly
    public void process(Exchange exchange)
    {
        
        
        String id = exchange.getIn().getBody().toString();
        
        JobStatus status = JobStatus.valueOf(exchange.getIn().getHeader("status", String.class));
        logger.info("Got Message from job {} with status {}", id, status.toString());
        
        statusMap.put(id, status);
               
        logger.info("Status:");
        for(String key: statusMap.keySet())
        {
            logger.info("Job: {}, Status: {}", key, statusMap.get(key));
        }
        
        //if all jobs are complete we fire the processingCompleted event
        if(allComplete())
        {            
            logger.info("All jobs completed...");
            template.sendBody("seda:processingComplete", "All jobs completed!");
        }
        
    }
    
    private boolean allComplete()
    {
        if(statusMap.size() == 0){return false;}
        
        for(String key: statusMap.keySet())
        {
           if(statusMap.get(key) == JobStatus.COMPLETED || statusMap.get(key) == JobStatus.FAILED)
           {
               continue;
           }
           return false;
        }
        return true;
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
    
    
}
