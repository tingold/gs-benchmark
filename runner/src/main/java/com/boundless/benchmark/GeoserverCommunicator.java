/**
 *
 */
package com.boundless.benchmark;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import java.io.File;
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

    protected boolean createStyle(File sld, String styleName) {
        boolean result = false;
        try {
            if (!this.getReader().getStyles().getNames().contains(styleName)) {
                result = this.getPublisher().publishStyle(sld, styleName);
                logger.info("Published style {}", styleName);
                
            } else {
                logger.info("Style {} already exists", styleName);
                
            }
        } catch (Exception ex) {
            logger.error("Error uploading style: {}", ex.getMessage());
        }
        finally
        {
            return result;
        }
    }

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
