/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.services;

import edu.facilities.dao.FunctionDao;
import edu.facilities.model.Function;
import edu.facilities.utils.Format;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能管理控制类
 * 
 * @author user
 */
public class FunctionServices {

    
    public RequestDispatcher functionListInit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userTypeId = Format.null2Blank(request.getSession().getAttribute("userTypeId"));
            FunctionDao functionDao = new FunctionDao();
            List<Function> list = functionDao.findByUserTypeId(userTypeId);
            request.getSession().setAttribute("functionlist", list);
            return request.getRequestDispatcher("/WEB-INF/jsp/application.jsp");
        } catch (Exception ex) {
            Logger.getLogger(FunctionServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
    }
}
