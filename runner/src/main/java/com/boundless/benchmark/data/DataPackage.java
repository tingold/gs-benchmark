/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class DataPackage {
    
    private static final Logger logger = LoggerFactory.getLogger(DataPackage.class);
    
    private String name;
    
    private DataType dataType;
    
    private Encoding encoding;
    
    private File sld;
    
    private File dataFile; 
    
    private DataFormat format;
    
    private String SRS;
    
    private List<File> tests = new ArrayList<File>();
    
    private Boolean enabled;
    
    public DataPackage(){}
    
    public DataPackage(File properties) throws IOException
    {
        if(properties.exists())
        {
           Properties props = new Properties();
           props.load(new FileInputStream(properties));
           //parse name
           this.name = props.getProperty("data.layer.name");          
           
           this.SRS = props.getProperty("data.layer.srs");
          
           //parse encoding
           logger.debug(props.getProperty("data.layer.encoding").toUpperCase());           
           this.encoding = Encoding.valueOf(props.getProperty("data.layer.encoding").toUpperCase());
           
            //parse data type
           logger.debug(props.getProperty("data.layer.type").toUpperCase());
           this.dataType = DataType.valueOf(props.getProperty("data.layer.type").toUpperCase());
           
           String filename = properties.getParentFile().getCanonicalPath()+File.separator+props.getProperty("data.layer.filename");
           this.dataFile = new File(filename);
           
           String style = properties.getParentFile().getCanonicalPath()+File.separator+props.getProperty("data.layer.style");
           this.sld = new File(style);
           
           this.format = DataFormat.valueOf(props.getProperty("data.layer.format").toUpperCase());
           
           this.enabled = Boolean.parseBoolean(props.getProperty("data.layer.enabled"));
           
           String testString = props.getProperty("data.layer.tests","");
           
           if(!testString.isEmpty() && testString.contains(","))
           { 
               String[] testArry = testString.split(",");               
               for(String test: testString.split(","))
               {
                   tests.add(new File(properties.getParentFile().getCanonicalPath()+File.separator+test));
               }
           }
           else if(!testString.isEmpty())
           {
               logger.info("Adding test {}", testString);
               tests.add(new File(properties.getParentFile().getCanonicalPath()+File.separator+testString));
           }
        }
        
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the encoding
     */
    public Encoding getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the sld
     */
    public File getSld() {
        return sld;
    }

    /**
     * @param sld the sld to set
     */
    public void setSld(File sld) {
        this.sld = sld;
    }

    /**
     * @return the dataFile
     */
    public File getDataFile() {
        return dataFile;
    }

    /**
     * @param dataFile the dataFile to set
     */
    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * @return the SRS
     */
    public String getSRS() {
        return SRS;
    }

    /**
     * @param SRS the SRS to set
     */
    public void setSRS(String SRS) {
        this.SRS = SRS;
    }

    /**
     * @return the format
     */
    public DataFormat getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(DataFormat format) {
        this.format = format;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the tests
     */
    public List<File> getTests() {
        return tests;
    }

    /**
     * @param tests the tests to set
     */
    public void setTests(List<File> tests) {
        this.tests = tests;
    }
    
    
    
    public enum Encoding 
    {
        ZIP, 
        FLAT
    }
    
    public enum DataType
    {
        VECTOR,
        RASTER;
    } 
    
    public enum DataFormat
    {
        SHAPEFILE,
        GEOTIF,
        WORLDFILE,
        IMAGE_PYRAMID
    
    }
}
