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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private int mUserTypeId = 0;
    private int mUserId = 0;

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
        mUserTypeId = Format.str2Int(request.getSession().getAttribute("userTypeId"));
        mUserId = Format.str2Int(request.getSession().getAttribute("userId"));
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
        ReserveRecord rr = mReserveRecordDao.findById(reserverecordid);
        if (null != rr) {
            if (mUserTypeId == 4) {//学生
                Date nDate = new Date();
                nDate.setTime(nDate.getTime() + Format.LONG_ONE_DAY);
                if(Format.compareDateWithDate(nDate, Format.formatDate(rr.getStartDate())) == 1) {
                    request.setAttribute("errorMsg", "无法取消，您需要提前24小时取消预约！");
                    return;
                }
            }
            int flag = mReserveRecordDao.del(rr);
            if(flag > 0) {
                request.setAttribute("errorMsg", "取消预约成功！");
            }else {
                request.setAttribute("errorMsg", "取消预约失败！");
            }
        }
        request.setAttribute("errorMsg", "预约记录已经不存在！");
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
        startdate = startdate + " " + hour[0] + ":00";//补全以适应derby数据库的timstamp函数
        enddate = enddate + " " + hour[1] + ":00";//补全以适应derby数据库的timstamp函数
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
     * @param userTypeId
     * @return 
     */
    public String isValidVacation(String fromstartdate, String fromenddate) {
        try {
            if(Format.compareDateWithDate(Format.formatDate(fromstartdate), new Date()) != 1) {
                return "开始日期要大于当前日期，请重新选择";
            }
            if(Format.compareDateWithDate(Format.formatDate(fromenddate), Format.formatDate(fromstartdate)) != 1) {
                return "结束日期要大于开始日期，请重新选择";
            }
            Date nDate = new Date();
            if (mUserTypeId == 2 || mUserTypeId == 3) {//操作员或者老师
                nDate.setTime(nDate.getTime() + Format.LONG_ONE_YEAR);
                if (Format.compareDateWithDate(nDate, Format.formatDate(fromstartdate)) == -1) {
                    return "您只能预约一年以内的设备！";
                }
            }else if(mUserTypeId == 4) {//学生
                nDate.setTime(nDate.getTime() + Format.LONG_TWO_MONTH);
                if (Format.compareDateWithDate(nDate, Format.formatDate(fromstartdate)) == -1) {
                    return "您只能预约两个月以内的设备！";
                }
                // 查下学生的预约设备数 不能超过五个
                long num = mReserveRecordDao.findReserveRecordByUserIdAndNoExpiry(mUserId, Format.formatDate(new Date()));
                if (num >= 5) {
                    return "您最多只能预约五个设备奥！";
                }
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
                if((Format.compareDateWithReserve(fromstartdate, startdate) == -1) && (Format.compareDateWithReserve(fromenddate, startdate) == -1)) {
                    flag = true;
                }
                //第二种时间段都在某假期之后
                if(((Format.compareDateWithReserve(fromstartdate, enddate) == 1) || (Format.compareDateWithReserve(fromstartdate, enddate) == 0)) && (Format.compareDateWithReserve(fromenddate, enddate) == 1)) {
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
        String monthDate = Format.null2Blank(request.getParameter("_month"));
        if(monthDate.length() > 0) {
            try {
                String nowDate = Format.formatStringToMonth(new Date());
                if(Format.compareDateWithDate(Format.formatStringToMonth(monthDate), Format.formatStringToMonth(nowDate)) == -1) {
                    String[] date = getMaxAndMinDate(Format.formatStringToMonth(monthDate), true);
                    List<ReserveRecord> list = mReserveRecordDao.findReserveRecordByDatesAndFacilityId(date[1] + " 00:00:00", date[0] + " 21:30:00", 0);//最晚21:30
                    request.setAttribute("reserverecordList", list);
                }
            } catch (Exception e) {
            }
        }
        request.setAttribute("maxdate", getMaxAndMinDate(new Date(), false)[0]);
        request.setAttribute("monthDate", monthDate);
        request.setAttribute("fid", 7);
    } 
    
    /**
     * 得到某日期的最大和最小日期
     * @param date
     * @param isSelect
     * @return 
     */
    private String[] getMaxAndMinDate(Date date, boolean isSelect) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        if(!isSelect) {
            if (calendar.getTime().getTime() != Format.formatString(Format.formatString(new Date())).getTime()) {
                calendar.add(Calendar.MONTH, -1);
            }
        }
        String maxDate = Format.formatString(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String minDate = Format.formatString(calendar.getTime());
        return new String[]{maxDate, minDate};
    }
    
    
//    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        System.out.println(Format.formatStringToMonth(calendar.getTime()));
//        calendar.add(Calendar.MONTH, -1);
//        System.out.print(Format.formatStringToMonth(calendar.getTime()));
//    }
}
