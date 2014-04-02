/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.jmeter;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class ResultProcessor extends RouteBuilder{

    private static final Logger logger = LoggerFactory.getLogger(ResultProcessor.class);
    
    @Override
    public void configure() throws Exception 
    {
        from("file:./?antInclude=*.jtl").to("xslt:xslt/jmeter-results-report_21.xsl").to("file:./results?autoCreate=true");
        
        
    }
    
}
