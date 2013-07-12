/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.model;

/**
 * 设备类型对象
 * 
 * @author user
 */
public class FacilitiesType {

    //流水id
    private int id;
    
    //设备类型名称
    private String name;

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
    
}
