/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.UserType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户类型数据库操作类
 * 
 * @author user
 */
public class UserTypeDao extends BaseDao<UserType, Integer> {

    @Override
    public int saveOrUpdate(UserType t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int del(UserType t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserType findById(Integer pk) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserType> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from usertype");
        ResultSet rs = ps.executeQuery();
        ArrayList<UserType> list = new ArrayList<UserType>();
        while (rs.next()) {            
            UserType userType = new UserType();
            userType.setId(rs.getInt("id"));
            userType.setName(rs.getString("name"));
            list.add(userType);
        }
        return list;
    }

}
