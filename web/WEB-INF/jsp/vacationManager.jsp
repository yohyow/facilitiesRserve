<%@page import="edu.facilities.model.User"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.Vacation"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统-假期管理</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="all" />
        <script src="js/jquery.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui-i18n.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#_startdate").datepicker();
                $("#_startdate").datepicker( "option", "dateFormat", "yy-mm-dd");
                $("#_enddate").datepicker();
                $("#_enddate").datepicker( "option", "dateFormat", "yy-mm-dd");
                $("#_adddiv").hide();
                $("#_addvacation").click(function () {
                    $("#_adddiv").show();
                    $("#_type").val("add");
                });
                $("._delvacation").each(function() {
                    $(this).click(function() {
                        if(confirm("确定删除吗？")) {
                            $("#_type").val("del");
                            $("#_vacationid").val($(this).attr("val"));
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
                <form id="_form1" action="<%=request.getContextPath()%>/vacationManager.do" method="post">
                    <ul class="personnel_top" >
                        <li class="personnel_top_sel">
                            <input type="button" id="_addvacation" value="添加假期" /><%
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

                    <div class="personnel" id="_adddiv">
                        <input type="hidden" name="_type" id="_type" value="" />
                        <div class="login_form_row"><label class="login_label">假期名：</label><input type="text" name="_name" maxlength="50"/></div>
                        <div class="login_form_row"><label class="login_label">开始日期：</label><input type="text" id="_startdate" name="startdate" readonly="readonly"/></div>
                        <div class="login_form_row"><label class="login_label">结束日期：</label><input type="text" id="_enddate" name="enddate" maxlength="50" readonly="readonly" value="" /></div>
                        <div class="login_form_row"><input type="submit" style="margin-left: 15px;" value="添加"/></div>
                    </div>
                    <div class="personnel_list">

                        <%
                            String type = Format.null2Blank(request.getAttribute("type"));
                            StringBuilder sb = new StringBuilder();
                            try {
                                List<Vacation> list = (List<Vacation>) request.getAttribute("vacationlist");
                                sb.append("<ul>");
                                sb.append("<li>序号</li>");
                                sb.append("<li>假期名</li>");
                                sb.append("<li>开始时间</li>");
                                sb.append("<li>结束时间</li>");
                                sb.append("<li class='action'>操作</li>");
                                sb.append("</ul>");
                                int flag = 1;
                                for (Vacation vacation : list) {
                                    sb.append("<ul>");
                                    sb.append("<li>");
                                    sb.append(flag++);
                                    sb.append("</li>");
                                    sb.append("<li>");
                                    sb.append(vacation.getName());
                                    sb.append("</li>");
                                    sb.append("<li>");
                                    sb.append(vacation.getStartDate());
                                    sb.append("</li>");
                                    sb.append("<li>");
                                    sb.append(vacation.getEndDate());
                                    sb.append("</li>");
                                    sb.append("<li class='action'><a href='javascript:;' class='_delvacation' val='");
                                    sb.append(vacation.getId());
                                    sb.append("'>删除</a></li>");
                                    sb.append("</ul>");
                                }
                            } catch (Exception e) {
                            }
                            out.print(sb.toString());
                        %>
                        <input type="hidden" value="" name="_vacationid" id="_vacationid"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>


