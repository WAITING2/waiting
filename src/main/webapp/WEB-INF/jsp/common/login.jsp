<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!DOCTYPE html>
<html>
<head>
<title>登录学位论文提交系统</title>
<link rel="stylesheet" type="text/css" href="res/css/bootstrap.min.css">
<style>
  body{
      background: url(image/back.jpg);
    font-family: "Microsoft Yahei";
  }
</style>
</head>
<body>

<br><br>
<h3 align="center">欢迎登陆学位论文提交系统</h3>
<br><br><br>
<%--<div class="jumbotron">--%>
  <%--<div class="container">--%>
    <%--<h3>学位论文提交系统</h3>--%>
    <%--<p>系统登录</p>--%>
  <%--</div>--%>
<%--</div>--%>
<div class="container">
	<div class="col-md-4"></div>
	<div class="col-md-4">
		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath }/login">
			${requestScope.hints }		
			<div class="form-group">
				<label for="username">用户</label>
				<div>
					<input id="username" name="username" type="text" class="form-control">
				</div>
			</div>
      <div class="form-group">
				<label for="password">密码</label>
				<div>
					<input id="password" name="password" type="password" class="form-control">
				</div>
			</div>
			<div class="form-group">
        <label>用户类型</label>
        <div>
          <span class="radio-inline">
            <input type="radio" name="account" id="account1" value="student" checked="true"> 毕业生
          </span>
          <span class="radio-inline">
            <input type="radio" name="account" id="account2" value="teacher"> 指导老师
          </span>
          <span class="radio-inline">
           <input type="radio" name="account" id="account4" value="instructor"> 辅导员
         </span>
          <span class="radio-inline">
            <input type="radio" name="account" id="account3" value="admin"> 系统管理员
          </span>
        </div>
			</div>
                <br>
      <%--<button class="btn btn-success btn-block" type="submit">登录</button>--%>
                <button class="btn btn-success btn-block" type="submit" style='background-color:#ff577e'>登录</button>
		</form>
	</div>
	<div class="col-md-4"></div>
</div>
</body>
</html>
 