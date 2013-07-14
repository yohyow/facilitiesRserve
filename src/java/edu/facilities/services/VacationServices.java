/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.services;

import edu.facilities.dao.VacationDao;
import edu.facilities.model.Vacation;
import edu.facilities.utils.Format;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  假期管理控制类
 *
 * @author user
 */
public class VacationServices {
    
    private VacationDao mVacationDao = new VacationDao();
    /**
     * 假期管理控制方法
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    public void vacationDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String type = Format.null2Blank(request.getParameter("_type"));
            if (type.equals("add")) {
               addDispatcher(request, response);
            }else if(type.equals("del")) {
                delDispatcher(request, response);
            }else if(type.equals("valid")) {
                
            }
            List<Vacation> list = mVacationDao.findAll();
            request.setAttribute("vacationlist", list);
        } catch (Exception e) {
        }
        request.setAttribute("fid", 4);
    }
    
    /**
     * 删除假期
     * @param request
     * @param response 
     */
    public void delDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            int gradeId = Format.str2Int(request.getParameter("_vacationid"));
            Vacation vacation = new Vacation();
            vacation.setId(gradeId);
            int flag = mVacationDao.del(vacation);
            if(flag == 0) {
                request.setAttribute("errorMsg", "删除失败！");
            }
            request.setAttribute("errorMsg", "删除成功！");
        } catch (Exception e) {
        }
    }
    
    /**
     * 增加假期
     * @param request
     * @param response
     * @throws Exception 
     */
    public void addDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String name = Format.iso2utf8(Format.null2Blank(request.getParameter("_name")));
            String fromstartdate = Format.null2Blank(request.getParameter("startdate"));
            String fromenddate = Format.null2Blank(request.getParameter("enddate"));
            int isExist = mVacationDao.findByNameIsExist(name);
            if(isExist == 1) {
                request.setAttribute("errorMsg", "假期已经添加过！");
            }else {
                Vacation vacation = new Vacation();
                vacation.setName(name);
                vacation.setStartDate(fromstartdate);
                vacation.setEndDate(fromenddate);
                int status = mVacationDao.saveOrUpdate(vacation);
                if(status > 0) {
                    request.setAttribute("errorMsg", "添加成功！");
                }else {
                    request.setAttribute("errorMsg", "添加失败！");
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", "添加失败！");
        }
    }
}
