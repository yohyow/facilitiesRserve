<%@page import="edu.facilities.model.User"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.Grade"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统-班级管理</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#_adddiv").hide();
                $("#_addgrade").click(function () {
                    $("#_adddiv").show();
                    $("#_type").val("add");
                });
                $("._delgrade").each(function() {
                    $(this).click(function() {
                        if(confirm("确定删除吗？")) {
                            $("#_type").val("del");
                            $("#_gradeid").val($(this).attr("val"));
                            $("#_form1").submit();
                        }
                    });
                });
            });
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
                <!-- InstanceBeginEditable name="EditRegion1" --> 
                <form id="_form1" action="<%=request.getContextPath()%>/gradeManager.do" method="post">
                    <ul class="personnel_top" >
                        <li class="personnel_top_sel">
                            <input type="button" id="_addgrade" value="添加班级" /><%
                                String msg = Format.null2Blank(request.getAttribute("errorMsg"));
                                if (msg.length() > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("(<span style='color:red'>");
                                    sb.append(msg);
                                    sb.append("</span>)");
                                    out.print(sb.toString());
                                }
                            %>
                        </li>
                    </ul>

                    <ul class="personnel_top" id="_adddiv">
                        <input type="hidden" name="_type" id="_type" value="" />
                        <li class="personnel_top_sel">班级：<input type="text" name="_name" maxlength="50"/><input type="submit" style="margin-left: 15px;" value="添加"/></li> 
                    </ul>
                    <div class="personnel_list">

                        <%
                            String type = Format.null2Blank(request.getAttribute("type"));
                            StringBuilder sb = new StringBuilder();
                            try {
                                List<Grade> list = (List<Grade>) request.getAttribute("gradelist");
                                sb.append("<ul>");
                                sb.append("<li>序号</li>");
                                sb.append("<li>班级</li>");
                                sb.append("<li class='action'>操作</li>");
                                sb.append("</ul>");
                                int flag = 1;
                                for (Grade grade : list) {
                                    sb.append("<ul>");
                                    sb.append("<li>");
                                    sb.append(flag++);
                                    sb.append("</li>");
                                    sb.append("<li>");
                                    sb.append(grade.getName());
                                    sb.append("</li>");
                                    sb.append("<li class='action'><a href='javascript:;' class='_delgrade' val='");
                                    sb.append(grade.getId());
                                    sb.append("'>删除</a></li>");
                                    sb.append("</ul>");
                                }
                            } catch (Exception e) {
                            }
                            out.print(sb.toString());
                        %>
                        <input type="hidden" value="" name="_gradeid" id="_gradeid"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>


