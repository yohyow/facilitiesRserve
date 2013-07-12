<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.Function"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="header">
    <div class="logo">体育设施预约系统</div>
    <p class="hello">您好：<%=request.getSession().getAttribute("userName")%>&nbsp;&nbsp;&nbsp;<a class="hello removedownline" href="<%=request.getContextPath()%>/logout.do">退出</a></p>
</div>
<div class="menu">
    <ul>                                                                         
        <%
            List<Function> list = (List<Function>) request.getSession().getAttribute("functionlist");
            int fid = Format.str2Int(request.getAttribute("fid"));
            if (null != list && list.size() > 0) {
                for (Function function : list) {
                    StringBuilder sb = new StringBuilder();
                    if (function.getId() == fid) {
                        sb.append("<li><a class='selected' href='");
                    } else {
                        sb.append("<li><a href='");
                    }
                    sb.append(request.getContextPath());
                    sb.append("/");
                    sb.append(function.getUrl());
                    sb.append("'>");
                    sb.append(function.getName());
                    sb.append("</a></li>");
                    out.print(sb.toString());
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.do");
            }
        %>
    </ul>
</div>

