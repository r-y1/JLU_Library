<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>数据分析</title>
    <meta charset="utf-8">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" />

    <!-- 引入 ECharts 文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
</head>
<div class="container-fluid">
<body>
<jsp:include page="/admin/header.jsp"></jsp:include>
<jsp:include page="/background.jsp"></jsp:include>

<br><br>
<br><br>
<br><br>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 800px;height:600px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    option = {
        title : {
            text: '图书借阅统计',
            subtext: '源自表GOODS和ORDERITEM',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: [
                <c:forEach items="${goodsList}" var="g">
                ["${g.name}"],
                </c:forEach>
            ]
        },
        series : [
            {
                name: '图书借阅量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    <c:forEach items="${goodsList}" var="goods">
                    {value:"${goods.stock}", name:"${goods.name}"},
                    </c:forEach>
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</div>
</body>
</html>