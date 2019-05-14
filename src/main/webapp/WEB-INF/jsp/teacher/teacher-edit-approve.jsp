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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/res/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/wangEditor.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ueditor/Comment-ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
  body{
    font-family: "Microsoft Yahei";
    .text{
	    width: 300px;
	    height: 200px;
	    position: absolute;
	    left: 50%;
	    margin-left: -150px;
	    background-image: -webkit-linear-gradient(left,blue,#66ffff 10%,#cc00ff 20%,#CC00CC 30%, #CCCCFF 40%, #00FFFF 50%,#CCCCFF 60%,#CC00CC 70%,#CC00FF 80%,#66FFFF 90%,blue 100%);
	    -webkit-text-fill-color: transparent;/* 将字体设置成透明色 */
	    -webkit-background-clip: text;/* 裁剪背景图，使文字作为裁剪区域向外裁剪 */
	    -webkit-background-size: 200% 100%; 
	    -webkit-animation: masked-animation 4s linear infinite;
	    font-size: 35px;
	}

}
</style>
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
        <li><a href="/">首页</a></li>
        <li><a href="${pageContext.request.contextPath }/teacher/direction">选题申报</a></li>
        <li><a href="${pageContext.request.contextPath }/teacher/info">信息管理</a></li>
        <li ><a href="${pageContext.request.contextPath }/teacher/notice">公告</a></li>
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
    <h4>论文审核页面</h4>
    <div id='contentDiv' style="display: none;">
     ${paper.paper_content}
    </div>
  <form action="${pageContext.request.contextPath }/teacher/approvePaper" method="post">
   	<div style="float:left;width:740px;">
		<textarea id="content" style="display: none;" name="comment.content"></textarea>
		<script id="container" type="text/plain"></script>
	</div>
	<script type="text/javascript">
		var editor = UE.getEditor('container');
	</script>
<!--     <button class="btn btn-success" id="submit">提交</button> -->
  </section>
  <div style="width: 768px;height: 52px;line-height: 50px;float: left;border-left:1px solid  #C2D5E3;border-right:1px solid  silver;background-color: #F8F8F8"
		align="left">
  <input type="hidden" name='id' value="${paper.id}">
  		结果：
		<select name="paper_status" id="paper_status">
			<option value="0">合格</option>
            <option value="3">基本合格</option>
			<option value="1">不合格</option>
		</select>
		评语：
		<input type="text"  id="t_comment" name="comment" width="1000px"><br/>
  </div>
  		<div
		style="width: 768px;height: 52px;line-height: 50px;float: left;border-left:1px solid  #C2D5E3;border-right:1px solid  silver;background-color: #F8F8F8"
		align="left">
		
		<input type="button" value="提交审核" class="butt"
			style="margin-top: 10px;margin-left: 20" /><font size="2px;">提示：提交之后不可修改</font>
		</div>
		
  </form>
  <script type="text/javascript">
  
  $(function(){
	  editor.addListener("ready", function () {
		  editor.setContent($("#contentDiv").html());
		  });
  })
  $(".butt").click(function(){
	var va = editor.getContent();
    var grade = $("#grade").val();
    var comfirm_text = "提交之后不允许了，请您确认该学生论文信息";
    if(confirm(comfirm_text)){
    	$.post("${pageContext.request.contextPath }/teacher/approvePaper",{
    			"id":"${paper.id}",
				"paper_content":va,
				"comment":$("#t_comment").val(),
				"paper_status":$("#paper_status").val()
			},function(result){
			   if(result.status=='success'){
				   alert("审核成功!");
				   var studentNum = "${paper.f_student_number}"
				   window.location.href="${pageContext.request.contextPath}/teacher/grade/"+studentNum+".html";
			   }else{
				   alert("更新失败");
			   }
			});
    }else{
//       alert("Cancle")
    }
  });
  </script>
</div>
</body>
</html>
 