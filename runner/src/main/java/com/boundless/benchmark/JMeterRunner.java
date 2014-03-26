/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark;

import java.io.IOException;

/**
 *
 * @author Tom
 */
public class JMeterRunner {
    
    private String jmeterLocation;
    
    private String propertiesFile;
    
    private String testFile;
    
    public void runJMeter()
    {
        try{
            Process jproc = new ProcessBuilder(jmeterLocation, "-n -q "+propertiesFile,"-t "+testFile).start();
            }
        catch(IOException ex)
        {
        
        }
    }
    
}
