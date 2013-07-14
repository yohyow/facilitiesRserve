/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.dao;

import edu.facilities.model.ReserveRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 预留记录数据库操作表
 * 
 * @author user
 */
public class ReserveRecordDao extends BaseDao<ReserveRecord, Integer> {

    @Override
    public int saveOrUpdate(ReserveRecord t) throws Exception {
        StringBuilder sql = new StringBuilder();
        if(t.getId() == 0) {
            sql.append("insert into fr_reserveRecord(facilityID, facilityname, userID, userName, startdate, enddate, isAbsence, createDate) values(");
            sql.append(t.getFacilityID());
            sql.append(",'");
            sql.append(t.getFacilityName());
            sql.append("',");
            sql.append(t.getUserID());
            sql.append(",'");
            sql.append(t.getUserName());
            sql.append("','");
            sql.append(t.getStartDate());
            sql.append("','");
            sql.append(t.getEndDate());
            sql.append("',");
            sql.append(t.getIsAbsence());
            sql.append(",'");
            sql.append(t.getCreateDate());
            sql.append("')");
        }else {
            sql.append("update fr_reserveRecord set facilityID = ");
            sql.append(t.getFacilityID());
            sql.append(", facilityname = '");
            sql.append(t.getFacilityName());
            sql.append("', userID = ");
            sql.append(t.getUserID());
            sql.append(", userName = '");
            sql.append(t.getUserName());
            sql.append("', startdate = '");
            sql.append(t.getStartDate());
            sql.append("', enddate = '");
            sql.append(t.getEndDate());
            sql.append("', isAbsence = ");
            sql.append(t.getIsAbsence());
            sql.append(", createdate = '");
            sql.append(t.getCreateDate());
            sql.append("' where id = ");
            sql.append(t.getId());
        }
        PreparedStatement ps = getPreparedStatement(sql.toString());
        return ps.executeUpdate();
    }

    @Override
    public int del(ReserveRecord t) throws Exception {
        PreparedStatement ps = getPreparedStatement("delete from fr_reserveRecord where id = " + t.getId());
        return ps.executeUpdate();
    }

    @Override
    public ReserveRecord findById(Integer pk) throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_reserveRecord where id = " + pk);
        ResultSet rs = ps.executeQuery();
        ReserveRecord reserveRecord = null;
        while (rs.next()) {            
            reserveRecord = new ReserveRecord();
            reserveRecord = getReserveRecord(rs, reserveRecord);
        }
        return reserveRecord;
    }

    @Override
    public List<ReserveRecord> findAll() throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_reserveRecord order by enddate desc");
        ResultSet rs = ps.executeQuery();
        ArrayList<ReserveRecord> list = new ArrayList<ReserveRecord>();
        while (rs.next()) {            
            ReserveRecord reserveRecord = new ReserveRecord();
            reserveRecord = getReserveRecord(rs, reserveRecord);
            list.add(reserveRecord);
        }
        return list;
    }
    
    /**
     * 根据设备查询预约记录
     * @param facilitiesId
     * @return
     * @throws Exception 
     */
    public List<ReserveRecord> findByFacilityID(int facilitiesId) throws Exception {
        PreparedStatement ps = getPreparedStatement("select * from fr_reserveRecord where facilityID = " + facilitiesId + " order by enddate desc");
        ResultSet rs = ps.executeQuery();
        ArrayList<ReserveRecord> list = new ArrayList<ReserveRecord>();
        while (rs.next()) {            
            ReserveRecord reserveRecord = new ReserveRecord();
            reserveRecord = getReserveRecord(rs, reserveRecord);
            list.add(reserveRecord);
        }
        return list;
    }
    
    /**
     * 根据用户ID查询最后一次缺席记录
     * @param userId
     * @return
     * @throws Exception 
     */
    public ReserveRecord findLastAbsenceByUserId(int userId) throws Exception{
        PreparedStatement ps = getPreparedStatement("select * from fr_reserveRecord where userid = " + userId + " and isabsence = 1 order by enddate desc OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        ReserveRecord reserveRecord = null;
        while (rs.next()) {            
            reserveRecord = new ReserveRecord();
            reserveRecord = getReserveRecord(rs, reserveRecord);
        }
        return reserveRecord;
    }
    
    
    /**
     * 得到预留记录对象
     * @param rs
     * @param reserveRecord
     * @return
     * @throws Exception 
     */
    public ReserveRecord getReserveRecord(ResultSet rs, ReserveRecord reserveRecord) throws Exception{
        reserveRecord.setId(rs.getInt("id"));
        reserveRecord.setUserID(rs.getInt("userid"));
        reserveRecord.setUserName(rs.getString("username"));
        reserveRecord.setCreateDate(rs.getString("createdate"));
        reserveRecord.setEndDate(rs.getString("enddate"));
        reserveRecord.setStartDate(rs.getString("startdate"));
        reserveRecord.setIsAbsence(rs.getInt("isabsence"));
        reserveRecord.setFacilityID(rs.getInt("facilityid"));
        reserveRecord.setFacilityName(rs.getString("facilityname"));
        return reserveRecord;
    }

    /**
     * 查询某时间以后的预约记录
     * @param nowDate
     * @return
     * @throws Exception 
     */
    public List<ReserveRecord> findRserveRecordByNowDate(String nowDate) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select * from fr_reserverecord where enddate >  '");
        sql.append(nowDate);
        sql.append("'");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        List<ReserveRecord> list = new ArrayList<ReserveRecord>();
        while(rs.next()) {
            ReserveRecord rr = new ReserveRecord();
            rr = getReserveRecord(rs , rr);
            list.add(rr);
        }
        return list;
    }
    
    /**
     * 找到某用户某段时间的预约记录
     * @param userId
     * @param facilityId
     * @return
     * @throws Exception 
     */
    public List<ReserveRecord> findReserveRecordByUserIdAndDate(int userId, String fromDate, String toDate) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select * from fr_reserverecord where userid = ");
        sql.append(userId);
        sql.append(" and TimeStamp(startdate) >= TimeStamp('");
        sql.append(fromDate);
        sql.append("') and TimeStamp(enddate) <= TimeStamp('");
        sql.append(toDate);
        sql.append("') order by enddate desc");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        List<ReserveRecord> list = new ArrayList<ReserveRecord>();
        while (rs.next()) {            
            ReserveRecord rr = new ReserveRecord();
            rr = getReserveRecord(rs, rr);
            list.add(rr);
        }
        return list;
    }
    
    /**
     * 找到某用户没过期的预约记录
     * @param userId
     * @param nowDate
     * @return
     * @throws Exception 
     */
    public long findReserveRecordByUserIdAndNoExpiry(int userId, String nowDate) throws Exception{
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) num from fr_reserverecord where userid = ");
        sql.append(userId);
        sql.append(" and TimeStamp(startdate) >= TimeStamp('");
        sql.append(nowDate);
        sql.append("')");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {            
            return rs.getLong("num");
        }
        return 0;
    }
    
    /**
     * 找到某设备某时间段预约记录
     * @param stratdate
     * @param enddate
     * @param facilityId
     * @return
     * @throws Exception 
     */
    public List<ReserveRecord> findReserveRecordByDatesAndFacilityId(String startdate, String enddate, int facilityId) throws Exception{
        StringBuilder sql = new StringBuilder();
        if (facilityId > 0) {
            sql.append("select * from fr_reserverecord where facilityId = ");
            sql.append(facilityId);
            sql.append(" and");
        }else {
            sql.append("select * from fr_reserverecord where");
        }
        sql.append(" TimeStamp(startdate) >= TimeStamp('");
        sql.append(startdate);
        sql.append("') and TimeStamp(enddate) <= TimeStamp('");
        sql.append(enddate);
        sql.append("') order by enddate desc");
        PreparedStatement ps = getPreparedStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        List<ReserveRecord> list = new ArrayList<ReserveRecord>();
        while (rs.next()) {            
            ReserveRecord rr = new ReserveRecord();
            rr = getReserveRecord(rs, rr);
            list.add(rr);
        }
        return list;
    }
 }
