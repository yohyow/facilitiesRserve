/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据库操作类
 * 
 * @author user
 */
public class UserDao extends BaseDao<User, Integer> {

    @Override
    public int saveOrUpdate(User t) throws Exception {
        StringBuilder sql = new StringBuilder();
        if(t.getId() == 0) {
            sql.append("insert into fr_user(account, password, name, gradeID, usertypeid, isvalid, createdate) values('");
            sql.append(t.getAccount());
            sql.append("','");
            sql.append(t.getPassword());
            sql.append("','");
            sql.append(t.getName());
            sql.append("',");
            sql.append(t.getGradeID());
            sql.append(",");
            sql.append(t.getUserTypeID());
            sql.append(",");
            sql.append(t.getIsValid());
            sql.append(",'");
            sql.append(t.getCreateDate());
            sql.append("')");
        }else {
            sql.append("update fr_user set account = '");
            sql.append(t.getAccount());
            sql.append("',password = '");
            sql.append(t.getPassword());
            sql.append("',name = '");
            sql.append(t.getName());
            sql.append("',gradeid = ");
            sql.append(t.getGradeID());
            sql.append(", usertypeid = ");
            sql.append(t.getUserTypeID());
            sql.append(", isvalid = ");
            sql.append(t.getIsValid());
            sql.append(", absencenum = ");
            sql.append(t.getAbsenceNum());
            sql.append(", absencedate = '");
            sql.append(t.getAbsenceDate());
            sql.append("', createdate = '");
            sql.append(t.getCreateDate());
            sql.append("' where id = ");
            sql.append(t.getId());
        }
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }

    @Override
    public int del(User t) throws Exception {
        PreparedStatement ps = getPreparedStatement("delete from fr_user where id = " + t.getId());
        return ps.executeUpdate();
    }

    @Override
    public User findById(Integer pk) throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_user where id = " + pk);
        ResultSet rs = ps.executeQuery();
        User user = null;
        while(rs.next()) {
            user = new User();
            user = getUser(rs, user);
        }
        return user;
        
    }
    
    /**
     * 根据用户名account查询某用户对象
     * @param account
     * @return
     * @throws Exception 
     */
    public User findByAccount(String account) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("select * from fr_user where account = '");
        sql.append(account);
        sql.append("'");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        User user = null;
        while(rs.next()) {
            user = new User();
            user = getUser(rs, user);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_user");
        ResultSet rs = ps.executeQuery();
        ArrayList<User> list = new ArrayList<User>();
        while (rs.next()) {            
            User user = new User();
            user = getUser(rs, user);
            list.add(user);
        }
        return list;
    }
    
    /**
     * 封装user对象
     * @param rs
     * @param user
     * @return
     * @throws Exception 
     */
    private User getUser(ResultSet rs, User user) throws Exception{
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setAccount(rs.getString("account"));
        user.setPassword(rs.getString("password"));
        user.setUserTypeID(rs.getInt("usertypeid"));
        user.setIsValid(rs.getInt("isvalid"));
        user.setGradeID(rs.getInt("gradeid"));
        user.setAbsenceNum(rs.getInt("absencenum"));
        user.setAbsenceDate(rs.getString("absencedate"));
        return user;
    }
    
    /**
     * 更新用户是否有效
     * @param id
     * @param isvalid 0代表无效 1代表有效
     * @return
     * @throws Exception 
     */
    public int updateIsValid(String id, int isvalid) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("update fr_user set isvalid = ");
        sql.append(isvalid);
        sql.append(", where id = ");
        sql.append(id);
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }
    
    /**
     * 更新用户缺席次数
     * @param id
     * @param num
     * @param absenceDate
     * @return 
     */
    public int updateAbsenceNum(String id, int num, String absenceDate) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("update fr_user set absenceNum = ");
        sql.append(num);
        sql.append(", and absenceDate = '");
        sql.append(absenceDate);
        sql.append("' where id = ");
        sql.append(id);
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }
    
    /**
     * 查找某班级和用户类型
     * @return
     * @throws Exception 
     */
    public List<User> findByGradeIDAndUserTypeID(String gradeId, String userTypeId) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select * from fr_user where gradeid = ");
        sql.append(gradeId);
        sql.append(" and usertypeid = ");
        sql.append(userTypeId);
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        ArrayList<User> list = new ArrayList<User>();
        while (rs.next()) {            
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAccount(rs.getString("account"));
            user.setPassword(rs.getString("password"));
            user.setGradeID(rs.getInt("gradeid"));
            user.setUserTypeID(rs.getInt("usertypeid"));
            user.setIsValid(rs.getInt("isvalid"));
            user.setAbsenceNum(rs.getInt("absencenum"));
            user.setAbsenceDate(rs.getString("absencedate"));
            list.add(user);
        }
        return list;
    }

}
