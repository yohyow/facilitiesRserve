<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.facilities.utils.Format"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>体育设施预约系统登录</title>
        <link href="css/facility_style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="main_container">
            <div id="header">
                <div class="logo">体育设施预约系统</div>
            </div>
            <div class="center_content">

                <div class="text_box">
                    <form action="<%=request.getContextPath()%>/login.do" method="post">
                        <%String msg = Format.null2Blank(request.getAttribute("errorMsg"));
                            if (msg.length() > 0) {
                                out.print("<strong style='color:red;'>提示：" + msg + "</strong>");
                            }
                        %>
                        <div class="title">用户登录</div>
                        <div class="login_form_row">
                            <label class="login_label">用户名：</label><input type="text" name="account" class="login_input" />
                        </div>

                        <div class="login_form_row">
                            <label class="login_label">密码：</label><input type="password" name="password" class="login_input" />
                        </div>                                     
                        <input type="image" src="css/images/login.png" class="login" />                              
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
