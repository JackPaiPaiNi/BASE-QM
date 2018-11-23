<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>404</title>
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
				404 <i class="fa fa-warning"></i>
			</div>
			<div class="error-content">
				<div class="error-message">您所访问的页面不存在...</div>
				<div class="error-desc">您所访问的页面不存在或尚未开放.</div>
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