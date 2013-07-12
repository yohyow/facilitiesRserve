<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.FacilitiesInfo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统-设备管理</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#_selectfacilities").click(function () {
                    $("#_type").val("select");//查询按钮点击后类型为select
                    $("#_form1").submit();
                });
                $("#_adddiv").hide();
                $("#_addfacilitiesinfo").click(function () {
                    $("#_adddiv").show();
                    $("#_type").val("add");
                });
                $("._delfacilitiesinfo").each(function() {
                   $(this).click(function() {
                       if(confirm("确定删除吗？")) {
                            $("#_type").val("del");
                            $("#_facilitiesinfoid").val($(this).attr("val"));
                            $("#_form1").submit();
                       }
                   });
                });
                
                var usertype = "<%=Format.null2Blank(request.getAttribute("facilitiestype"))%>";
                $("select[@name='facilitiesTypeId'] option").each(function() {
                   if($(this).attr("value") == usertype) {
                       $(this).attr("selected", "selected");
                   } 
                });
            });
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
            <form id="_form1" action="<%=request.getContextPath()%>/facilitiesManager.do" method="post">
                <ul class="personnel_top">
                        <li class="personnel_top_sel">
                             <label>设备类型：</label><select name="facilitiesTypeId" class="personnel_width">
                                <option value="1">篮球室</option>
                                <option value="2">羽毛球室</option>
                                <option value="3">乒乓球室</option>
                            </select>
                        </li>
                        <li class="personnel_top_sel">
                            <input type="button" id="_selectfacilities" value="查询" />
                        </li>
                        <li class="personnel_top_sel">
                            <input type="button" id="_addfacilitiesinfo" value="添加设备" />
                        </li>
                    <%
                               String msg = Format.null2Blank(request.getAttribute("errorMsg"));
                               if(msg.length() > 0) {
                                   StringBuilder sb = new StringBuilder();
                                   sb.append("(<span style='color:red'>");
                                   sb.append(msg);
                                   sb.append("</span>)");
                                   out.print(sb.toString());
                               }        
                            %>
                </ul>
                    <ul class="personnel_top" id="_adddiv">
                        <input type="hidden" name="_type" id="_type" value="" />
                        <li class="personnel_top_sel">设备名称：<input type="text" name="_name" maxlength="50"/><select name="_facilitiestype">
                                <option value="1">篮球室</option>
                                <option value="2">羽毛球室</option>
                                <option value="3">乒乓球室</option>
                            </select><input type="submit" style="margin-left: 15px;" value="添加"/></li> 
                    </ul>
                <div class="personnel_list" id="_selectlistdiv">
                    <%
                        String type = Format.null2Blank(request.getAttribute("type"));
                        StringBuilder sb = new StringBuilder();
                        try {
                            List<FacilitiesInfo> list = (List<FacilitiesInfo>) request.getAttribute("facilitiesList");
                            sb.append("<ul>");
                            sb.append("<li>序号</li>");
                            sb.append("<li>设备名称</li>");
                            sb.append("<li class='action'>操作</li>");
                            sb.append("</ul>");
                            int flag = 1;
                            for (FacilitiesInfo facilitiesinfo : list) {
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append(flag ++);
                                sb.append("</li>");
                                sb.append("<li>");
                                sb.append(facilitiesinfo.getName());
                                sb.append("</li>");
                                sb.append("<li class='action'><a href='javascript:;' class='_delfacilitiesinfo' val='");
                                sb.append(facilitiesinfo.getId());
                                sb.append("'>删除</a></li>");
                                sb.append("</ul>");
                            }
                        } catch (Exception e) {
                        }
                        out.print(sb.toString());
                    %>
                    <input type="hidden" value="" name="_facilitiesinfoid" id="_facilitiesinfoid"/>
                </div>
            </form>
        </div>
    </body>
</html>


