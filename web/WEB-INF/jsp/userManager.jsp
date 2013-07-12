<%@page import="edu.facilities.model.User"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.Grade"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="all" />
        <script src="js/jquery.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui-i18n.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#_absencedate").datepicker();
                $("#_absencedate").datepicker( "option", "dateFormat", "yy-mm-dd");
                $("#_selectuser").click(function () {
                    $("#_type").val("select");//查询按钮点击后类型为select
                    $("#_form1").submit();
                });
                $("#_adduser").click(function () {
                    $("#_type").val("add");//添加用户按钮点击后类型为add
                    $("#_adddiv").show();
                    $("#_selectul").hide();
                    $("#_selectlistdiv").hide();
                });
                var usertype = "<%=Format.null2Blank(request.getAttribute("usertype"))%>";
                $("select[@name='_usertype'] option").each(function() {
                    if($(this).text() == usertype) {
                        $(this).attr("selected", "selected");
                    } 
                });
                var type = "<%=Format.null2Blank(request.getAttribute("type"))%>";
                if(type == "edit" || type == 'add') {
                    $("#_adddiv").show();
                    $("#_selectul").hide();
                    $("#_selectlistdiv").hide();
                }else {
                    $("#_adddiv").hide();
                    $("#_selectul").show();
                    $("#_selectlistdiv").show();
                }
                $("#_submit").click(function (){
                    
                });
            });
            function edit(_id) {
                $("#_type").val("edit");//添加用户按钮点击后类型为edit
                $("#_userid").val(_id);
                $("#_form1").submit();
            }
            function del(_id) {
                $("#_type").val("del");//添加用户按钮点击后类型为edit
                $("#_userid").val(_id);
                $("#_form1").submit();
            }
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
                <form id="_form1" action="<%=request.getContextPath()%>/userManager.do" method="post">
                    <ul class="personnel_top" id="_selectul">
                        <li class="personnel_top_sel">
                            <label>班级:</label>
                            <select id="_grade" name="_grade" class="personnel_width">
                                <%
                                    try {
                                        List<Grade> list = (List<Grade>) request.getAttribute("gradelist");
                                        StringBuilder sb = new StringBuilder();
                                        int gradeid = Format.str2Int(request.getAttribute("gradeid"));
                                        for (Grade grade : list) {
                                            sb.append("<option value='");
                                            sb.append(grade.getId());
                                            if (grade.getId() == gradeid) {
                                                sb.append("' selected='selected");
                                            }
                                            sb.append("'>");
                                            sb.append(grade.getName());
                                            sb.append("</>");
                                        }
                                        out.print(sb.toString());
                                    } catch (Exception e) {
                                    }
                                %>
                            </select>
                            <input type="hidden" id="_type" name="_type" value="<%=request.getAttribute("type")%>" />
                        </li>
                        <li class="personnel_top_sel">
                            <label>用户类型:</label>
                            <select name="_usertype" class="personnel_width">
                                <option value="2">操作员</option>
                                <option value="3">老师</option>
                                <option value="4">学生</option>
                            </select>
                        </li>
                        <li class="personnel_top_sel">
                            <input type="button" id="_selectuser" value="查询" />
                        </li>
                        <li class="personnel_top_sel">
                            <input type="button" id="_adduser" value="添加新用户" />
                        </li>
                    </ul>
                    <div class="personnel_list" id="_selectlistdiv">
                        
                            <%
                                String type = Format.null2Blank(request.getAttribute("type"));
                                StringBuilder sb = new StringBuilder();
                                if (type.equals("select")) {
                                    try {
                                        List<User> list = (List<User>) request.getAttribute("userlist");
                                        sb.append("<ul>");
                                        sb.append("<li>姓名</li>");
                                        sb.append("<li>类型</li>");
                                        sb.append("<li>缺席次数</li>");
                                        sb.append("<li class='action'>操作</li>");
                                        sb.append("</ul>");
                                        for (User _user : list) {
                                            sb.append("<ul>");
                                            sb.append("<li>");
                                            sb.append(_user.getName());
                                            sb.append("</li>");
                                            sb.append("<li>");
                                            sb.append(Format.null2Blank(request.getAttribute("usertype")));
                                            sb.append("</li>");
                                            sb.append("<li>");
                                            sb.append(_user.getAbsenceNum());
                                            sb.append("</li>");
                                            sb.append("<li class='action'>");
                                            sb.append("<a href='javascript:edit(");
                                            sb.append(_user.getId());
                                            sb.append(")' >编辑</a>&nbsp;&nbsp;&nbsp;<a href='javascript:del(");
                                            sb.append(_user.getId());
                                            sb.append(")' >删除</a></li>");
                                            sb.append("</ul>");
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                out.print(sb.toString());
                            %>
                    </div>   
                    <div class="personnel" id='_adddiv'>
                        <%User user = null;
                            try {
                                user = (User) request.getAttribute("user");
                            } catch (Exception e) {
                            }
                            if (null == user) {
                                user = new User();
                            }
                        %>
                        <input type="hidden" name="_userid" id="_userid" value="<%=user.getId()%>"/>
                        <div class="login_form_row">
                            <label class="login_label">姓名:</label><input type="text" name="name" class="login_input" value="<%=Format.null2Blank(user.getName())%>" />
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">账号:</label><input type="text" name="account" value="<%=Format.null2Blank(user.getAccount())%>" class="login_input"/>
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">密码:</label><input type="text" name="password" class="login_input" value="<%=Format.null2Blank(user.getPassword())%>" />
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">缺席次数:</label><input type="text" name="absenceNum" class="login_input" value="<%=user.getAbsenceNum()%>" />
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">缺席日期:</label><input type="text" id="_absencedate" name="absenceDate" class="login_input" value="<%=Format.null2Blank(user.getAbsenceDate())%>" />
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">班级:</label>
                            <select name="_grade1" class="select_width">
                                <%
                                    try {
                                        List<Grade> list1 = (List<Grade>) request.getAttribute("gradelist");
                                        StringBuilder sb1 = new StringBuilder();
                                        for (Grade grade : list1) {
                                            sb1.append("<option value='");
                                            sb1.append(grade.getId() == user.getGradeID() ? (grade.getId() + "' selected='selected") : grade.getId());
                                            sb1.append("'>");
                                            sb1.append(grade.getName());
                                            sb1.append("</>");
                                        }
                                        out.print(sb1.toString());
                                    } catch (Exception e) {
                                    }
                                %>
                            </select>
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">用户类型:</label>
                            <select name="_usertype1" class="select_width">
                                <option value="2" <%String usertype = user.getUserTypeID() == 2 ? "selected='selected'" : "";%>>操作员</option>
                                <option value="3" <%usertype = user.getUserTypeID() == 3 ? "selected='selected'" : "";%>>老师</option>
                                <option value="4" <%usertype = user.getUserTypeID() == 4 ? "selected='selected'" : "";%>>学生</option>
                            </select>
                        </div>
                        <div class="login_form_row">
                            <label class="login_label">是否有效:</label>
                            <select name="_isvalid" class="select_width">
                                <option value="1" <%=(user.getIsValid() == 1 && (Format.null2Blank(user.getAccount()).length() > 0)) ? "selected = 'selected'" : ""%>>有效</option>
                                <option value="0" <%=(user.getIsValid() == 0 && (Format.null2Blank(user.getAccount()).length() > 0)) ? "selected = 'selected'" : ""%>>无效</option>
                            </select>
                        </div>
                        <div class="submit">
                            <input type="image" src="css/images/submit.png"  id="_submit" class="submit_btn" />
                        </div>
                    </div>
                </form>
            </div> 
        </div>
    </body>
</html>


