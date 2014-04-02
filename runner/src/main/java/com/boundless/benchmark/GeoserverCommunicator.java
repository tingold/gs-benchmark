/**
 *
 */
package com.boundless.benchmark;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Soumya Sengupta
 *
 */
public abstract class GeoserverCommunicator extends AbstractBenchmarkComponent {
    

    private GeoServerRESTPublisher publisher;

    private GeoServerRESTReader reader;
/*    
    public boolean loadDataStore(String workspaceName, String dataStoreName,
            String filePath) throws Exception {
        
        logger.debug("About to load data from [" + filePath
                + "] into data store [" + dataStoreName + "] in workspace ["
                + workspaceName + "]");
        HttpPut request = new HttpPut("http://" + geoserverHost+":"+geoserverPort
                + "/geoserver/rest/workspaces/" + workspaceName
                + "/datastores/" + dataStoreName
                + "/file.shp?configure=all&target=shp");
        request.addHeader("Content-type", "application/zip");
        request.setEntity(EntityBuilder.create().setFile(new File(filePath))
                .build());

        return (Boolean) process(request, 201);
    }

    public boolean createDatastore(String workspaceName, String datastoreName,
            String dbHost, String dbPort, String username, String password,
            String dbName) throws Exception {
       
        String entity = "{" + "'dataStore': {" + "'name': '" + datastoreName
                + "'," + "'connectionParameters': {" + "'host': '" + dbHost
                + "'," + "'port': '" + dbPort + "'," + "'database': '" + dbName
                + "'," + "'user': '" + username + "'," + "'geoserverPassword': '"
                + password + "'," + "'dbtype': 'postgis'" + "	}" + "}" + "}";

        logger.debug("About to create datastore [ " + datastoreName
                + "] in workspace [" + workspaceName + "]");
        HttpPost request = new HttpPost("http://" + geoserverHost+":"+geoserverPort
                + "/geoserver/rest/workspaces/" + workspaceName + "/datastores");
        request.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
        request.setEntity(EntityBuilder.create().setText(entity)
                .setContentType(ContentType.APPLICATION_JSON).build());

        return (Boolean) process(request, 201);
    }

  */  

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
}
