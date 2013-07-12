/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.connection.DBConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 
 * @author user
 */
public abstract class BaseDao <T,PK extends Serializable> {
    
    /**
     * 获得连接
     * @return 
     */
    public Connection getConnection() throws SQLException {
        Connection conn = DBConnection.getInstance().getConn();
        conn.setAutoCommit(true);
        return conn;
    }

    /**
     * 获取预编译执行句柄
     * @param sql
     * @return
     * @throws SQLException 
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(sql);
    }
    
    public Statement getStatement() throws SQLException {
        Connection conn = getConnection();
        return conn.createStatement();
    }
    
    /**
     * 子类实现添加或者更新功能
     * @return 
     */
    public abstract int saveOrUpdate(T t) throws Exception;
    
    /**
     * 删除班级
     * @param t
     * @return
     * @throws Exception 
     */
    public abstract int del(T t) throws Exception;
    
    /**
     * 通过主键id找到对象
     * @param pk
     * @return 
     */
    public abstract T findById(PK pk) throws Exception;
    
    /**
     * 找到所有对象
     * @return 
     */
    public abstract List<T> findAll() throws Exception;
    
}
