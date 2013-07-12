/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.model;

/**
 * 预留记录对象
 * 
 * @author user
 */
public class ReserveRecord {

    //流水id
    private int id;
    
    //设备对象id
    private int facilityID;
    
    //设备预留开始时间
    private String startDate; 
    
    //设备预留结束时间
    private String endDate;
    
    //预约用户对象id
    private int userID;
    
    //预约用户名
    private String userName;
    
    //设备名称
    private String facilityName;
    
    //预约时间
    private String createDate;
    
    //是否缺席 
    private int isAbsence;

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
     * @return the facilityID
     */
    public int getFacilityID() {
        return facilityID;
    }

    /**
     * @param facilityID the facilityID to set
     */
    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
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

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the isAbsence
     */
    public int getIsAbsence() {
        return isAbsence;
    }

    /**
     * @param isAbsence the isAbsence to set
     */
    public void setIsAbsence(int isAbsence) {
        this.isAbsence = isAbsence;
    }

    /**
     * @return the facilityName
     */
    public String getFacilityName() {
        return facilityName;
    }

    /**
     * @param facilityName the facilityName to set
     */
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    
}
