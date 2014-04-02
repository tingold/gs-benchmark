/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.InOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This bean will start the main JMeter processing route when trigger
 * @author Tom
 */
public class CamelPhaseAssistant implements CamelContextAware{

    private CamelContext cc;
    private final static Logger logger = LoggerFactory.getLogger(CamelPhaseAssistant.class);
   
    @InOnly
    public void setupComplete()
    {
        try
        {
            logger.info("Starting processing route");
            cc.startRoute("processingRoute");
        }
        catch(Exception ex)
        {
            logger.error("Error starting processing route JMeter tests wont be executed! {}",ex.getMessage() );
        }
    }
    
    @InOnly
    public void tearDownComplete() throws Exception
    {
        cc.stop();
    }
    
    public void setCamelContext(CamelContext cc) {
        this.cc = cc;
    }

    public CamelContext getCamelContext() {
        return cc;
    }
    
}
