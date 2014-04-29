/**
 *
 */
package com.boundless.benchmark;

import com.boundless.benchmark.data.DataFinder;
import com.boundless.benchmark.data.DataPackage;
import java.io.File;
import java.util.List;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Soumya Sengupta, Tom Ingold
 *
 */
public class ShapefileBasedDataStoreCreator extends GeoserverCommunicator {

    final static Logger logger = LoggerFactory
            .getLogger(ShapefileBasedDataStoreCreator.class);

    private String workspaceName;

    private String dataDir;

    /**
     * @return the workspaceName
     */
    public String getWorkspaceName() {
        return workspaceName;
    }

    /**
     * @param workspaceName the workspaceName to set
     */
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }



    protected void loadZip(DataPackage dp) 
    { 
        try{
            
            String styleName = dp.getName()+"_style";
            boolean publishStyle = false;
            try
            {
                if(!this.getReader().getStyles().getNames().contains(styleName))
                {
                    publishStyle = this.getPublisher().publishStyle(dp.getSld(),styleName);
                    logger.info("Published style {}", styleName);
                }
                else
                {
                    logger.info("Style {} already exists", styleName);
                    publishStyle = true;
                }
            }
            catch(Exception ex)
            {
                logger.error("Error uploading style: {}", ex.getMessage());
            }
            //if the style insert succeeded then assign the style to the new layer
            if(publishStyle)
            {
                this.getPublisher().publishShp(workspaceName,dp.getName()+"_store", dp.getName(), dp.getDataFile(), dp.getSRS(), styleName);
                logger.info("Published layer {} with default style {}", dp.getName(), styleName);
            }
            //otherwise just leave it as default
            else
            {
                this.getPublisher().publishShp(workspaceName,dp.getName()+"_store", dp.getName(), dp.getDataFile());
                logger.info("Published layer {} with no default style", dp.getName());
            }
                                    
        }
        catch(Exception ex)
        {
            logger.error("Error publishing datapackage {}: {}", dp.getName(), ex.getMessage());
        }
    }

    

    /**
     * @return the dataDir
     */
    public String getDataDir() {
        return dataDir;
    }

    /**
     * @param dataDir the dataDir to set
     */
    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public void process(Exchange exchng) throws Exception {
        if(!this.isDeployData())
        {
            return;
        }
        logger.info("Processing Shapefiles....");
        List<DataPackage> packageList = DataFinder.findData(new File(this.dataDir));

        if(!this.getReader().getWorkspaceNames().contains(this.workspaceName))
        {
            this.getPublisher().createWorkspace(workspaceName);
        }
        
        for (DataPackage dp : packageList) {
            if (dp.getDataType() == DataPackage.DataType.VECTOR && dp.getEncoding() == DataPackage.Encoding.ZIP) {
                this.loadZip(dp);
            }

        }        
       }
}
