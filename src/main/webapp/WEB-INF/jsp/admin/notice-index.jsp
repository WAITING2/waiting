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
</head>
<body>
  <nav class="navbar navbar-default">
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
        <li ><a href="/">管理学生用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/teacher">管理指导老师用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/administrator">管理系统管理员账户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/selection">查看选题情况</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/grade">查看已完成学生成绩</a></li>
        <li class="active"><a href="${pageContext.request.contextPath }/admin/notice">公告管理</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/getStudentPaperInfo">查询导出</a></li>
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
<div class="container">
  <div class="row">
      <div class="col-md-6 col-xs-6">
        <h4 style="padding-left:8px;">公告信息</h4>
      </div>
      <c:if test="${admin}">
      <div class="col-md-6 col-xs-6">
        <a href="${pageContext.request.contextPath }/admin/notice/add.html" class="btn btn-primary btn" style="float:right;">添加新公告</a>
      </div>
      </c:if>
    </div>
    <c:forEach var="notice" items="${notices}">
	    
	     <table border="1" cellspacing="0" cellpadding="0"width="100%" height="100%">
	    
	    
	    
	    	<tr align="center"><td width="10%">主题</td><td colspan="5">${notice.title}</td></tr>
	    	
	    	<tr align="center"><td width="10%">内容</td><td td colspan="5">${notice.content}</td></tr>
	    	
	    	<tr style="colspan" align="center">
	    		<td width="10%">发布日期</td>
	    		<td width="30%">
	    			${notice.createdate}
	    		</td>
	    		<td width="10%">发布人</td>
	    		<td width="20%">
	    			${notice.admin.userName}
	    		</td>
	    		<td width="10%">操作</td>
	    		<td width="20%">
	    			<c:if test="${admin}">
	    				<a  class="small blue button" href="${pageContext.request.contextPath }/admin/editNotice?id=${notice.id}" >编辑</a>
		    			<a  class="small blue button" href="${pageContext.request.contextPath }/admin/delNotice?id=${notice.id}" >删除</a>
	    			</c:if> 
    				
	    		</td>
	    	</tr>
	    </table>
	     <HR style="border:3 double #987cb9" width="80%" color=#987cb9 SIZE=3>
    </c:forEach>
    </div>
</body>
</html>
