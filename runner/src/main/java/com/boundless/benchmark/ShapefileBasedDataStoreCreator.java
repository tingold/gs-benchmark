/**
 *
 */
package com.boundless.benchmark;

import com.boundless.benchmark.data.DataFinder;
import com.boundless.benchmark.data.DataPackage;
import java.io.File;
import java.util.List;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.boundless.benchmark.BenchmarkComponent#process()
     */
    @Override
    public Object process() throws Exception {

        List<DataPackage> packageList = DataFinder.findData(new File(this.dataDir));

        this.ensureWorkspace(workspaceName);
        
        for (DataPackage dp : packageList) {
            if (dp.getDataType() == DataPackage.DataType.VECTOR && dp.getEncoding() == DataPackage.Encoding.ZIP) {
                this.loadZip(dp);
            }

        }        
        return new Object();
    }

    protected void loadZip(DataPackage dp) 
    {                 
        try{
        if (!this.loadDataStore(workspaceName, dp.getName(),dp.getDataFile().getCanonicalPath())) 
        {
            logger.info("Data store [{}] could not be created.", dp.getName());
        }              
        else 
        {
            logger.info("Data store [{}] was created.", dp.getName());
        }
        }
        catch(Exception ex)
        {
            logger.error("Error while attempting to create datastore [{}]: {}", dp.getName(), ex.getMessage());
        }
        
        
    }

    protected void ensureWorkspace(String workspace) {
                // If the workspace exists, remove from Geoserver so that we start
        // from a clean slate.
        try {
            if (!this.checkIfWorkspaceExists(workspace)) {
                logger.info("Workspace [{}] exists.", workspace);

                if (!this.deleteWorkspace(workspace)) {
                    throw new Exception("Workspace ["+workspace+"] could not be deleted.");
                } else {
                    logger.info("Workspace [{}] was deleted.", workspace);
                }
            } else {
                logger.info("Workspace [" + workspace + "] does not exists.");
            }

            // Need the workspace before we can create a data store.
            if (!this.createWorkspace(workspace)) {
                logger.info("Workspace [{}] could not be created.", workspace);
            } else {
                logger.info("Workspace [{}] was created.", workspace);
            }
        } catch (Exception ex) {
            logger.error("Error ensuring workspace {}: {}", workspace, ex.getMessage());
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
}
