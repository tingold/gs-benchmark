/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boundless.benchmark.data.results;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tom
 */
public class TestRunOutput implements Serializable {

    private Date testRunCompleted;
    private int totalTests = 0;
    private long testDuration = 0;
    private int totalRequests = 0;
    private int totalSuccesses = 0;
    private int totalErrors = 0;
    
    private List<SingleTestOutput> tests = new ArrayList<SingleTestOutput>();

    /**
     * @return the testRunCompleted
     */
    public Date getTestRunCompleted() {
        return testRunCompleted;
    }

    /**
     * @param testRunCompleted the testRunCompleted to set
     */
    public void setTestRunCompleted(Date testRunCompleted) {
        this.testRunCompleted = testRunCompleted;
    }

    /**
     * @return the totalTests
     */
    public int getTotalTests() {
        return totalTests;
    }

    /**
     * @param totalTests the totalTests to set
     */
    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    /**
     * @return the tests
     */
    public List<SingleTestOutput> getTests() {
        return tests;
    }

    /**
     * @param tests the tests to set
     */
    public void setTests(List<SingleTestOutput> tests) {
        this.tests = tests;
    }

    /**
     * @return the testDuration
     */
    public long getTestDuration() {
        return testDuration;
    }

    /**
     * @param testDuration the testDuration to set
     */
    public void setTestDuration(long testDuration) {
        this.testDuration = testDuration;
    }

    /**
     * @return the totalRequests
     */
    public int getTotalRequests() {
        return totalRequests;
    }

    /**
     * @param totalRequests the totalRequests to set
     */
    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    /**
     * @return the totalSuccesses
     */
    public int getTotalSuccesses() {
        return totalSuccesses;
    }

    /**
     * @param totalSuccesses the totalSuccesses to set
     */
    public void setTotalSuccesses(int totalSuccesses) {
        this.totalSuccesses = totalSuccesses;
    }

    /**
     * @return the totalErrors
     */
    public int getTotalErrors() {
        return totalErrors;
    }

    /**
     * @param totalErrors the totalErrors to set
     */
    public void setTotalErrors(int totalErrors) {
        this.totalErrors = totalErrors;
    }
}
