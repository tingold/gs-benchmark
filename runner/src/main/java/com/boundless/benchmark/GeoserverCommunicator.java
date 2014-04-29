/**
 *
 */
package com.boundless.benchmark;


import com.boundless.benchmark.data.DataPackage;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Soumya Sengupta
 *
 */
public abstract class GeoserverCommunicator extends AbstractBenchmarkComponent {
    
    protected Logger logger = LoggerFactory.getLogger("GeoserverCommunicator");
    
    private GeoServerRESTPublisher publisher;

    private GeoServerRESTReader reader;
    
    private boolean deployData;

    
    /**
     * @return the publisher
     */
    public GeoServerRESTPublisher getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(GeoServerRESTPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the reader
     */
    public GeoServerRESTReader getReader() {
        return reader;
    }

    /**
     * @param reader the reader to set
     */
    public void setReader(GeoServerRESTReader reader) {
        this.reader = reader;
    }

    /**
     * @return the deployData
     */
    public boolean isDeployData() {
        return deployData;
    }

    /**
     * @param deployData the deployData to set
     */
    public void setDeployData(boolean deployData) {
        this.deployData = deployData;
    }
}
