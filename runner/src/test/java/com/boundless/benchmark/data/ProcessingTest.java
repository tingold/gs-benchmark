package com.boundless.benchmark.data;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tom
 */
public class ProcessingTest {
 
    private static final Logger logger = LoggerFactory.getLogger(ProcessingTest.class);
    
    @Test
    public void testProcessingChain()
    {
        CamelContext ctx = new DefaultCamelContext();
        BindyCsvDataFormat bindy = new BindyCsvDataFormat(JTFLine.class);
        logger.info(ctx.getRegistry().getClass().toString());
        //ctx.addComponent("bindy", bindy);
        
        RouteBuilder rb = new RouteBuilder()
        {

            @Override
            public void configure() throws Exception {
                from("file:///Users/Tom/Desktop/NWs/NWS-Testing/test-results/part1/test-edited.jtl")
                        .split().tokenize("\n", 100)
                        .unmarshal("bindy");
            }
        
        };
    
    }
}
