/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.facilities.servlet.view;

import edu.facilities.utils.Format;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础servlet 继承此类
 *
 * @author user
 */
public class BaseServletManager extends HttpServlet {

    
    public String sessionRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (Format.null2Blank(request.getSession().getAttribute("userId")).length() <= 0) {
            return request.getContextPath() + "/login.do";
        }else {
            return "";
        }
    }
}
