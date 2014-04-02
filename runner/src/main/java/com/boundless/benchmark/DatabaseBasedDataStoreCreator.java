/**
 *
 */
package com.boundless.benchmark;

import com.boundless.benchmark.data.DataFinder;
import com.boundless.benchmark.data.DataPackage;
import java.io.File;
import java.util.List;
import javax.sql.DataSource;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Soumya Sengupta
 *
 */
public class DatabaseBasedDataStoreCreator extends GeoserverCommunicator {

    final static Logger logger = LoggerFactory
            .getLogger(DatabaseBasedDataStoreCreator.class);

    private String workspaceName, dataDir, dbHost,
            dbPort, dbName, dbUsername, dbPassword;

    private JdbcTemplate jdbcTemplate;

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

    /**
     * @return the dbHost
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * @param dbHost the dbHost to set
     */
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    /**
     * @return the dbPort
     */
    public String getDbPort() {
        return dbPort;
    }

    /**
     * @param dbPort the dbPort to set
     */
    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the dbUsername
     */
    public String getDbUsername() {
        return dbUsername;
    }

    /**
     * @param dbUsername the dbUsername to set
     */
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * @param dbPassword the dbPassword to set
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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

    @Override
    public void process(Exchange exchng) throws Exception {
     List<DataPackage> packages = DataFinder.findData(new File(this.dataDir));

     if(!this.getReader().getWorkspaceNames().contains(workspaceName))
     {
        this.getPublisher().createWorkspace(workspaceName);
     }
        for (DataPackage dp : packages) {
            if (dp.getDataType().equals(DataPackage.DataType.VECTOR)
                    && dp.getEncoding().equals(DataPackage.Encoding.ZIP)) {
                
//                this.getPublisher().
//                
//                
//                // Now we can create the data store.
//                if (!this.createDatastore(workspaceName, dp.getName(), dbHost, dbPort,
//                        dbUsername, dbPassword, dbName)) {
//                    logger.info("Datastore [{}] could not be created.", dp.getName());
//                } else {
//                    logger.info("Datastore [{}] was created.", dp.getName());
//                }
//                // Now that the data store exists, load the data.
//                if (!this.loadDataStore(workspaceName, dp.getName(),dp.getDataFile().getCanonicalPath())) {
//                    logger.info("Data store [{}] could not be created.", dp.getName());
//                } else {
//                    logger.info("Data store [{}] was created.", dp.getName());
//                }

            }

        }
        
    }

}
