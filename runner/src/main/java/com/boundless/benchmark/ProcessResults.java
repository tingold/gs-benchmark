/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boundless.benchmark;

import com.boundless.benchmark.data.ResultProcessor;
import java.io.File;
import java.util.Arrays;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class ProcessResults {

    final static Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);

    
    private Main main;

    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        final String parsePath = args[0];
        String profile = "classpath:/parseContext.xml";
        final ResultProcessor rp = new ResultProcessor();
        RouteBuilder rb = new RouteBuilder() {

            
            @Override
            public void configure() throws Exception {
                from("file://"+parsePath)
                        .split().tokenize("\n", 100)
                        .unmarshal("bindy").bean(rp,"processStat");
            }

        };

        Main main = new Main();
        try {

            main.enableHangupSupport();
            main.setFileApplicationContextUri(profile);
            main.setRouteBuilders(Arrays.asList(new RouteBuilder[]{rb}));
            main.run();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("Completed benchmarking session...");

    }

}
