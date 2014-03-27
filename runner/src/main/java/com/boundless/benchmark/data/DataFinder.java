/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data;

import java.io.File;
import java.io.FilenameFilter;
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
    
    public static List<DataPackage> findData(File rootDirectory)
    {
        logger.info("Looking through {}", rootDirectory.getName());
        System.out.println("Looking through "+rootDirectory.getName());             
        List<DataPackage> packages = new ArrayList<DataPackage>();
        
        
        if(rootDirectory.isDirectory())
        {           
                        
            System.out.println("dir is "+rootDirectory.getName());
            File[] listFiles = rootDirectory.listFiles();           
            System.out.println(listFiles.length);
            for(File file: listFiles)
            {             
                packages.addAll(DataFinder.findData(file));            
            }            
        }    
        else
        {
            logger.debug("{} was not a directory", rootDirectory.getName());
            if(rootDirectory.getName().equalsIgnoreCase("data-package.properties"))
                   {
                    try{
                       DataPackage dp = new DataPackage(rootDirectory);
                       packages.add(dp);}
                       catch(IOException ex)
                       {
                           logger.warn("error parsing data package file: {}", ex.getMessage());
                       }
                   } 
        }
        return packages;
    }
     
    
    
}
