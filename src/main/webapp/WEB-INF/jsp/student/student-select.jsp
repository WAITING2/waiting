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
  <nav class="navbar navbar-inverse" style="border-radius:0px;">
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
        <li><a href="/">首页</a></li>
        <li class="active"><a href="${pageContext.request.contextPath }/student/select">选题管理</a></li>
        <li><a href="${pageContext.request.contextPath }/student/info">信息管理</a></li>
        <li><a href="${pageContext.request.contextPath }/student/info">论文管理</a></li>
         <li><a href="${pageContext.request.contextPath }/student/notice">公告</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container">
<c:choose>
	<c:when test="${requestScope.selectionInfo != null }">
		<h3 class="text-center">抱歉，你已经进行选题了，不允许再进行选题操作</h3>
  		<h5 class="text-center">如有需要，请和教务处老师联系</h5>
  		<p class="text-center"><a href="${pageContext.request.contextPath }">返回首页</a></p>
  		</div>
	</c:when>
	<c:otherwise>
	<h4>毕业设计选题</h4>
  <table class="table">
    <tr><th>课题</th><th>专业</th><th>指导老师</th><th>操作</th></tr>
    <c:forEach items="${requestScope.topics }" var="item">
    	<tr><td>${item.topic }</td><td>${item.teacher.department.departmentName }</td><td>${item.teacher.teacherName }</td><td><button class="btn btn-success btn-sm" onclick="do_comfirm('${item.topic }','${item.teacher.teacherName }','${item.id }')">选题</button></td></tr>
    </c:forEach>
  </table>
</div>
<script type="text/javascript">
  function do_comfirm(title,teacher,id){
    var msg = "请您确认以下选择信息\n选题名："+title+"\n指导老师:"+teacher+"\n注意!!\n 一旦确认提交便不可以修改选题，请慎重选题";
    if(confirm(msg)){
    	$.post("${pageContext.request.contextPath }/student/select/handle",{
			"topic":id
		},function(result){
		   if(result.status=='ok'){
			   alert("选择成功!");
			   window.location.href="${pageContext.request.contextPath }/student/select";
		   }else{
			   alert("选择失败");
		   }
		});
    }
  }
</script>
	</c:otherwise>
</c:choose>

</body>
</html>

