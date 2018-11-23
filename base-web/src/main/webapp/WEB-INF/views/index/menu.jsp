<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="level" value="${level + 1}" scope="request" /><!-- 每一次循环，index+1 -->
<c:forEach var="vo" items="${menuList}">
	<c:choose>
		<c:when test="${vo.sfmj == 0}">
			<li class="has-sub" mlevel="${level}">
				<a href="javascript:;">
					<b class="caret pull-right"></b> <c:if test="${not empty vo.tb}"><i class="${vo.tb}"></i></c:if> <span>${vo.cd}</span>
				</a>
				<ul class="sub-menu">
					<c:set var="level" value="${level}" scope="request" /><!-- 循环一次子列表，level+1 -->
				    <c:set var="menuList" value="${vo.children}" scope="request" /><!-- 注意此处，子列表覆盖treeList，在request作用域 -->
				    <c:import url="menu.jsp" /><!-- 这就是递归了 -->
				</ul></li>
		</c:when>
		<c:otherwise>
			<li mlevel="${level}"><a menuId="${vo.id}" href="javascript:;"
						url="<c:url value='${vo.ljdz}'/>" text="${vo.cd}">
				<c:if test="${not empty vo.tb}"><i class="${vo.tb}"></i></c:if> <span>${vo.cd}</span></a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:set var="level" value="${level - 1}" scope="request" /><!-- 退出时，level-1 -->  