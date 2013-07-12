/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.Function;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能列表数据库操作类
 * 
 * @author user
 */
public class FunctionDao extends BaseDao<Function, Integer> {

    @Override
    public int saveOrUpdate(Function t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int del(Function t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Function findById(Integer pk) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Function> findAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Function> findByUserId(String id) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select f.* from fr_function as f, fr_function_user as fu where fu.FUNCTIONID = f.ID and fu.USERID = ");
        sql.append(id);
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        ArrayList<Function> list = new ArrayList<Function>();
        while (rs.next()) {            
            Function function = new Function();
            function.setId(rs.getInt("id"));
            function.setName(rs.getString("name"));
            function.setUrl(rs.getString("url"));
            list.add(function);
        }
        return list;
    }
}
