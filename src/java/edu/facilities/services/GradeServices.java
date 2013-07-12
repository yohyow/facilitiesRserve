/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.services;

import edu.facilities.dao.GradeDao;
import edu.facilities.model.Grade;
import edu.facilities.utils.Format;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 班级管理控制类
 * 
 * @author user
 */
public class GradeServices {

    private GradeDao mGradeDao = new GradeDao();
    /**
     * 返回所有班级集合供
     * @return
     * @throws Exception 
     */
    public List<Grade> findAll() throws Exception{
        return mGradeDao.findAll();
    }
    
    /**
     * 班级查询添加控制
     * @param request
     * @param response
     * @return 
     */
    public void GradeDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = Format.null2Blank(request.getParameter("_type"));
            if (type.equals("add")) {
               addDispatcher(request, response);
            }else if(type.equals("del")) {
                delDispatcher(request, response);
            }
            List<Grade> list = mGradeDao.findAll();
            request.setAttribute("gradelist", list);
        } catch (Exception e) {
        }
        request.setAttribute("fid", 2);
    }
    
    /**
     * 添加班级
     * @param request
     * @param response
     * @return 
     */
    public void addDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = Format.iso2utf8(Format.null2Blank(request.getParameter("_name")));
            int isExist = mGradeDao.findByNameIsExist(name);
            if(isExist == 1) {
                request.setAttribute("errorMsg", "班级已经添加过！");
            }else {
                Grade grade = new Grade();
                grade.setName(name);
                int status = mGradeDao.saveOrUpdate(grade);
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
    
    /**
     * 删除班级
     * @param request
     * @param response 
     */
    public void delDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            int gradeId = Format.str2Int(request.getParameter("_gradeid"));
            Grade grade = new Grade();
            grade.setId(gradeId);
            int flag = mGradeDao.del(grade);
            if(flag == 0) {
                request.setAttribute("errorMsg", "删除失败！");
            }
            request.setAttribute("errorMsg", "删除成功！");
        } catch (Exception e) {
        }
    }
}
