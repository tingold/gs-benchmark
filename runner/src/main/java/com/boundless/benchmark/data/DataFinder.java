/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tom
 */
public class DataFinder
{
    private static final Logger logger = LoggerFactory.getLogger(DataFinder.class);
    //need to setup this class to cache info instead of parsing it out everytime
    private static List<DataPackage> cache = new ArrayList<DataPackage>();

    /**
     * @return the cache
     */
    public static List<DataPackage> getCache() {
        return cache;
    }
    private String cachedDir;
    
    private static DataFinder instance;
    
    protected DataFinder()
    {
        
    }
    public static DataFinder getInstance()
    {
        if(instance == null)
        {
            instance = new DataFinder();
        }
        return instance;
    }
    public static List<DataPackage> findData(File rootDirectory)
    {
        cache.clear();
        return _findData(rootDirectory);
    }
    private static List<DataPackage> _findData(File rootDirectory)
    {
        
        logger.debug("Looking through {}", rootDirectory.getName());
        List<DataPackage> packages = new ArrayList<DataPackage>();
        
        
        if(rootDirectory.isDirectory())
        {           
                                    
            File[] listFiles = rootDirectory.listFiles();           
            for(File file: listFiles)
            {             
                packages.addAll(DataFinder._findData(file));            
            }            
        }    
        else
        {
            logger.debug("{} was not a directory", rootDirectory.getName());
            if(rootDirectory.getName().equalsIgnoreCase("data-package.properties"))
                   {
                    try{
                       DataPackage dp = new DataPackage(rootDirectory);
                       packages.add(dp);
                       cache.add(dp);
                    }
                    
                       catch(IOException ex)
                       {
                           logger.warn("error parsing data package file: {}", ex.getMessage());
                       }
                   } 
        }
        return packages;
    }
     
    
    
}
