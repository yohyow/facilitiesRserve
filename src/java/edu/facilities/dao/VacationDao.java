/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.Vacation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 假期数据库操作类
 * 
 * @author user
 */
public class VacationDao extends BaseDao<Vacation, Integer> {

    @Override
    public int saveOrUpdate(Vacation t) throws Exception {
        StringBuilder sql = new StringBuilder();
        if(t.getId() == 0) {
            sql.append("insert into fr_vacation(name, startDate, enddate) values('");
            sql.append(t.getName());
            sql.append("','");
            sql.append(t.getStartDate());
            sql.append("','");
            sql.append(t.getEndDate());
            sql.append("')");
        }else {
            sql.append("update fr_vacation set name = '");
            sql.append(t.getName());
            sql.append("',startdate = '");
            sql.append(t.getStartDate());
            sql.append("',enddate = '");
            sql.append(t.getEndDate());
            sql.append("' where id = ");
            sql.append(t.getId());
        }
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }

    @Override
    public int del(Vacation t) throws Exception {
        PreparedStatement ps = getPreparedStatement("delete from fr_vacation where id = " + t.getId());
        return ps.executeUpdate();
    }

    @Override
    public Vacation findById(Integer pk) throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_vacation where id = " + pk);
        ResultSet rs = ps.executeQuery();
        Vacation vacation = null;
        while (rs.next()) {            
            vacation = new Vacation();
            vacation.setId(rs.getInt("id"));
            vacation.setName(rs.getString("name"));
            vacation.setStartDate(rs.getString("startdate"));
            vacation.setEndDate(rs.getString("enddate"));
        }
        return vacation;
    }

    @Override
    public List<Vacation> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_vacation");
        ResultSet rs = ps.executeQuery();
        ArrayList<Vacation> list = new ArrayList<Vacation>();
        while (rs.next()) {            
            Vacation vacation = new Vacation();
            vacation.setId(rs.getInt("id"));
            vacation.setName(rs.getString("name"));
            vacation.setStartDate(rs.getString("startdate"));
            vacation.setEndDate(rs.getString("enddate"));
            list.add(vacation);
        }
        return list;
    }

    /**
     * 根据名字判断是否存在此假期
     * @param name
     * @return int 0 为不存在 1为存在
     * @throws Exception 
     */
    public int findByNameIsExist(String name) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select id from fr_vacation where name = '");
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
