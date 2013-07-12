/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.services;

import edu.facilities.dao.GradeDao;
import edu.facilities.dao.UserDao;
import edu.facilities.model.Grade;
import edu.facilities.model.User;
import edu.facilities.utils.Format;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理控制类
 * 
 * @author user
 */
public class UserServices {

    private UserDao mUserDao = new UserDao();
    private GradeDao mGradeDao = new GradeDao();
    /**
     * 登录
     * 
     * @param request
     * @param response
     * @return RequestDispatcher
     */
    public RequestDispatcher login(HttpServletRequest request, HttpServletResponse response) {
        String account = Format.null2Blank(request.getParameter("account"));
        String password = Format.null2Blank(request.getParameter("password"));
        User user = null;
        try {
            user = mUserDao.findByAccount(account);
        } catch (Exception e) {
        }
        if(null != user) {
            if(user.getIsValid() == 0) {
                request.setAttribute("errorMsg", "对不起，您的帐号无效!");
            }else if(user.getPassword().equals(password)) {
                request.getSession().setAttribute("userId", user.getId());
                request.getSession().setAttribute("userName", user.getName());
                return request.getRequestDispatcher("/application.do");
            }
        }else {
            request.setAttribute("errorMsg", "用户名或者密码不正确！");
        }
        return request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
    }
    
    /**
     * 退出系统
     * @param request
     * @param response
     * @return 
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) {
            request.getSession().removeAttribute("userId");
            request.getSession().removeAttribute("userName");
            return "/login.do";
    }
    
    /**
     * 用户管理控制方法
     * @param request
     * @param response
     * @return 
     */
    public RequestDispatcher userDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = Format.null2Blank(request.getParameter("_type"));
            if(type.equals("select")) {
                selectUserDispatcher(request, response);
            }else if(type.equals("add")) {
                addOrEditUserDispatcher(request, response);
                selectUserDispatcher(request, response);
            }else if(type.equals("edit")) {
                request.setAttribute("type", "add");
                findUserByUserId(request,response);
            }else if(type.equals("del")) {
                delUserDispatcher(request, response);
                selectUserDispatcher(request, response);
            }
            
            List<Grade> list = mGradeDao.findAll();
            request.setAttribute("gradelist", list);
            
            String _grade = Format.null2Blank(request.getParameter("_grade"));
            String _usertype = Format.null2Blank(request.getParameter("_usertype"));
            request.setAttribute("gradeid", _grade);
            if (_usertype.equals("1")) {
                request.setAttribute("usertype", "管理员");
            }else if(_usertype.equals("2")) {
                request.setAttribute("usertype", "操作员");
            }else if(_usertype.equals("3")) {
                request.setAttribute("usertype", "老师");
            }else if(_usertype.equals("4")) {
                request.setAttribute("usertype", "学生");
            }
        } catch (Exception e) {
        }
        request.setAttribute("fid", 1);
        return request.getRequestDispatcher("/WEB-INF/jsp/userManager.jsp");
    }
    
    /**
     * 查询用户集合
     * @param request
     * @param response 
     */
    public void selectUserDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String _grade = Format.null2Blank(request.getParameter("_grade"));
        String _usertype = Format.null2Blank(request.getParameter("_usertype"));
        try {
            List<User> list = mUserDao.findByGradeIDAndUserTypeID(_grade, _usertype);
            request.setAttribute("userlist", list);
            request.setAttribute("type", "select");
        } catch (Exception e) {
        }
    }
    
    /**
     * 根据用户id查询用户
     * @param request
     * @param response 
     */
    public void findUserByUserId(HttpServletRequest request, HttpServletResponse response) {
        int userid = Format.str2Int(request.getParameter("_userid"));
        try {
            User user = mUserDao.findById(userid);
            request.setAttribute("user", user);
        } catch (Exception e) {
        }
    }
    
    /**
     * 添加用户
     * @param request
     * @param response 
     */
    public void addOrEditUserDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            int userid = Format.str2Int(request.getParameter("_userid"));
            String account = Format.null2Blank(request.getParameter("account"));
            String password = Format.null2Blank(request.getParameter("password"));
            String name = Format.iso2utf8(Format.null2Blank(request.getParameter("name")));
            String absenceDate = Format.null2Blank(request.getParameter("absenceDate"));
            int absenceNum = Format.str2Int(request.getParameter("absenceNum"));
            int _usertype1 = Format.str2Int(request.getParameter("_usertype1"));
            int _grade1 = Format.str2Int(request.getParameter("_grade1"));
            if(_grade1 > 0 && _usertype1 > 0) {
                User user = new User();
                if(userid > 0) {
                    user.setId(userid);
                }
                int _isvalid = Format.str2Int(request.getParameter("_isvalid"));
                user.setName(name);
                user.setAccount(account);
                user.setPassword(password);
                user.setGradeID(_grade1);
                user.setUserTypeID(_usertype1);
                user.setAbsenceNum(absenceNum);
                user.setAbsenceDate(absenceDate);
                user.setIsValid(_isvalid);
                user.setCreateDate(Format.formatDate(new Date()));
                mUserDao.saveOrUpdate(user);
            }
        } catch (Exception e) {
            
        }
    }
    
    /**
     * 删除用户
     * @param request
     * @param response 
     */
    public void delUserDispatcher(HttpServletRequest request, HttpServletResponse response) {
        try {
            int userid = Format.str2Int(request.getParameter("_userid"));
            User user = new User();
            user.setId(userid);
            mUserDao.del(user);
        } catch (Exception e) {
        }
    }
}
