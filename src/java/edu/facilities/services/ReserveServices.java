/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.services;

import edu.facilities.dao.FacilitiesInfoDao;
import edu.facilities.dao.ReserveRecordDao;
import edu.facilities.dao.UserDao;
import edu.facilities.dao.VacationDao;
import edu.facilities.model.FacilitiesInfo;
import edu.facilities.model.ReserveRecord;
import edu.facilities.model.User;
import edu.facilities.model.Vacation;
import edu.facilities.utils.Format;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 预约管理控制类
 *
 * @author user
 */
public class ReserveServices {

    private ReserveRecordDao mReserveRecordDao = new ReserveRecordDao();
    private FacilitiesInfoDao mFacilitiesInfoDao = new FacilitiesInfoDao();
    private UserDao mUserDao = new UserDao();
    private VacationDao mVacationDao = new VacationDao();

    /**
     * 预约管理控制方法
     *
     * @param request
     * @param response
     * @return
     */
    public void reserveRecordDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String type = Format.null2Blank(request.getParameter("_type"));
        int facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
        FacilitiesInfo facilitiesInfo = null;
        if (facilitiesTypeId > 0) {
            try {
                int facilitiesInfoId = Format.str2Int(request.getParameter("facilitiesInfoId"));
                if (facilitiesInfoId > 0) {
                    List<ReserveRecord> list = mReserveRecordDao.findByFacilityID(facilitiesInfoId);
                    request.setAttribute("reserveRecordList", list);
                }
                request.setAttribute("facilitiesInfoList", mFacilitiesInfoDao.findByTypeID(String.valueOf(facilitiesTypeId)));
                request.setAttribute("facilitiesInfoId", facilitiesInfoId);
                facilitiesInfo = mFacilitiesInfoDao.findById(facilitiesInfoId);
                request.setAttribute("facilitiesInfo", facilitiesInfo);
            } catch (Exception e) {
            }
        }
        try {
            if (type.equals("add")) {
                addDispatcher(request, response, facilitiesInfo);
            }else if(type.equals("cancle")) {
                cancleDispatcher(request, response);
            }else if(type.equals("absence")) {
                absenceDispatcher(request, response);
            }else if(type.equals("noabsence")) {
                noAbsenceDispatcher(request, response);
            }
        } catch (Exception e) {
        }
        
        request.setAttribute("facilitiesTypeId", facilitiesTypeId);
        request.setAttribute("fid", 4);
        request.setAttribute("currentdate", Format.formatDate(new Date()));
    }
    /**
     * 未缺席设置
     * @param request
     * @param response
     * @throws Exception 
     */
    public void noAbsenceDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int reserverecordid = Format.str2Int(request.getParameter("_reserverecordid"));
        try {
            ReserveRecord rr = mReserveRecordDao.findById(reserverecordid);
            rr.setIsAbsence(0);//0为不缺席
            mReserveRecordDao.saveOrUpdate(rr);
            // TODO 更新用户缺席次数和时间
            User user = mUserDao.findById(rr.getUserID());
            int absenceNum = user.getAbsenceNum();
            if(absenceNum <= 0) {
                absenceNum = 0;
            }else {
            absenceNum --; 
            }
            user.setAbsenceNum(absenceNum);
            user.setAbsenceDate(mReserveRecordDao.findLastAbsenceByUserId(rr.getUserID()).getEndDate());
            mUserDao.saveOrUpdate(user);
        } catch (Exception e) {
        }
    }
    
    /**
     * 缺席设置
     * @param request
     * @param response
     * @throws Exception 
     */
    public void absenceDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int reserverecordid = Format.str2Int(request.getParameter("_reserverecordid"));
        try {
            ReserveRecord rr = mReserveRecordDao.findById(reserverecordid);
            rr.setIsAbsence(1);//1为缺席
            mReserveRecordDao.saveOrUpdate(rr);
            // TODO 更新用户缺席次数和时间
            User user = mUserDao.findById(rr.getUserID());
            int absenceNum = user.getAbsenceNum() + 1;
            if(absenceNum >= 3) {
                user.setIsValid(1);
            }
            user.setAbsenceNum(absenceNum);
            user.setAbsenceDate(rr.getEndDate());
            mUserDao.saveOrUpdate(user);
        } catch (Exception e) {
        }
    }
    
    /**
     * 取消预约方法
     * @param request
     * @param response
     * @throws Exception 
     */
    public void cancleDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int reserverecordid = Format.str2Int(request.getParameter("_reserverecordid"));
        ReserveRecord rr = new ReserveRecord();
        rr.setId(reserverecordid);
        try {
            int flag = mReserveRecordDao.del(rr);
        } catch (Exception e) {
        }
    }
    
    /**
     * 增加预约方法
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    public void addDispatcher(HttpServletRequest request, HttpServletResponse response, FacilitiesInfo facilitiesInfo) throws Exception{
        String startdate = Format.null2Blank(request.getParameter("startdate"));
        String enddate = Format.null2Blank(request.getParameter("enddate"));
        String[] hour = request.getParameterValues("_hour");
        startdate = startdate + " " + hour[0];
        enddate = enddate + " " + hour[1];
        String validStr = isValidVacation(startdate, enddate);
        if(validStr.equals("true") && null != facilitiesInfo) {
            ReserveRecord rr = new ReserveRecord();
            rr.setCreateDate(Format.formatDate(new Date()));
            rr.setEndDate(enddate);
            rr.setFacilityID(facilitiesInfo.getId());
            rr.setFacilityName(facilitiesInfo.getName());
            rr.setIsAbsence(0);
            rr.setStartDate(startdate);
            rr.setUserID(Format.str2Int(request.getSession().getAttribute("userId")));
            rr.setUserName(Format.null2Blank(request.getSession().getAttribute("userName")));
            try {
                mReserveRecordDao.saveOrUpdate(rr);
            } catch (Exception e) {
            }
        }else {
            request.setAttribute("errorMsg", validStr);
        }
    }
    
    /**
     * 是否为有效日期
     * @param request
     * @param response
     * @return 
     */
    public String isValidVacation(String fromstartdate, String fromenddate) {
        try {
            if(Format.compareDateWithDate(Format.formatStringWithHour(fromstartdate), new Date()) != 1) {
                return "开始日期要大于当前日期，请重新选择";
            }
            if(Format.compareDateWithDate(Format.formatStringWithHour(fromenddate), Format.formatStringWithHour(fromstartdate)) != 1) {
                return "结束日期要大于开始日期，请重新选择";
            }
            List<Vacation> list = mVacationDao.findAll();
            for(Vacation vacation: list) {
                boolean flag = false;
                String startdate = vacation.getStartDate();
                String enddate = vacation.getEndDate();
                //第一种时间段都在某假期开始前
                if((Format.compareDate(fromstartdate, startdate) == -1) && (Format.compareDate(fromenddate, startdate) == -1)) {
                    flag = true;
                }
                //第二种时间段都在某假期之后
                if((Format.compareDate(fromstartdate, enddate) == 1) && (Format.compareDate(fromenddate, enddate) == 1)) {
                    flag = true;
                }
                if(!flag) {
                    return "您选择的日期包含【" + vacation.getName() + "(" + vacation.getStartDate() + " ~ " + vacation.getEndDate() + ")】 请重新选择";
                }
            }
            List<ReserveRecord> reserveRecords = mReserveRecordDao.findRserveRecordByNowDate(Format.formatDate(new Date()));
            for (ReserveRecord reserveRecord : reserveRecords) {
                boolean flag = false;
                String startdate = reserveRecord.getStartDate();
                String enddate = reserveRecord.getEndDate();
                //第一种时间段都在某假期开始前
                if((Format.compareDateWithHour(fromstartdate, startdate) == -1) && (Format.compareDateWithHour(fromenddate, startdate) == -1)) {
                    flag = true;
                }
                //第二种时间段都在某假期之后
                if(((Format.compareDateWithHour(fromstartdate, enddate) == 1) || (Format.compareDateWithHour(fromstartdate, enddate) == 0)) && (Format.compareDateWithHour(fromenddate, enddate) == 1)) {
                    flag = true;
                }
                if(!flag) {
                    return "【" + reserveRecord.getUserName() + "】 在【" + reserveRecord.getStartDate() + " ~ " + reserveRecord.getEndDate() + "】已预约";
                }
            }
        } catch (Exception e) {
        }
        return "true";
    }
    
    /**
     * 设备使用统计 控制方法
     * @param request
     * @param response
     * @return 
     */
    public void reportDispatcher(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("fid", 7);
    }
}
