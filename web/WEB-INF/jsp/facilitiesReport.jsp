<%@page import="edu.facilities.model.ReserveRecord"%>
<%@page import="edu.facilities.utils.Format"%>
<%@page import="edu.facilities.model.FacilitiesInfo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统-设备使用报告</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="all" />
        <script src="js/jquery.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>   
        <script src="js/jquery-ui-i18n.min.js" type="text/javascript"></script>
        
        <script type="text/javascript">
            $(document).ready(function () {
                $("#_selectfacilities").click(function () {
                    $("#_type").val("select");//查询按钮点击后更改请求类型为select
                    $("#_form1").submit();
                });
                $("#_month").datepicker({//添加日期选择功能 
                    changeMonth: true,//允许以下拉列表方式更改月份
//                    changeYear: true,
                    numberOfMonths:1,//显示几个月  
                    showButtonPanel:true,//是否显示按钮面板  
                    dateFormat: 'yy-mm-dd',//日期格式  
                    clearText:"清除",//清除日期的按钮名称  
                    closeText:"关闭",//关闭选择框的按钮名称  
                    yearSuffix: '年', //年的后缀  
                    showMonthAfterYear:true,//是否把月放在年的后面  
                    defaultDate:'<%=Format.null2Blank(request.getAttribute("maxdate"))%>',//默认日期  
                    maxDate:'<%=Format.null2Blank(request.getAttribute("maxdate"))%>',//最大日期  
                    //monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
                    //dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
                    //dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
                    //dayNamesMin: ['日','一','二','三','四','五','六'],  
                    onClose: function(dateText, inst) {
                        var date = dateText.split("-");//去掉日只留年月 例如2013-07-13 更改为2013-07
                        $(this).val(date[0] + "-" + date[1]);
                    }
                });
                var monthDate = '<%=Format.null2Blank(request.getAttribute("monthDate"))%>';
                $("#_month").val(monthDate);//记录用户的日期选择
            });
        </script>
    </head>

    <body>
        <div id="main_container">
            <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
            <div class="center_content">
                <form id="_form1" action="<%=request.getContextPath()%>/facilitiesReport.do" method="post">
                    <ul class="personnel_top">
                        <li class="personnel_top_sel">
                            <label>月份：</label>
                            <input type="text" name="_month" id="_month" readonly="readonly" />
                        </li>
                        <li class="personnel_top_sel">
                            <input type="button" id="_selectfacilities" value="查询" />
                        </li>
                    </ul>
                    <div class="personnel_list" id="_selectlistdiv">
                            <%
                                StringBuilder sb = new StringBuilder();
                                try {
                                    int flag = 1;
                                    List<ReserveRecord> reserveRecords = (List<ReserveRecord>) request.getAttribute("reserverecordList");
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
    </body>
</html>


