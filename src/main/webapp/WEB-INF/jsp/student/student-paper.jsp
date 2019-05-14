<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>学位论文提交系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/res/css/wangEditor.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/wangEditor.min.js"></script>
  <style type="text/css">
  body{
    font-family: "Microsoft Yahei";
  }
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/ajaxfileupload.js"></script>  
<script type="text/javascript">
function doUpload() {  
 	var name  = document.getElementById("paper_file").value;
 	if(!(/.(doc)$/.test(name) || /.(docx)$/.test(name) || /.(pdf)$/.test(name))){
	   alert("请选择正确的doc,docx,pdf文件 (仅支持.doc,.docx,.pdf格式)!");
       return false;
   	}
	var url = "${pageContext.request.contextPath }/student/uploadPaper?teacherNumber="+
		"${requestScope.teacher.teacherNumber}&studentNumber=${requestScope.student.studentNumber}";
   $.ajaxFileUpload({  
        url: url ,  
        secureuri : false,
        type: 'POST',  
		fileElementId : 'paper_file',
		dataType : 'multipart/form-data',
        async: true,  
        success: function (data) {

	       	if(data.indexOf('true')){
	       		
	    		alert("上传成功")
	       		location.reload()
	       	}else{
	       		
	       		alert("上传失败")
	       		
	       	}
        },  
        error: function (returndata) {  
            alert(returndata);  
        }  
   });  
}

$(function(){
	var paper_flag = "${student.paper_flag}"
	if(paper_flag == 0){
		$("#div0").show();
		$("#div1").hide();
		$("#div2").hide();
        $("#div3").hide();
	}else if(paper_flag == 1){
		$("#div1").show();
		$("#div0").hide();
		$("#div2").hide();
        $("#div3").hide();
	}else if(paper_flag == 2){
		$("#div2").show();
		$("#div1").hide();
		$("#div0").hide();
        $("#div3").hide();
	}else if(paper_flag == 3){
        $("#div3").show();
        $("#div2").hide();
        $("#div1").hide();
        $("#div0").hide();
    }
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
      <a class="navbar-brand" href="${pageContext.request.contextPath }">学位论文提交系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="nav-bar">
      <ul class="nav navbar-nav">
        <li ><a href="/">首页</a></li>
        <li><a href="${pageContext.request.contextPath }/student/select">选题管理</a></li>
        <li><a href="${pageContext.request.contextPath }/student/info">信息管理</a></li>
          <li class="active"><a href="${pageContext.request.contextPath }/student/paper">论文管理</a></li>
           <li><a href="${pageContext.request.contextPath }/student/notice">公告</a></li>
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
    <h3>欢迎您，${sessionScope.username }</h3>
    <p>欢迎使用学位论文提交系统</p>
  </div>
</div>
<div class="container">
  <section>
  
  
<!--     <h4>论文情况</h4> -->
              <div id="div3">
                  您还未选题，请先进行选题后才能提交论文...
              </div>
    		<div id="div0">
    		
    			您有上传成功的论文，不能再次上传...
    		</div>
    		<div id="div2">
    			您有正在核审的论文，不能再次上传...
    		</div>
			<div id="div1">
		   		<p>Oops...上传论文</p>
		   		<form id= "uploadForm">  
		   		<input type="file" name= 'paper_file' id="paper_file">
		   		<input type="button" onclick="doUpload();" value="上传">
		   		</form>
		   		</div>
    	 <c:if test="${papers!= null && fn:length(papers) > 0}">
    	
	    <c:forEach var="paper" items="${papers}">
	    
	    <table border="1" cellspacing="0" cellpadding="0"width="100%" height="100%">
	    
	    
	    
	    	<tr align="center"><td width="10%">论文内容</td><td colspan="3">${paper.paper_content }</td></tr>
	    	<c:if test="${paper.paper_status !=  2}">
	    	
	    	<tr align="center"><td width="10%">老师评语</td><td td colspan="3">${paper.comment}</td></tr>
	    	
	    	</c:if>
	    	<tr style="colspan" align="center">
	    		<td width="10%">论文状态</td>
	    		<td width="40%">
	    			<c:if test="${paper.paper_status ==  0}"><p style="font-size: 20px;color: green">合格</p></c:if>
                    <%--<c:if test="${paper.paper_status ==  0}"><p style="font-size: 20px;color: green">基本合格</p></c:if>--%>
	    			<c:if test="${paper.paper_status ==  1}"><p style="font-size: 20px;color: red">不合格</p></c:if>
	    			<c:if test="${paper.paper_status ==  2}">待审批</c:if>
                    <c:if test="${paper.paper_status ==  3}"><p style="font-size: 20px;color: red">基本合格</p></c:if>
	    		</td>
	    		<td width="10%">操作</td>
	    		<td width="40%">
	    		</td>
	    	</tr>
	    </table>
	      <HR style="border:3 double #987cb9" width="80%" color=#987cb9 SIZE=3>
	    </c:forEach>
	    
	    
    </c:if>
  </section>
</div>
</body>
</html>
