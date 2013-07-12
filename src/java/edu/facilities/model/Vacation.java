/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.model;

/**
 * 
 * 假期对象
 *
 * @author user
 */
public class Vacation {
    
    //流水id
    private int id;
    
    //假期名称
    private String name;
    
    //假期开始日期
    private String startDate;
    
    //假期结束日期
    private String endDate;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    
}
