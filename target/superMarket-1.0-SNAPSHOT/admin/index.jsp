<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>
<%--改--%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>后台管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css"/> 
</head>
<body>
<div class="container-fluid">

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="/background.jsp"></jsp:include>

	<br><br>
	<div class="alert alert-success" role="alert">JLUer！你已进入管理员页面</div>
	
</div>	
</body>
</html>