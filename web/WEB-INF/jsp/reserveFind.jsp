<%@page import="edu.facilities.model.FacilitiesInfo"%>
<%@page import="edu.facilities.model.ReserveRecord"%>
<%@page import="edu.facilities.model.User"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.Grade"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统 - 预约记录查询</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="all" />
        <script src="js/jquery.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui-i18n.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                var type = '<%=Format.null2Blank(request.getAttribute("type"))%>';
                //根据servlet传递过来的type选择是显示用户查询还是设备查询
                if(type == "") {//默认显示用户选择
                    selectuser();
                    $("#_type").val("selectuser");
                }else if(type == 'selectuser'){
                    selectuser();
                    $("#_type").val(type);
                }else {
                    selectfacility();
                    $("#_type").val(type);
                }
                //给id为_startdate的添加日期选择控件
                $("#_startdate").datepicker();
                //格式化日期格式yy-mm-dd
                $("#_startdate").datepicker( "option", "dateFormat", "yy-mm-dd");
                $("#_enddate").datepicker();
                $("#_enddate").datepicker( "option", "dateFormat", "yy-mm-dd");
                var startdate = '<%=Format.null2Blank(request.getAttribute("startdate"))%>';
                var enddate = '<%=Format.null2Blank(request.getAttribute("enddate"))%>';
                //记录用户的选择日期
                $("#_startdate").val(startdate);
                $("#_enddate").val(enddate);
                //给用户类型添加选择监听 下拉列表值改变时更改类型为selectuser并提交form
                $("#li_usertype").change(function() {
                    $("#_type").val("selectuser");
                    $("#_form1").submit();
                });
                //设备类型更改时提交form
                 $("#select_facilitiesTypeId").change(function() {
                    $("#_form1").submit();
                });
//                $("#select_facilitiesInfoId").change(function() {
//                    $("#_form1").submit();
//                });
            });
            
            function selectuser() {
                //隐藏设备类型 、 设备两个select下拉列表
                $("#li_facilitiestype").hide();
                $("#li_facilityinfo").hide();
                //显示用户类型、用户、班级下拉列表
                $("#li_usertype").show();
                $("#li_user").show();
                $("#li_grade").show();
                $("#_type").val("selectuser");//查询按钮点击后类型为selectuser
            }
            function selectfacility() {
                //隐藏用户类型、用户、班级下拉列表
                $("#li_usertype").hide();
                $("#li_user").hide();
                $("#li_grade").hide();
                //显示设备类型 、 设备两个select下拉列表
                $("#li_facilitiestype").show();
                $("#li_facilityinfo").show();
//                $("#_selectfacilities").show();
                $("#_type").val("selectfacility");//查询按钮点击后类型为selectfacility
            }
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
                <form id="_form1" action="<%=request.getContextPath()%>/reserveFind.do" method="post">
                <input type="hidden" id="_type" name="_type" value="<%=Format.null2Blank(request.getAttribute("type"))%>" />
                <ul class="personnel_top">
                    <li class="personnel_top_sel"><label><a href="javascript:selectuser();" class="removedownline">用户预约查询</a></label></li>
                    <li class="personnel_top_sel"><label><a href="javascript:selectfacility();" class="removedownline">设备预约查询</a></label></li>
                </ul>
                    <ul class="personnel_top" id="_selectul">
                        <%
                            int userTypeId = Format.str2Int(request.getAttribute("userTypeId"));
                            int userInfoId = Format.str2Int(request.getAttribute("userInfoId"));
                            int gradeId = Format.str2Int(request.getAttribute("gradeId"));
                        %>
                        <li class="personnel_top_sel" id="li_grade">
                            <label>班级:</label>
                            <select name="_grade" class="personnel_width">
                                <option value="0">请选择</option>
                                <%
                                    List<Grade> gradeList = null;
                                    StringBuilder sb_gradeInfo = new StringBuilder();
                                    try{
                                        gradeList = (List<Grade>)request.getAttribute("gradeList");
                                        for(Grade g: gradeList) {
                                            sb_gradeInfo.append("<option value='");
                                            sb_gradeInfo.append(g.getId());
                                            sb_gradeInfo.append("' ");
                                            if(gradeId == g.getId()) {
                                                sb_gradeInfo.append("selected='selected' />");
                                            }else {
                                                sb_gradeInfo.append(" />");
                                            }
                                            sb_gradeInfo.append(g.getName());
                                            sb_gradeInfo.append("</option>");
                                        }
                                    }catch(Exception e){
                                    }
                                    out.print(sb_gradeInfo.toString());
                                %>
                            </select>
                        </li>
                        <li class="personnel_top_sel" id="li_usertype">
                            <label>用户类型:</label>
                            <select name="_usertype" class="personnel_width">
                                <option value="0" <%=userTypeId == 0 ? "selected = 'selected'":""%>>请选择</option>
                                <%
                                    int currentUserType = Format.str2Int(request.getSession().getAttribute("userTypeId"));
                                    if((currentUserType == 2) || (currentUserType == 3)) {
                                        out.print("<option value='4' " + (userTypeId == 4 ? "selected = 'selected'":"") + ">学生</option>");
                                    }else if(currentUserType == 1) {
                                        out.print("<option value='2' " + (userTypeId == 2 ? "selected = 'selected'":"") + ">操作员</option><option value='3' " + (userTypeId == 3 ? "selected = 'selected'":"") + ">老师</option><option value='4' " + (userTypeId == 4 ? "selected = 'selected'":"") + ">学生</option>");
                                    }
                                %>
                            </select>
                        </li>
                        
                        <li class="personnel_top_sel" id="li_user">
                            <label>用户:</label>
                            <select name="_user" class="personnel_width">
                                <option value="0">请选择</option>
                                <%
                                    List<User> userList = null;
                                    StringBuilder sb_userInfo = new StringBuilder();
                                    try{
                                        userList = (List<User>)request.getAttribute("userList");
                                        for(User u: userList) {
                                            sb_userInfo.append("<option value='");
                                            sb_userInfo.append(u.getId());
                                            sb_userInfo.append("' ");
                                            if(userInfoId == u.getId()) {
                                                sb_userInfo.append("selected='selected' />");
                                            }else {
                                                sb_userInfo.append(" />");
                                            }
                                            sb_userInfo.append(u.getName());
                                            sb_userInfo.append("</option>");
                                        }
                                    }catch(Exception e){
                                    }
                                    out.print(sb_userInfo.toString());
                                %>
                            </select>
                        </li>
                        <%
                        int ftypeid = Format.str2Int(request.getAttribute("facilitiesTypeId"));
                        int facilitiesInfoId = Format.str2Int(request.getAttribute("facilitiesInfo"));
                    %>
                    <li class="personnel_top_sel" id="li_facilitiestype">
                             <label>设备类型：</label><select id="select_facilitiesTypeId" name="facilitiesTypeId" class="personnel_width">
                                <option value="0" <%=ftypeid == 0 ? "selected = 'selected'":""%>>请选择</option>
                                <option value="1" <%=ftypeid == 1 ? "selected = 'selected'":""%>>篮球室</option>
                                <option value="2" <%=ftypeid == 2 ? "selected = 'selected'":""%>>羽毛球室</option>
                                <option value="3" <%=ftypeid == 3 ? "selected = 'selected'":""%>>乒乓球室</option>
                            </select>
                        </li>
                        <li class="personnel_top_sel" id="li_facilityinfo">
                             <label>设备：</label><select id="select_facilitiesInfoId"name="facilitiesInfoId" class="personnel_width">
                                <option value="0">请选择</option>
                                <%
                                    List<FacilitiesInfo> list = null;
                                    StringBuilder sb_finfo = new StringBuilder();
                                    try{
                                        list = (List<FacilitiesInfo>)request.getAttribute("facilitiesInfoList");
                                        for(FacilitiesInfo f: list) {
                                            sb_finfo.append("<option value='");
                                            sb_finfo.append(f.getId());
                                            sb_finfo.append("' ");
                                            if(facilitiesInfoId == f.getId()) {
                                                sb_finfo.append("selected='selected' />");
                                            }else {
                                                sb_finfo.append(" />");
                                            }
                                            sb_finfo.append(f.getName());
                                            sb_finfo.append("</option>");
                                        }
                                    }catch(Exception e){
                                    }
                                    out.print(sb_finfo.toString());
                                %>
                            </select>
                        </li>
                        <li class="personnel_top_sel" id="li_startdate">
                            开始日期：<input type="text" id="_startdate" style="width: 80px;" name="startdate" />
                        </li>
                        <li class="personnel_top_sel" id="li_enddate">
                            结束日期：<input type="text" id="_enddate" style="width: 80px;" name="enddate" />
                        </li>
                        <li class="personnel_top_sel">
                            <input type="submit" id="_selectfacilities" value="查询" />
                        </li>
                    </ul>
                    <div class="personnel_list" id="_selectlistdiv">
                            <%
                                String type = Format.null2Blank(request.getAttribute("type"));
                                StringBuilder sb = new StringBuilder();
                                try {
                                    int flag = 1;
                                    List<ReserveRecord> reserveRecords = (List<ReserveRecord>) request.getAttribute("reserverecordlist");
                                    sb.append("<ul>");
                                    sb.append("<li>序号</li>");
                                    sb.append("<li>姓名</li>");
                                    sb.append("<li>设备名称</li>");
                                    sb.append("<li>预约开始时间</li>");
                                    sb.append("<li>预约结束时间</li>");
                                    sb.append("<li class='action'>是否缺席</li>");
                                    sb.append("</ul>");
                                    for (ReserveRecord rr : reserveRecords) {
                                        sb.append("<ul>");
                                        sb.append("<li>");
                                        sb.append(flag);
                                        sb.append("</li>");
                                        sb.append("<li>");
                                        sb.append(rr.getUserName());
                                        sb.append("</li>");
                                        sb.append("<li>");
                                        sb.append(rr.getFacilityName());
                                        sb.append("</li>");
                                        sb.append("<li>");
                                        sb.append(rr.getStartDate());
                                        sb.append("</li>");
                                        sb.append("<li>");
                                        sb.append(rr.getEndDate());
                                        sb.append("</li>");
                                        sb.append("<li class='action'>");
                                        if(rr.getIsAbsence() == 0) {
                                            sb.append("未缺席");
                                        }else {
                                            sb.append("缺席");
                                        }
                                        sb.append("</li>");
                                        sb.append("</ul>");
                                        flag ++;
                                    }
                                } catch (Exception e) {
                                }
                                out.print(sb.toString());
                            %>
                    </div>   
                </form>
            </div> 
        </div>
    </body>
</html>


