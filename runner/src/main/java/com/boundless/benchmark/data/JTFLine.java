/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boundless.benchmark.data;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;


/**
 *
 * @author Tom
 */
@CsvRecord(separator = ",")
public class JTFLine implements java.io.Serializable {
    
    @DataField(pos=1)
    private long timestamp;

    @DataField(pos=2)
    private long elapsedTime;
    
    @DataField(pos=3)
    private String label;
            
    @DataField(pos=4)
    private String responseCode;
    
    @DataField(pos=5)
    private String responseMessage;
    
    @DataField(pos=6)
    private String threadName;
    
    @DataField(pos=7)
    private String dataType;
    
    @DataField(pos=8)
    private String success = "false";
        
    @DataField(pos=9)
    private int byteSize;   
    
    @DataField(pos=10)
    private int latency;
    
    @DataField(pos=11)
    private int sampleCount;
    
    @DataField(pos=12)
    private int errorCount;

    @DataField(pos=13)
    private int idleTime;
    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the elapsedTime
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the elapsedTime to set
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * @return the threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * @param threadName the threadName to set
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the success
     */
    public Boolean getSuccess() {
        return Boolean.parseBoolean(success);
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(Boolean success) {
        this.success = success.toString();
    }

    /**
     * @return the byteSize
     */
    public int getByteSize() {
        return byteSize;
    }

    /**
     * @param byteSize the byteSize to set
     */
    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }

    /**
     * @return the latency
     */
    public int getLatency() {
        return latency;
    }

    /**
     * @param latency the latency to set
     */
    public void setLatency(int latency) {
        this.latency = latency;
    }

    /**
     * @return the sampleCount
     */
    public int getSampleCount() {
        return sampleCount;
    }

    /**
     * @param sampleCount the sampleCount to set
     */
    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    /**
     * @return the errorCount
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the idleTime
     */
    public int getIdleTime() {
        return idleTime;
    }

    /**
     * @param idleTime the idleTime to set
     */
    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }
}
