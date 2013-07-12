<%@page import="java.util.Date"%>
<%@page import="edu.facilities.model.ReserveRecord"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.FacilitiesInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统-预约管理</title>
        
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
                $("#_addreservediv").hide();
                $("#_selectlistdiv").show();
                $("#_selectreserve").click(function() {
                    $("#_type").val("select");
                    $("#_form1").submit();
                });
                $("#select_facilitiesTypeId").change(function() {
                    $("#_type").val("select");
                    $("#_form1").submit();
                });
                
                $("#_addreserve").click(function() {
                    $("#_type").val("add");
                    $("#_selectlistdiv").hide();
                    $("#_addreservediv").show();
                });
            });
            function cancle(rid) {
                if(confirm("确定取消预约吗？")) {
                    $("#_reserverecordid").val(rid);
                    $("#_type").val("cancle");
                    $("#_form1").submit();
                }
            }
            function absence(rid) {
                if(confirm("确定要设置缺席吗？")) {
                    $("#_reserverecordid").val(rid);
                    $("#_type").val("absence");
                    $("#_form1").submit();
                }
            }
            function noabsence(rid) {
                if(confirm("确定要更改为未缺席吗？")) {
                    $("#_reserverecordid").val(rid);
                    $("#_type").val("noabsence");
                    $("#_form1").submit();
                }
            }
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
            <form id="_form1" action="<%=request.getContextPath()%>/reserveManager.do" method="post">
                <input type="hidden" id="_type" name="_type" value="<%=request.getAttribute("type")%>" />
                <ul class="personnel_top">
                    <%
                        int ftypeid = Format.str2Int(request.getAttribute("facilitiesTypeId"));
                        FacilitiesInfo finfo = null;
                        try{
                            finfo = (FacilitiesInfo)request.getAttribute("facilitiesInfo");
                        }catch(Exception e){
                        }
                    %>
                        <li class="personnel_top_sel">
                             <label>设备类型：</label><select id="select_facilitiesTypeId" name="facilitiesTypeId" class="personnel_width">
                                <option value="0" <%=ftypeid == 0 ? "selected = 'selected'":""%>>请选择</option>
                                <option value="1" <%=ftypeid == 1 ? "selected = 'selected'":""%>>篮球室</option>
                                <option value="2" <%=ftypeid == 2 ? "selected = 'selected'":""%>>羽毛球室</option>
                                <option value="3" <%=ftypeid == 3 ? "selected = 'selected'":""%>>乒乓球室</option>
                            </select>
                        </li>
                        <li class="personnel_top_sel">
                             <label>设备：</label><select name="facilitiesInfoId" class="personnel_width">
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
                                            if(null != finfo && (finfo.getId() == f.getId())) {
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
                        <li class="personnel_top_sel">
                            <input type="button" id="_selectreserve" value="查询" />
                            <%
                                if(null != finfo && finfo.getId() > 0) {
                                    out.print("<input type='button' id='_addreserve' value='预约此设备' />");
                                }
                            %>
                        </li>
                </ul>
                <ul class="personnel_top">
                    <li>
                        <%
                            String msg = Format.null2Blank(request.getAttribute("errorMsg"));
                            if (msg.length() > 0) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("(<span id='_errormsg' style='color:red'>");
                                sb.append(msg);
                                sb.append("</span>)");
                                out.print(sb.toString());
                            }
                        %>
                    </li>
                </ul>
                <div class="personnel_list" id="_selectlistdiv">
                    <%
                        StringBuilder sb = new StringBuilder();
                        try {
                            List<ReserveRecord> reserveRecords = (List<ReserveRecord>) request.getAttribute("reserveRecordList");
                            sb.append("<ul>");
                            sb.append("<li>设备名称</li>");
                            sb.append("<li>预留开始时间</li>");
                            sb.append("<li>预留结束时间</li>");
                            sb.append("<li>预约用户</li>");
                            sb.append("<li>是否缺席</li>");
                            sb.append("<li class='action'>操作</li>");
                            sb.append("</ul>");
                            int flag = 1;
                            for (ReserveRecord reserveRecord : reserveRecords) {
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append(finfo.getName());
                                sb.append("</li>");
                                sb.append("<li>");
                                sb.append(reserveRecord.getStartDate());
                                sb.append("</li>");
                                sb.append("<li>");
                                sb.append(reserveRecord.getEndDate());
                                sb.append("</li>");
                                sb.append("<li>");
                                sb.append(reserveRecord.getUserName());
                                sb.append("</li>");
                                sb.append("<li>");
                                if(reserveRecord.getIsAbsence() == 0) {
                                    sb.append("未缺席");
                                }else {
                                    sb.append("缺席");
                                }
                                sb.append("</li>");
                                sb.append("<li class='action'>");
                                if(Format.compareDateWithDate(Format.formatStringWithHour(reserveRecord.getStartDate()), new Date()) == 1) {
                                    sb.append("<a href='javascript:cancle(");
                                    sb.append(reserveRecord.getId());
                                    sb.append(");'>取消预约</a>");
                                }
                                sb.append("&nbsp;&nbsp;<a href='javascript:absence(");
                                sb.append(reserveRecord.getId());
                                sb.append(");'>缺席</a>&nbsp;&nbsp;<a href='javascript:noabsence(");
                                sb.append(reserveRecord.getId());
                                sb.append(");'>未缺席</a></li>");
                                sb.append("</ul>");
                            }
                        } catch (Exception e) {
                        }
                        out.print(sb.toString());
                    %>
                    <input type="hidden" value="" name="_facilitiesinfoid" id="_facilitiesinfoid"/>
                </div>
                    
                <div class="personnel" id='_addreservediv'>
                    <input type="hidden" name="_reserverecordid" id="_reserverecordid" value=""/>
                    <div class="login_form_row">
                        <label class="login_label">开始日期:</label><input type="text" id="_startdate" name="startdate" class="login_input" value="" />
                        <jsp:include page="/WEB-INF/jsp/hour.jsp" ></jsp:include>
                    </div>
                    <div class="login_form_row">
                        <label class="login_label">结束日期:</label><input type="text" id="_enddate" name="enddate" class="login_input" value="" />
                        <jsp:include page="/WEB-INF/jsp/hour.jsp" ></jsp:include>
                    </div>
                    <div class="submit">
                        <input type="image" src="css/images/submit.png"  id="_submit" class="submit_btn" />
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>


