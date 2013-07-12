/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.model;

/**
 * 用户对象
 * 
 * @author user
 */
public class User {
    
    //流水id 
    private int id;
    
    //登录用的用户名
    private String account;
    
    //用于登录用的密码
    private String password;
    
    //用于显示的姓名
    private String name;
    
    //班级id
    private int gradeID;
    
    //用户类型id
    private int userTypeID;
    
    //是否有效
    private int isValid;
    
    //缺席次数
    private int absenceNum;
    
    //缺席日期
    private String absenceDate;
    
    //创建日期
    private String createDate;

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
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the gradeID
     */
    public int getGradeID() {
        return gradeID;
    }

    /**
     * @param gradeID the gradeID to set
     */
    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    /**
     * @return the userTypeID
     */
    public int getUserTypeID() {
        return userTypeID;
    }

    /**
     * @param userTypeID the userTypeID to set
     */
    public void setUserTypeID(int userTypeID) {
        this.userTypeID = userTypeID;
    }

    /**
     * @return the isValid
     */
    public int getIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the absenceNum
     */
    public int getAbsenceNum() {
        return absenceNum;
    }

    /**
     * @param absenceNum the absenceNum to set
     */
    public void setAbsenceNum(int absenceNum) {
        this.absenceNum = absenceNum;
    }

    /**
     * @return the absenceDate
     */
    public String getAbsenceDate() {
        return absenceDate;
    }

    /**
     * @param absenceDate the absenceDate to set
     */
    public void setAbsenceDate(String absenceDate) {
        this.absenceDate = absenceDate;
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
    
}
