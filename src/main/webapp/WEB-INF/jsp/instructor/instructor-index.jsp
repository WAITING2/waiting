<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>学位论文提交系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/res/css/wangEditor.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/res/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/res/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/res/js/wangEditor.min.js"></script>
    <style>
        body{
            font-family: "Microsoft Yahei";
        }
    </style>
    <script type="text/javascript">
        $(function(){
            $("#exportExcle").click(function(){
                var url = "${pageContext.request.contextPath}/instructor/exportExcle";
                url += "?studentUsername="+$("input[name=studentUsername]").val();
                url += "&teacherName="+$("input[name=studentUsername]").val();
                url += "&departId="+$("select[name=departId]").val();
                url += "&classId="+$("select[name=classId]").val();
                location.href = url
            })

        })
    </script>
</head>
<body>
<nav class="navbar navbar-inverse" style="margin-bottom:0px;border-radius:0px;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-bar" aria-expanded="false">
                <span class="sr-only">导航按钮</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">学位论文提交系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="nav-bar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">首页</a></li>
                <li><a href="${pageContext.request.contextPath }/instructor/notice">公告</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">您好，${sessionScope.username } <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath }/logout">退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="jumbotron">
    <div class="container">
        <h3>欢迎您，${sessionScope.username } </h3>
        <p>欢迎使用学位论文提交系统</p>
    </div>
</div>
<div class="container">
    <section>
        <h4>论文情况跟踪：</h4>
        <form action="${pageContext.request.contextPath }/instructor/index" method="POST">
            学生姓名：<input type="text" name="studentUsername" value="${studentUsername }" placeholder="请输入学生姓名">
            老师姓名：<input type="text" name="teacherName" value="${teacherName }" placeholder="请输入老师姓名">
            专业：
            <select name="departId">
                <option value="">--请选择--</option>
                <c:forEach var="depart" items="${departs}">
                    <option  value="${depart.departmentId }" ${ departId ==  depart.departmentId ? 'selected':''}>${depart.departmentName}</option>
                </c:forEach>
            </select>
            班级：
            <select name="classId">
                <option value="">--请选择--</option>
                <c:forEach var="cla" items="${classes}">
                    <option  value="${cla.id }" ${ classId ==  cla.id ? 'selected':''}>${cla.className}</option>
                </c:forEach>
            </select>
            <input type="submit" value="查询"> <input type="button" id="exportExcle" value="导出Excel">
        </form>
        <table class="table">
            <tr>
                <th>题目</th>
                <th>学生姓名</th>
                <th>专业名称</th>
                <th>班级</th>
                <th>老师姓名</th>
                <th>论文提交时间</th>
                <th>论文状态</th>
            </tr>
            <c:forEach items="${papers}" var="item">
                <tr>
                    <td>${item.topic}</td>
                    <td>${item.studentName}</td>
                    <td>${item.department}</td>
                    <td>${item.className}</td>
                    <td>${item.teacherName}</td>
                    <td>${item.posted_time}</td>
                    <td>
                        <c:if test="${item.paperStatus==0}"> 通过 </c:if>
                        <c:if test="${item.paperStatus==1}"> 未通过 </c:if>
                        <c:if test="${item.paperStatus==2}"> 待审核 </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </section>
</div>
</body>
</html>
