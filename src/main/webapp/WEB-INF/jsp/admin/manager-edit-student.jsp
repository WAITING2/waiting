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

<style>
  body{
    font-family: "Microsoft Yahei";
  }
</style>
    <script>

        $(function(){

            var content_url =  "${requestScope.formAction }"

            $("#operatForm").attr("action",content_url);
        })





    </script>
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
      <a class="navbar-brand" href="${pageContext.request.contextPath }">学位论文提交系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="nav-bar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/">管理学生用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/teacher">管理指导老师用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/administrator">管理系统管理员账户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/selection">查看选题情况</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/grade">查看已完成学生成绩</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/notice">公告管理</a></li>
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
  <form action="" id="operatForm" method="POST" onsubmit="return  register_123()">
      <input type="hidden" id="num_id" value="${student.studentNumber}" >
  <div class="form-group">
    <label for="id">学生学号</label>
    <c:choose>
    	<c:when test="${requestScope.formAction.contains(\"modify\")}">
    		<input type="text" maxlength="11" class="form-control"id="studentNumber" name="studentNumber" placeholder="请输入学号" value="${requestScope.student.studentNumber }"  disabled="true">
    		<input type="text" maxlength="11" class="form-control"id="studentNumber" name="studentNumber" placeholder="请输入学号" value="${requestScope.student.studentNumber }"  style="display:none;">
    	</c:when>
    	<c:otherwise>
			<input type="text"  onfocus="warnNumber()" onblur="return checkNumber()" maxlength="11" class="form-control"id="studentNumber" name="studentNumber" placeholder="请输入学号" value="${requestScope.student.studentNumber }" >
   		 </c:otherwise>
    </c:choose>
      <div id="numberts"
           style="height:20px;line-height:20px;text-align: left;font-size: 12px;"></div>
  </div>
  <div class="form-group">
    <label for="name">学生姓名</label>
    <input type="text"  id="uName"  maxlength="8" onfocus="warnName()" onblur="return checkname()" class="form-control" id="name"  name="name" placeholder="请输入姓名"  value="${requestScope.student.studentName }">


  </div>
      <div id="namets"
             style="height:20px;line-height:20px;text-align: left;font-size: 12px;"></div>
  <div class="form-group">
    <label for="class">联系电话</label>
    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入联系电话"  value="${requestScope.student.phone }">
  </div>
      <div class="form-group">
          <label for="class">联系邮箱</label>
          <input type="text" class="form-control" id="email" name="email" placeholder="请输入联系邮箱"  value="${requestScope.student.email }">
      </div>
  <div class="form-group">
    <label for="gender">学生性别</label>
    <c:choose>
    	<c:when test="${requestScope.student.gender == \"男\" }">
    		<select class="form-control" id="gender" name="gender"><option value="男" selected="true">男生</option><option value="女">女生</option></select>
    	</c:when>
    	<c:when test="${requestScope.student.gender == \"女\" }">
    		<select class="form-control" id="gender" name="gender"><option value="男">男生</option><option value="女" selected="true">女生</option></select>
    	</c:when>
    	<c:otherwise>
    		<select class="form-control" id="gender" name="gender"><option value="男">男生</option><option value="女">女生</option></select>
    	</c:otherwise>
    </c:choose>
    
  </div>
  <div class="form-group">
    <label for="dept">学生专业</label>
    <select class="form-control" id="dept" name="department"><option value="">请选择</option>
    <c:forEach items="${requestScope.departments }" var="dept">
    	<c:if test="${requestScope.student.department.departmentName == dept.departmentName }">
    		<option value="${dept.departmentId }" selected="true">${dept.departmentName }</option>
    	</c:if>
    	<c:if test="${requestScope.student.department.departmentName != dept.departmentName }">
    		<option value="${dept.departmentId }">${dept.departmentName }</option>
    	</c:if>
    </c:forEach>
    </select>
  </div>
    <div class="form-group">
      <label for="class">学生班级</label>
        <select class="form-control" id="cls" name="cls"><option value="">请选择</option>
            <c:forEach items="${requestScope.classes }" var="clz">
                <c:if test="${requestScope.student.className == clz.id }">
                    <option value="${clz.id}" selected="true">${clz.className }</option>
                </c:if>
                <c:if test="${requestScope.student.className != clz.id }">
                    <option value="${clz.id }">${clz.className }</option>
                </c:if>
            </c:forEach>
        </select>
      <%--<input type="text" class="form-control" id="class" name="class" placeholder="请输入班级"  value="${requestScope.student.className }">--%>
    </div>
  <div class="form-group">
    <label for="password">登录密码</label>
    <input type="password" name="password" class="form-control" id="password" placeholder="请输入修改后的密码">
  </div>
  <%--<in type="button" class="btn btn-success">提交</in>--%>
      <input type="submit" class="btn btn-success" value="提交">
</form>
</div>
  <script type="text/javascript" src="${pageContext.request.contextPath }/res/js/register.js"></script>
</body>
</html>
