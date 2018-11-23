<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>403</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/sky.main.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/theme/default.css" rel="stylesheet" id="theme" />
</head>
<body>
	<!-- begin #page-container -->
	<div class="fade in">
		<!-- begin error -->
		<div class="error">
			<div class="error-code">
				403 <i class="fa fa-warning"></i>
			</div>
			<div class="error-content">
				<div class="error-message">您没有权限访问该页面...</div>
				<div class="error-desc">请联系系统管理员进行授权.</div>
				<%-- <div>
					<a href="<%=request.getContextPath()%>/index.action"
						class="btn btn-success">返回首页</a>
				</div> --%>
			</div>
		</div>
		<!-- end error -->
	</div>
</body>
</html>