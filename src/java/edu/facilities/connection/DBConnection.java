/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.connection;

import edu.facilities.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Administrator
 * 
 * 单例模式数据库连接管理类
 */
public class DBConnection {
    
    private static DBConnection mDBConnectioin = null;
    
    private final static String mUrl_derby = "jdbc:derby://";
    
    private final static String mDriverName_derby = "org.apache.derby.jdbc.EmbeddedDriver";
    
    private final static String mDB_name = "/bookingDB;create=true;territory=zh_CN";
    
    private final static String mDB_userName = "root";
    
    private final static String mDB_password = "123";
    
    private final static String mDB_address = "localhost:1527";
    
    private Connection mConnection = null;
    
    private DBConnection() {
    }
    
    /**
     * 得到数据库连接管理类
     * @return 
     */
    public static DBConnection getInstance() {
        if(null == mDBConnectioin) {
            mDBConnectioin = new DBConnection();
        }
        return mDBConnectioin;
    }
    
    /**
     * 得到数据库连接
     * @return 
     */
    public Connection getConn() {
        if(mConnection != null) {
            return mConnection;
        }else {
            StringBuilder url = new StringBuilder();
            url.append(mUrl_derby);
            url.append(mDB_address);
            url.append(mDB_name);
            try {
                Class.forName(mDriverName_derby);
                mConnection = DriverManager.getConnection(url.toString(), mDB_userName, mDB_password);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            return mConnection;
        }
    }
    
    public static void main(String[] args) throws Exception{
        Connection conn = DBConnection.getInstance().getConn();
        PreparedStatement ps = conn.prepareStatement("select * from fr_user where account = " + "'admin'");
        ResultSet rs = ps.executeQuery();
        User user = null;
        while(rs.next()) {
            user = new User();
            user = getUser(rs, user);
        }
        System.out.print(user.getName());
    }
    
    /**
     * 封装user对象
     * @param rs
     * @param user
     * @return
     * @throws Exception 
     */
    private static User getUser(ResultSet rs, User user) throws Exception{
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
}