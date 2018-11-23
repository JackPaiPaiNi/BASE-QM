<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>500</title>
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
				500 <i class="fa fa-warning"></i>
			</div>
			<div class="error-content">
				<div class="error-message">应用程序内部错误...</div>
				<div class="error-desc">
					应用程序内部错误,请重新登录。<br>如果重新登录后还有问题请<a
						href="mailto:xxzx@skyworth.com">联系我们</a>
				</div>
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