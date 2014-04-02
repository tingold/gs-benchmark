/**
 * 
 */
package com.boundless.benchmark;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Soumya Sengupta
 * 
 */
public class GeoserverCleaner extends GeoserverCommunicator {
	final static Logger logger = LoggerFactory
			.getLogger(GeoserverCleaner.class);

	private String workspaceName;
        
        private boolean deleteOnExit = true;

	/**
	 * @return the workspaceName
	 */
	public String getWorkspaceName() {
		return workspaceName;
	}

	/**
	 * @param workspaceName
	 *            the workspaceName to set
	 */
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}



    public void process(Exchange exchng) throws Exception 
    {
        if(this.deleteOnExit)
        {
            this.getPublisher().removeNamespace(workspaceName, true);    
        }
    }

    /**
     * @return the deleteOnExit
     */
    public boolean isDeleteOnExit() {
        return deleteOnExit;
    }

    /**
     * @param deleteOnExit the deleteOnExit to set
     */
    public void setDeleteOnExit(boolean deleteOnExit) {
        this.deleteOnExit = deleteOnExit;
    }
}
