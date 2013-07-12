/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.Grade;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * 班级数据库操作类
 * 
 * @author user
 */
public class GradeDao extends BaseDao<Grade, Integer> {

    @Override
    public int saveOrUpdate(Grade t) throws Exception{
        StringBuilder sql = new StringBuilder();
        if(t.getId() == 0) {
                sql.append("insert into fr_grade(name) values('");
                sql.append(t.getName()); 
                sql.append("')");
        }else {
            sql.append("update fr_grade set name = '");
            sql.append(t.getName());
            sql.append("' where id = ");
            sql.append(t.getId());
        }
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }

    @Override
    public Grade findById(Integer pk) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select * from fr_grade where id = ");
        sql.append(pk);
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        Grade grade = null;
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            grade.setId(id);
            grade.setName(name);
        }
        return grade;
    }

    @Override
    public List<Grade> findAll() throws Exception{
        PreparedStatement ps = getPreparedStatement("select * from fr_grade");
        ResultSet rs = ps.executeQuery();
        List<Grade> list = new ArrayList<Grade>();
        while(rs.next()) {
            Grade grade = new Grade();
            grade.setId(rs.getInt("id"));
            grade.setName(rs.getString("name"));
            list.add(grade);
        }
        return list;
    }

    @Override
    public int del(Grade t) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from fr_grade where id = ");
        sql.append(t.getId());
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }
    
    /**
     * 根据名字判断是否存在此班级
     * @param name
     * @return int 0 为不存在 1为存在
     * @throws Exception 
     */
    public int findByNameIsExist(String name) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select id from fr_grade where name = '");
        sql.append(name);
        sql.append("'");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return 1;
        }else {
            return 0;
        }
    }

}
