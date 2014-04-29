/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data.results;

import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class SimpleStatistic implements Serializable
{
    private String name;
    private double mean = 0;
    private double max = 0;
    private double min = 0;
    private double sampleSize;
    private double sdev = 0;

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
     * @return the mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * @param mean the mean to set
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * @return the sdev
     */
    public double getSdev() {
        return sdev;
    }

    /**
     * @param sdev the sdev to set
     */
    public void setSdev(double sdev) {
        this.sdev = sdev;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @return the sampleSize
     */
    public double getSampleSize() {
        return sampleSize;
    }

    /**
     * @param sampleSize the sampleSize to set
     */
    public void setSampleSize(double sampleSize) {
        this.sampleSize = sampleSize;
    }
    
}
