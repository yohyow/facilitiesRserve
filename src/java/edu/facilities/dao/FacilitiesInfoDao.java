/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.FacilitiesInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息数据库操作类
 * 
 * @author user
 */
public class FacilitiesInfoDao extends BaseDao<FacilitiesInfo, Integer> {

    @Override
    public int saveOrUpdate(FacilitiesInfo t) throws Exception {
        StringBuilder sql = new StringBuilder();
        if(t.getId() == 0) {
            sql.append("insert into fr_facilitiesinfo(name, facilitiesTypeID) values('");
            sql.append(t.getName());
            sql.append("',");
            sql.append(t.getFacilitiesTypeID());
            sql.append(")");
        }else {
            sql.append("update fr_facilitiesinfo set name = '");
            sql.append(t.getName());
            sql.append("', facilitiesTypeID = ");
            sql.append(t.getFacilitiesTypeID());
            sql.append(" where id = ");
            sql.append(t.getId());
        }
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }

    @Override
    public int del(FacilitiesInfo t) throws Exception {
        PreparedStatement ps = getPreparedStatement("delete from fr_facilitiesinfo where id = " + t.getId());
        return ps.executeUpdate();
    }

    @Override
    public FacilitiesInfo findById(Integer pk) throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_facilitiesinfo where id = " + pk);
        ResultSet rs = ps.executeQuery();
        FacilitiesInfo facilitiesInfo = null;
        while (rs.next()) {            
            facilitiesInfo = new FacilitiesInfo();
            facilitiesInfo.setId(rs.getInt("id"));
            facilitiesInfo.setName(rs.getString("name"));
        }
        return facilitiesInfo;
    }

    @Override
    public List<FacilitiesInfo> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_facilitiesinfo");
        ResultSet rs = ps.executeQuery();
        ArrayList<FacilitiesInfo> list = new ArrayList<FacilitiesInfo>();
        while (rs.next()) {            
            FacilitiesInfo facilitiesInfo = new FacilitiesInfo();
            facilitiesInfo.setId(rs.getInt("id"));
            facilitiesInfo.setName(rs.getString("name"));
            list.add(facilitiesInfo);
        }
        return list;
    }
    
    /**
     * 根据设备分类id得到设备集合
     * @param facilitiesTypeID
     * @return
     * @throws Exception 
     */
    public List<FacilitiesInfo> findByTypeID(String facilitiesTypeID) throws Exception{
        PreparedStatement ps = getPreparedStatement("select * from fr_facilitiesinfo where facilitiesTypeID = " + facilitiesTypeID);
        ResultSet rs = ps.executeQuery();
        ArrayList<FacilitiesInfo> list = new ArrayList<FacilitiesInfo>();
        while (rs.next()) {            
            FacilitiesInfo facilitiesInfo = new FacilitiesInfo();
            facilitiesInfo.setId(rs.getInt("id"));
            facilitiesInfo.setName(rs.getString("name"));
            list.add(facilitiesInfo);
        }
        return list;
    }

}
