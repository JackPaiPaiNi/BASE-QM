<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
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
<link href="${pageContext.request.contextPath}/css/style.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/sky.main.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/theme/default.css" rel="stylesheet" id="theme" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js?v=1"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sky/jquery.sky.main.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sky/jquery.sky.form.min.js"></script>

</head>
<body>
	<!-- begin page-container -->
	<div class="fade in page-container page-sidebar-fixed page-header-fixed page-with-iframe"><!--  page-sidebar-minified -->
		<!-- begin #header -->
		<div id="header" class="header navbar navbar-default navbar-fixed-top">
			<!-- begin container-fluid -->
			<div class="container-fluid">
				<!-- begin mobile sidebar expand / collapse button -->
				<div class="navbar-header">
					<a href="javascript:;" class="navbar-brand"><span
						class="navbar-logo"></span><!-- <span
						class="navbar-logo-sub"></span> --></a>
				</div>
				<!-- end mobile sidebar expand / collapse button -->
				<!-- end navbar-collapse -->
				<!-- begin header navigation right -->
				<ul class="nav navbar-nav navbar-right">
					<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><span class="text-info"> <i
								class="fa fa-bell-o"></i> 取数截止：
						</span><span class="text-danger" id="refresh-all"></span> <b
							class="caret"></b> </a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">&nbsp;&nbsp;AH取数截止：<span
									class="text-danger">xxxxxx</span></a></li>
							<li><a href="#">&nbsp;&nbsp;NC取数截止：<span
									class="text-danger">xxxxx</span></a></li>
							<li><a href="#"><span class="text-info">报表汇总截止：</span><span
									class="text-danger">xxxxx</span></a></li>
							<li class="divider"></li>
							<li><a href="#">月末两天、月初三天每2小时刷新一次，其<br>他时段每天刷新一次。
							</a></li>
							<li>
						</ul></li> -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span><i class="fa fa-user fa-fw"></i> [${user.gh}]${user.xm}
							<b class="caret"></b></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">部门：${user.bm}</a></li>
							<li><a href="#">职务：${user.hrzw}</a></li>
							<li class="divider"></li>
							<li><a href="javascript:;" title="清除缓存"
								onclick="javascript:freshCacheData();"><i
									class="fa fa-refresh text-success"></i> 刷新缓存</a></li>
						</ul></li>
					<!-- <li class="navbar-user"></li> -->
					<li class="button"><a href="javascript:;" title="注销"
						onclick="javascript:window.location.href='${pageContext.request.contextPath}/logout';"><i
							class="fa fa-power-off text-danger"></i></a></li>
				</ul>
				<!-- end header navigation right -->
			</div>
			<!-- end container-fluid -->
		</div>
		<!-- end #header -->

		<!-- begin sidebar -->
		<div class="sidebar">
			<!-- begin sidebar scrollbar -->
			<div data-scrollbar="true" data-height="100%">
				<!-- begin sidebar nav -->
				<ul class="nav menu_sys">
					<!-- <li class="nav-profile">
						<div class="info">
							<input type="text" class="form-control input-sm input-white"
								id="searchid" placeholder="搜索菜单" />
						</div>
					</li> -->
					
					<c:set var="level" value="0" scope="request" /><!-- 记录树的层次，注意scope-->
					<c:import url="menu.jsp" />
					
					<!-- begin sidebar minify button -->
					<li class="sidebar-minify-li"><a href="javascript:;"
						class="sidebar-minify-btn"><i class="fa fa-angle-double-left"></i></a></li>
					<!-- end sidebar minify button -->
				</ul>
				<!-- end sidebar nav-->
			</div>
			<!-- end sidebar scrollbar -->
		</div>
		<div class="sidebar-bg"></div>
		<!-- end sidebar -->
	
		<!-- begin #content -->
		<div class="content" style="overflow: hidden;">
			<div class="row content-tabs">
				<button class="roll-nav roll-left mtabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs mtabs">
					<div class="page-tabs-content">
						 <a menuId="-1" href="javascript:;" class="active mtab">首页</a> 
					</div>
				</nav>
				<button class="roll-nav roll-right mtabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown mtabClose" data-toggle="dropdown">
						关闭<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="mtabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="mtabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
			</div>
			 <div class="row mtabContent">
				<iframe class="mtabIframe" width="100%" height="100%" 
					src="<%=request.getContextPath()%>/demo/echarts" frameborder="0"
					menuId="-1"></iframe>
			</div>
		</div>
		
		<!-- end #content -->
		
		
	</div>
	<!-- end page container -->
</body>
<script type="text/javascript">
// 弹出框全屏使用句柄（勿删除）
var layerFullIndex;
$(function() {
	getIframeHeight();
	
	$(window).resize(function(){
		getIframeHeight();
	});
	

});

function getIframeHeight(){
	var iframeHeight = $(".sidebar").height() - 40;
	$(".mtabIframe").height(iframeHeight);
	return iframeHeight;
}

function freshCacheData() {
	// 刷新五大类缓存数据
	$.clearAllCache();
	layer.alert("缓存清除成功!", {
		shadeClose: true,
		title: "提示",
		icon: 1
	});
}
</script>
</html>
