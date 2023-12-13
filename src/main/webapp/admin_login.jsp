<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/simpleCart.min.js"></script>
</head>
<body>
<!--header-->
<jsp:include page="header.jsp">
	<jsp:param name="flag" value="11"></jsp:param>
</jsp:include>
<jsp:include page="background.jsp"></jsp:include>
<!--//header-->


<!--account-->
<div class="account">
	<div class="container">
		<div class="register">
			<c:if test="${!empty msg }">
				<div class="alert alert-success">${msg }</div>
			</c:if>
			<c:if test="${!empty failMsg }">
				<div class="alert alert-danger">${failMsg }</div>
			</c:if>

			<form action="<%=request.getContextPath()%>/admin_login" method="post">
				<div class="register-top-grid">
					<h3>管理员登录</h3>
					<div class="input">
						<span>用户名/邮箱 <label style="color:red;">*</label></span>
						<input type="text" name="ue" placeholder="请输入用户名" required="required">
					</div>
					<div class="input">
						<span>密码 <label style="color:red;">*</label></span>
						<input type="password" name="password" placeholder="请输入密码" required="required">
					</div>

					<div class="clearfix"> </div>
				</div>
				<div class="register-but text-center">
					<input type="submit" value="提交">
					<div class="clearfix"> </div>
				</div>
			</form>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>
<!--//account-->

<!--footer-->
<jsp:include page="footer.jsp"></jsp:include>
<!--//footer-->

</body>
</html>