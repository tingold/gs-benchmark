/**
 * 
 */
package com.boundless.benchmark;

import org.apache.camel.spring.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Soumya Sengupta
 * 
 */
public class BenchmarkRunner{
	
        final static Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);
        
        private Main main;
        
        
	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String profile = "classpath:/camelContext.xml";

		if (args.length > 0) {
			profile = args[0];
		}

		logger.info("Initializing benchmark runner with profile [" + profile
				+ "]");
                String[] camelArgs = {profile};
                Main main = new Main();
                try{
                    main.setFileApplicationContextUri(profile);
                    main.enableHangupSupport();
                    main.run();
                    
                }
                catch(Exception ex)  
                {
                    logger.error(ex.getMessage());
                }
            logger.info("Completed benchmarking session...");
        
	}


}
