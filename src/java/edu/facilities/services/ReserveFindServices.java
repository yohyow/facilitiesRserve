/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.services;

import edu.facilities.dao.FacilitiesInfoDao;
import edu.facilities.dao.GradeDao;
import edu.facilities.dao.ReserveRecordDao;
import edu.facilities.dao.UserDao;
import edu.facilities.model.Grade;
import edu.facilities.model.ReserveRecord;
import edu.facilities.utils.Format;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 预约记录查询控制类
 *
 * @author user
 */
public class ReserveFindServices {

    private ReserveRecordDao mReserveRecordDao = new ReserveRecordDao();
    private UserDao mUserDao = new UserDao();
    private GradeDao mGradeDao = new GradeDao();
    private FacilitiesInfoDao mFacilitiesInfoDao = new FacilitiesInfoDao();

    /**
     * 预约记录查询控制方法
     *
     * @param request
     * @param response
     * @return
     */
    public void reserveFindDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String type = Format.null2Blank(request.getParameter("_type"));
        if (type.equals("selectuser")) {
            selectUser(request, response);
        } else if (type.equals("selectfacility")) {
            selectFacility(request, response);
        }
        request.setAttribute("fid", 7);
        try {
            List<Grade> list = new ArrayList<Grade>();
            int currentUserType = Format.str2Int(request.getSession().getAttribute("userTypeId"));
            if (currentUserType == 2 || currentUserType == 3) {
                int userId = Format.str2Int(request.getSession().getAttribute("userId"));
                Grade grade = mGradeDao.findById(mUserDao.findById(userId).getGradeID());
                list.add(grade);
            }else if(currentUserType == 1) {
                list = mGradeDao.findAll();
            }
            request.setAttribute("gradeList", list);
        } catch (Exception e) {
        }
        request.setAttribute("type", type);
    }

    /**
     * 查询某用户某段时间预约记录
     *
     * @param request
     * @param response
     */
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        int _gradeId = Format.str2Int(request.getParameter("_grade"));
        int _usertypeId = Format.str2Int(request.getParameter("_usertype"));
        int _userId = Format.str2Int(request.getParameter("_user"));
        String fromStartDate = Format.null2Blank(request.getParameter("startdate"));
        String fromEndDate = Format.null2Blank(request.getParameter("enddate"));
        try {
            if (_usertypeId > 0 && _gradeId > 0) {
                request.setAttribute("userList", mUserDao.findByGradeIDAndUserTypeID(String.valueOf(_gradeId), String.valueOf(_usertypeId)));
            }
            
            if (_userId > 0 && fromStartDate.length() > 0 && fromEndDate.length() > 0) {
                //如果开始时间小于结束时间
                if (Format.compareDateWithDate(Format.formatString(fromStartDate), Format.formatString(fromEndDate)) == -1) {
                    List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByUserIdAndDate(_userId, fromStartDate + " 00:00:00", fromEndDate  + " 00:00:00");
                    request.setAttribute("reserverecordlist", list);
                }
            }
        } catch (Exception e) {
        }
        request.setAttribute("gradeId", _gradeId);
        request.setAttribute("userTypeId", _usertypeId);
        request.setAttribute("userInfoId", _userId);
        request.setAttribute("startdate", fromStartDate);
        request.setAttribute("enddate", fromEndDate);
    }

    /**
     * 查询某设备某段时间预约记录
     *
     * @param request
     * @param response
     */
    public void selectFacility(HttpServletRequest request, HttpServletResponse response) {
        int _facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
        int _facilitiesInfoId = Format.str2Int(request.getParameter("facilitiesInfoId"));
        String fromStartDate = Format.null2Blank(request.getParameter("startdate"));
        String fromEndDate = Format.null2Blank(request.getParameter("enddate"));
        try {
            if (_facilitiesTypeId > 0) {
                request.setAttribute("facilitiesInfoList", mFacilitiesInfoDao.findByTypeID(String.valueOf(_facilitiesTypeId)));
            }
            if (_facilitiesInfoId > 0 && fromStartDate.length() > 0 && fromEndDate.length() > 0) {
                //如果开始时间小于结束时间
                if (Format.compareDateWithDate(Format.formatString(fromStartDate), Format.formatString(fromEndDate)) == -1) {
                    List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByDatesAndFacilityId(fromStartDate + " 00:00:00", fromEndDate + " 00:00:00", _facilitiesInfoId);
                    request.setAttribute("reserverecordlist", list);
                }
            }
        } catch (Exception e) {
        }
        request.setAttribute("facilitiesTypeId", _facilitiesTypeId);
        request.setAttribute("facilitiesInfo", _facilitiesInfoId);
        request.setAttribute("startdate", fromStartDate);
        request.setAttribute("enddate", fromEndDate);
    }
}
