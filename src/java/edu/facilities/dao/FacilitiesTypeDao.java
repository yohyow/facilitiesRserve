/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.FacilitiesType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备类型数据库操作类
 * 
 * @author user
 */
public class FacilitiesTypeDao extends BaseDao<FacilitiesType, Integer> {

    @Override
    public int saveOrUpdate(FacilitiesType t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int del(FacilitiesType t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FacilitiesType findById(Integer pk) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FacilitiesType> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from facilitiestype");
        ResultSet rs = ps.executeQuery();
        ArrayList<FacilitiesType> list = new ArrayList<FacilitiesType>();
        while (rs.next()) {            
            FacilitiesType facilitiesType = new FacilitiesType();
            facilitiesType.setId(rs.getInt("id"));
            facilitiesType.setName(rs.getString("name"));
            list.add(facilitiesType);
        }
        return list;
    }

}
