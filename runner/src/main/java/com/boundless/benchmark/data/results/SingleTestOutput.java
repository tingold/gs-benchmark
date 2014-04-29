/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boundless.benchmark.data.results;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Tom
 */
public class SingleTestOutput implements Serializable
{

    private Requests requests = new Requests();
    
    private String name;
    private final String id = UUID.randomUUID().toString();
    private List<SimpleStatistic> stats = new ArrayList<SimpleStatistic>();
    /**
     * @return the requests
     */
    public Requests getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(Requests requests) {
        this.requests = requests;
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
     * @return the stats
     */
    public List<SimpleStatistic> getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(List<SimpleStatistic> stats) {
        this.stats = stats;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    public class Requests
    {
        
        private Integer errors = 0;
        private Integer successes = 0;

        /**
         * @return the total
         */
        public Integer getTotal() {
            return errors + successes;
        }

        /**
         * @return the errors
         */
        public Integer getErrors() {
            return errors;
        }

        /**
         * @param errors the errors to set
         */
        public void setErrors(Integer errors) {
            this.errors = errors;
        }

        /**
         * @return the successes
         */
        public Integer getSuccesses() {
            return successes;
        }

        /**
         * @param successes the successes to set
         */
        public void setSuccesses(Integer successes) {
            this.successes = successes;
        }
    }
    
    
}
