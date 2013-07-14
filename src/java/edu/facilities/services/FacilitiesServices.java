/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.services;

import edu.facilities.dao.FacilitiesInfoDao;
import edu.facilities.model.FacilitiesInfo;
import edu.facilities.utils.Format;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设备管理控制类
 * 
 * @author user
 */
public class FacilitiesServices {

    private FacilitiesInfoDao mFacilitiesInfoDao = new FacilitiesInfoDao();
    
    /**
     * 设备管理分发方法
     * @return 
     */
    public void facilitiesDispatcher(HttpServletRequest request, HttpServletResponse response) {
        String type = Format.null2Blank(request.getParameter("_type"));
        if(type.equals("add")) {
            add(request);
        }else if(type.equals("del")) {
            del(request);
        }
        int facilitiesTypeId = Format.str2Int(request.getParameter("facilitiesTypeId"));
        List<FacilitiesInfo> list = new ArrayList<FacilitiesInfo>();
        if(facilitiesTypeId > 0) {
            try {
                list = mFacilitiesInfoDao.findByTypeID(String.valueOf(facilitiesTypeId));
            } catch (Exception e) {
            }
        }
        request.setAttribute("facilitiesList", list);
        request.setAttribute("facilitiestype", facilitiesTypeId);
        request.setAttribute("fid", 2);
    }
    
    /**
     * 增加设备
     * @param request 
     */
    public void add(HttpServletRequest request) {
        String name = Format.iso2utf8(Format.null2Blank(request.getParameter("_name")));
        int typeid = Format.str2Int(request.getParameter("_facilitiestype"));
        if(name.length() > 0 && typeid > 0) {
            FacilitiesInfo facilitiesInfo = new FacilitiesInfo();
            facilitiesInfo.setName(name);
            facilitiesInfo.setFacilitiesTypeID(typeid);
            try {
                int flag = mFacilitiesInfoDao.saveOrUpdate(facilitiesInfo);
                if(flag > 0) {
                    request.setAttribute("errorMsg", "添加成功");
                }else {
                    request.setAttribute("errorMsg", " 添加失败");
                }                    
            } catch (Exception e) {
                request.setAttribute("errorMsg", "添加设备出现异常，请尝试重新添加");
            }
        }
    }
    
    /**
     * 删除
     * @param request 
     */
    public void del(HttpServletRequest request) {
        int facilitiesInfoId = Format.str2Int(request.getParameter("_facilitiesinfoid"));
        FacilitiesInfo facilitiesInfo = new FacilitiesInfo();
        facilitiesInfo.setId(facilitiesInfoId);
        try {
            int flag = mFacilitiesInfoDao.del(facilitiesInfo);
            if(flag > 0) {
                request.setAttribute("errorMsg", "删除成功");
            }else {
                request.setAttribute("errorMsg", "删除失败");
            }                
        } catch (Exception e) {
            request.setAttribute("errorMsg", "删除出现异常，请尝试重新删除");
        }
    }
            
}
