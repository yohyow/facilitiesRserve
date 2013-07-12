/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.model;

/**
 * 功能列表
 * 
 * @author user
 */
public class Function {

    //流水id
    private int id;
    
    //应用名称
    private String name;
    
    //应用访问地址
    private String url;

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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
}
