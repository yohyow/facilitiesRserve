/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.services;

import edu.facilities.dao.FacilitiesInfoDao;
import edu.facilities.dao.GradeDao;
import edu.facilities.dao.ReserveRecordDao;
import edu.facilities.dao.UserDao;
import edu.facilities.model.ReserveRecord;
import edu.facilities.utils.Format;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
        request.setAttribute("fid", 6);
        try {
            request.setAttribute("gradeList", mGradeDao.findAll());
        } catch (Exception e) {
        }
        request.setAttribute("type", type);
    }

    /**
     * 查询某用户某设备预约记录
     *
     * @param request
     * @param response
     */
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        int _gradeId = Format.str2Int(request.getParameter("_grade"));
        int _usertypeId = Format.str2Int(request.getParameter("_usertype"));
        int _userId = Format.str2Int(request.getParameter("_user"));
        int _facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
        int _facilitiesInfoId = Format.str2Int(request.getParameter("facilitiesInfoId"));
        try {
            if (_usertypeId > 0 && _gradeId > 0) {
                request.setAttribute("userList", mUserDao.findByGradeIDAndUserTypeID(String.valueOf(_gradeId), String.valueOf(_usertypeId)));
            }
            if (_facilitiesTypeId > 0) {
                request.setAttribute("facilitiesInfoList", mFacilitiesInfoDao.findByTypeID(String.valueOf(_facilitiesTypeId)));
            }
            if (_userId > 0 && _facilitiesInfoId > 0) {
                List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByUserIdAndFacilityId(_userId, _facilitiesTypeId);
                request.setAttribute("reserverecordlist", list);
            }
        } catch (Exception e) {
        }
        request.setAttribute("gradeId", _gradeId);
        request.setAttribute("userTypeId", _usertypeId);
        request.setAttribute("userInfoId", _userId);
        request.setAttribute("facilitiesTypeId", _facilitiesTypeId);
        request.setAttribute("facilitiesInfo", _facilitiesInfoId);
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
                if (Format.compareDateWithDate(Format.formatString(fromStartDate), Format.formatString(fromEndDate)) == -1) {
                    List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByDatesAndFacilityId(fromStartDate, fromEndDate, _facilitiesInfoId);
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
