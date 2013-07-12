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
                    $("#_type").val("select");//查询按钮点击后类型为select
                    $("#_form1").submit();
                });
                
                $("#_month").datepicker({//添加日期选择功能 
                    changeMonth: true,
//                    changeYear: true,
                    numberOfMonths:1,//显示几个月  
                    showButtonPanel:true,//是否显示按钮面板  
                    dateFormat: 'yy-mm-dd',//日期格式  
                    clearText:"清除",//清除日期的按钮名称  
                    closeText:"关闭",//关闭选择框的按钮名称  
                    yearSuffix: '年', //年的后缀  
                    showMonthAfterYear:true,//是否把月放在年的后面  
                    defaultDate:'2013-06-30',//默认日期  
                    maxDate:'2013-06-30',//最大日期  
                    //monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
                    //dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
                    //dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
                    //dayNamesMin: ['日','一','二','三','四','五','六'],  
                    onClose: function(dateText, inst) {
                        var date = dateText.split("-");
                        $(this).val(date[0] + "-" + date[1]);
                    }
                    
                });  
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
                        <%
                            String msg = Format.null2Blank(request.getAttribute("errorMsg"));
                            if (msg.length() > 0) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("(<span style='color:red'>");
                                sb.append(msg);
                                sb.append("</span>)");
                                out.print(sb.toString());
                            }
                        %>
                    </ul>
                    <div class="personnel_list" id="_selectlistdiv">
                        
                        <input type="hidden" value="" name="_facilitiesinfoid" id="_facilitiesinfoid"/>
                    </div>
                </form>
            </div>
    </body>
</html>


