/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.services;

import edu.facilities.dao.ReserveRecordDao;
import edu.facilities.dao.UserDao;
import edu.facilities.model.ReserveRecord;
import edu.facilities.utils.Format;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.derby.impl.store.replication.net.SlaveAddress;

/**
 * 预约记录查询控制类
 *
 * @author user
 */
public class ReserveFindServices {
    
    private ReserveRecordDao mReserveRecordDao = new ReserveRecordDao();
    private UserDao mUserDao = new UserDao();
    
    /**
     * 预约记录查询控制方法
     * @param request
     * @param response
     * @return 
     */
    public RequestDispatcher reserveFindDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String type = Format.null2Blank(request.getParameter("_type"));
        if (type.equals("selectuser")) {
           selectUser(request, response);
        }else if(type.equals("selectfacility")) {
            selectFacility(request, response);
        }
        request.setAttribute("fid", 6);
        return request.getRequestDispatcher("/WEB-INF/jsp/reserveFind.jsp");
    }
    
    /**
     * 查询某用户某设备预约记录
     * 
     * @param request
     * @param response 
     */
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            int _usertypeId = Format.str2Int(request.getParameter("_usertype"));
            int _userId = Format.str2Int(request.getParameter("_userId"));
            int _facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
            int _facilitiesInfoId = Format.str2Int(request.getParameter("facilitiesInfoId"));
            if (_userId > 0 && _facilitiesInfoId > 0) {
                List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByUserIdAndFacilityId(_userId, _facilitiesTypeId);
                request.setAttribute("reserverecordlist", list);
            }
            request.setAttribute("userTypeId", _usertypeId);
            request.setAttribute("userInfoId", _userId);
            request.setAttribute("facilitiesTypeId", _facilitiesTypeId);
            request.setAttribute("facilitiesInfo", _facilitiesInfoId);
        } catch (Exception e) {
        }
    }
    
    /**
     * 查询某设备某段时间预约记录
     * 
     * @param request
     * @param response 
     */
    public void selectFacility(HttpServletRequest request, HttpServletResponse response) {
        try {
            int _facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
            int _facilitiesInfoId = Format.str2Int(request.getParameter("facilitiesInfoId"));
            String fromStartDate = Format.null2Blank(request.getParameter("startDate"));
            String fromEndDate = Format.null2Blank(request.getParameter("enddate"));
            if (_facilitiesInfoId > 0 && fromStartDate.length() > 0 && fromEndDate.length() > 0) {
                if(Format.compareDateWithDate(Format.formatString(fromStartDate), Format.formatString(fromEndDate)) == -1) {
                    List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByDatesAndFacilityId(fromStartDate, fromEndDate, _facilitiesTypeId);
                    request.setAttribute("reserverecordlist", list);
                }
            }
            request.setAttribute("facilitiesTypeId", _facilitiesTypeId);
            request.setAttribute("facilitiesInfo", _facilitiesInfoId);
            request.setAttribute("startdate", fromStartDate);
            request.setAttribute("enddate", fromEndDate);
        } catch (Exception e) {
        }
    }
}
