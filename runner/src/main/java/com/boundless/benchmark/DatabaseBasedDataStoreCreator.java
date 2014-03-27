/**
 * 
 */
package com.boundless.benchmark;

import javax.sql.DataSource;

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

	private String workspaceName, datastoreName, shapefileZipLocation, dbHost,
			dbPort, dbName, dbUsername, dbPassword;

	private JdbcTemplate jdbcTemplate;

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

	/**
	 * @return the datastoreName
	 */
	public String getDatastoreName() {
		return datastoreName;
	}

	/**
	 * @param datastoreName
	 *            the datastoreName to set
	 */
	public void setDatastoreName(String datastoreName) {
		this.datastoreName = datastoreName;
	}

	/**
	 * @return the shapefileZipLocation
	 */
	public String getShapefileZipLocation() {
		return shapefileZipLocation;
	}

	/**
	 * @param shapefileZipLocation
	 *            the shapefileZipLocation to set
	 */
	public void setShapefileZipLocation(String shapefileZipLocation) {
		this.shapefileZipLocation = shapefileZipLocation;
	}

	/**
	 * @return the dbHost
	 */
	public String getDbHost() {
		return dbHost;
	}

	/**
	 * @param dbHost
	 *            the dbHost to set
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
	 * @param dbPort
	 *            the dbPort to set
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
	 * @param dbName
	 *            the dbName to set
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
	 * @param dbUsername
	 *            the dbUsername to set
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
	 * @param dbPassword
	 *            the dbPassword to set
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
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boundless.benchmark.IBenchmarkComponent#process()
	 */
	@Override
	public Object process() throws Exception {
		String shapefileZipLocation = this.getShapefileZipLocation();
		String workspaceName = this.getWorkspaceName();
		String datastoreName = this.getDatastoreName();
		String dbHost = this.getDbHost();
		String dbPort = this.getDbPort();
		String dbUsername = this.getDbUsername();
		String dbPassword = this.getDbPassword();
		String dbName = this.getDbName();

		// If the workspace exists, remove from Geoserver so that we start
		// from a clean slate.
		if (!this.checkIfWorkspaceExists(workspaceName)) {
			logger.info("Workspace [" + workspaceName + "] exists.");
			if (!this.deleteWorkspace(workspaceName)) {
				throw new Exception("Workspace [" + workspaceName
						+ "] could not be deleted.");
			} else {
				logger.info("Workspace [" + workspaceName + "] was deleted.");
			}
		} else {
			logger.info("Workspace [" + workspaceName + "] does not exists.");
		}

		// Need the workspace before we can create a data store.
		if (!this.createWorkspace(workspaceName)) {
			logger.info("Workspace [" + workspaceName
					+ "] could not be created.");
		} else {
			logger.info("Workspace [" + workspaceName + "] was created.");
		}

		// Now we can create the data store.
		if (!this.createDatastore(workspaceName, datastoreName, dbHost, dbPort,
				dbUsername, dbPassword, dbName)) {
			logger.info("Datastore [" + datastoreName
					+ "] could not be created.");
		} else {
			logger.info("Datastore [" + datastoreName + "] was created.");
		}

		// Now that the data store exists, load the data.
		if (!this.loadDataStore(workspaceName, datastoreName,
				shapefileZipLocation)) {
			logger.info("Data store [" + datastoreName
					+ "] could not be created.");
		} else {
			logger.info("Data store [" + datastoreName + "] was created.");
		}

		return new Object();
	}

}
