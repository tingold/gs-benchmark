/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tom
 */
public class ResultProcessorTest {
    
    public ResultProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of processStat method, of class ResultProcessor.
     */
    @Test
    public void testProcessStat() {
        System.out.println("processStat");
        JTFLine line = new JTFLine();
        ResultProcessor instance = new ResultProcessor();
        instance.processStat(line);
        
        
    }

    private JTFLine genTestLine()
    {
        
        
        JTFLine line = new JTFLine();
        line.setByteSize(654654);
        line.setElapsedTime(654654654654l);
        line.setErrorCount(0);
        line.setLatency(6546);
        line.setResponseCode("200");
        line.setResponseMessage("OK");
        line.setSuccess(true);
        line.setThreadName("my thread");
        line.setTimestamp(6565465);
        return line;
        
    }
    
    /**
     * Test of printStats method, of class ResultProcessor.
     */
    @Test
    public void testPrintStats() {
//        System.out.println("printStats");
//        ResultProcessor instance = new ResultProcessor();
//        instance.processStat(this.genTestLine());
//        instance.printStats();
//        
        
    }
    
}
