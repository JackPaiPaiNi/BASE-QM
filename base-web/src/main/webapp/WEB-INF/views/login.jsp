<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>财务报表共享平台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link href="${pageContext.request.contextPath}/css/images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${pageContext.request.contextPath}/css/images/favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${pageContext.request.contextPath}/css/images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/theme/default.css" rel="stylesheet" id="theme" />
<link href="${pageContext.request.contextPath}/css/style.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/login.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js?v=1"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-validation/messages_zh.js"></script>
</head>
<body>
	<div class="login-cover">
		<div class="login-cover-bg"></div>
	</div>
    <div class="login-v2-panel">
        <div class="row">
            <div class="col-sm-7">
                <div class="login-v2-info">
                    <div class="m-b"></div>
                    <h2>欢迎使用 <strong>财务报表共享平台</strong></h2>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>xxxxxx</li>
                    </ul>
                    <div align="center">
                    	
                    </div>
                </div>
            </div>
            <div class="col-sm-5">
				<form id="login_form" method="post">
					<h4 class="no-margins">登录：</h4>
					<p class="m-t-md">请输入您在创维的账号和密码</p>
					<input type="text" class="form-control uname" id="gh" name="gh"
						placeholder="用户名" /> <input type="password"
						class="form-control pword m-b" id="mm" name="mm" placeholder="密码" /> 
					<input type="hidden" name="loginType" value="0" />
					<div class="err text-warning"></div>
					<div class="err text-warning">${error}</div>
					<!-- <a href="javascript:;" onclick="findPw();">找回密码</a> --> <a
						class="btn btn-primary btn-block m-t-md" href="javascript:void(0);"
						id="btn_login" onclick="check();">登录</a>
				</form>
			</div>
        </div>
        <div class="row">
            <div class="col-sm-12">
		        <div class="login-v2-footer">
		        	&copy; 2018 深圳创维-RGB电子有限公司 信息管理部
		        </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
$(function() {
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if(self.frameElement && self.frameElement.tagName == "IFRAME"){
		//alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = location;
	}
	
	$("#login_form").validate({
		errorElement: "span",
		rules : {
			gh: "required",
			mm: "required"
		},
		messages : {
			gh: "工号不能为空！",
			mm: "密码不能为空！"
		},
		errorPlacement: function(error, element) {  
		    error.appendTo(element.next(".err"));  
		}
	});
	
	$("#gh").focus();
	
	$("#gh").bind("keypress", function(e) {
		var key = window.event ? e.keyCode : e.which;
		if (key.toString() == "13") {// 回车事件
			$("#mm").focus();
		}
	});
	
	$("#mm").bind("keypress", function(e) {
		var key = window.event ? e.keyCode : e.which;
		if (key.toString() == "13") {// 回车事件
			check();
		}
	});
});

function check() {
	$("#login_form").submit();
}

function findPw() {
	var sysurl = 'https://jloa.skyallhere.com/oa/cgi-bin/ForgotPassword.jsp?uname='+$("#username").val();
	window.open(sysurl);
}
</script>
</html>