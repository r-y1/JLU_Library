<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">JLU图书系统后台</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li ><a href="<%=request.getContextPath()%>/admin/order_list">借阅管理</a></li>
                <li ><a href="<%=request.getContextPath()%>/admin/user_list">客户管理</a></li>
                <li ><a href="<%=request.getContextPath()%>/admin/goods_list">图书管理</a></li>
                <li ><a href="<%=request.getContextPath()%>/admin/type_list">类目管理</a></li>
                <%--新添加的一项，数据分析--%>
                <li><a href="<%=request.getContextPath()%>/charts" >数据分析</a></li>

                <li><a href="<%=request.getContextPath()%>/user_logout">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
