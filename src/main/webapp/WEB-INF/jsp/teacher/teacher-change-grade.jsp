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
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/ajaxfileupload.js"></script>
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
    <script>
        function goPreview(item) {
            window.open( '${baseUrl}onlinePreview?url='+encodeURIComponent('${baseUrl}' +  'demo/'+item));
        }

        function doReview(src) {

           var _tab =  $(src).closest('table');

            console.debug(_tab);

           var _paper_status =  _tab.find(".paper_status");


           if(!_paper_status.val()){
               alert("请选择论文结果");
               return
           }

            var _comment =  _tab.find(".comment");

           if(!_comment.val()){
               alert("请对学生论文进行审批");
               _comment.focus()
               return
           }

            var name  = document.getElementById("paper_file").value;

            if(!(/.(doc)$/.test(name) || /.(docx)$/.test(name) || /.(pdf)$/.test(name))){
                alert("请选择正确的doc,docx,pdf文件 (仅支持.doc,.docx,.pdf格式)!");
                return false;
            }

           var _p_id =  _tab.find(".p_id");

            var url = "${pageContext.request.contextPath }/teacher/uploadPaper" +
            "?id="+_p_id.val()+"&comment="+_comment.val()+"&paper_status="+_paper_status.val();
            $.ajaxFileUpload({
                url: url ,
                secureuri : false,
                type: 'POST',
                fileElementId : 'paper_file',
                dataType : 'multipart/form-data',
                async: true,
                success: function (data) {
                    var data = $.parseJSON( data.replace(/<.*?>/ig,""))
                    if(data.success){
                        alert(data.reslut)
                        location.reload()
                    }else{

                        alert(data.reslut)

                    }
                },
                error: function (returndata) {
                    alert(returndata);
                }
            });
        }
    </script>
  <section>
    <p align="center" style="font-size: 30px">论文信息<p>
    <c:if test="${papers!= null && fn:length(papers) > 0}">
    	
	    <c:forEach var="paper" items="${papers}">
	    
	    <%--<table border="1" cellspacing="0" cellpadding="0"width="100%" height="100%">--%>
	    <%----%>
	    <%----%>
	    <%----%>
	    	<%--<tr align="center"><td width="10%">论文内容</td><td colspan="3">${paper.paper_content }</td></tr>--%>
	    	<%--<c:if test="${paper.paper_status !=  2}">--%>
	    	<%----%>
	    	<%--<tr align="center"><td width="10%">老师评语</td><td td colspan="3">${paper.comment}</td></tr>--%>
	    	<%----%>
	    	<%--</c:if>--%>
	    	<%--<tr style="colspan" align="center">--%>
	    		<%--<td width="10%">论文状态</td>--%>
	    		<%--<td width="40%">--%>
	    			<%--<c:if test="${paper.paper_status ==  0}"><p style="font-size: 20px;color: green">合格</p></c:if>--%>
                    <%--<c:if test="${paper.paper_status ==  3}"><p style="font-size: 20px;color: red">基本合格</p></c:if>--%>
	    			<%--<c:if test="${paper.paper_status ==  1}"><p style="font-size: 20px;color: red">不合格</p></c:if>--%>
	    			<%--<c:if test="${paper.paper_status ==  2}">待审批</c:if>--%>
	    		<%--</td>--%>
	    		<%--<td width="10%">操作</td>--%>
	    		<%--<td width="40%">--%>
		    		<%--<c:if test="${paper.paper_status ==  2}">--%>
		    		<%--<a  class="small blue button" href="${pageContext.request.contextPath }/teacher/approve?id=${paper.id}" >审批</a>--%>
		    		<%--</c:if>--%>
	    		<%--</td>--%>
	    	<%--</tr>--%>
	    <%--</table>--%>
	      <div style="height: 50px"></div>
	    </c:forEach>
    </c:if>
    <c:if test="${papers== null || fn:length(papers) == 0}">
    
    	<div><font color="red">暂无论文信息...</font></div>
    
    </c:if>

<c:if test="${papers!= null && fn:length(papers) > 0}">
    <c:forEach var="paper" items="${papers}">
       <form>
      <table class="table">
          <input type="hidden" class="p_id" value="${paper.id}">
          <tr><p style="font-size: 20px;color: #0C0C22">${paper.posted_time}上传的论文详情~</p></tr>
          <tr><td>论文结果</td>
                <td>
                    <%--<c:if test="${paper.paper_status ==  0}"><p style="font-size: 20px;color: green">合格</p></c:if>--%>
                    <%--<c:if test="${paper.paper_status ==  3}"><p style="font-size: 20px;color: red">基本合格</p></c:if>--%>
                    <%--<c:if test="${paper.paper_status ==  1}"><p style="font-size: 20px;color: red">不合格</p></c:if>--%>
                    <%--<c:if test="${paper.paper_status ==  2}">待审批</c:if>--%>
                    <c:if test="${paper.paper_status == 2}">
                       <select  class="paper_status"  ${paper.paper_status != 2 ? 'disabled' : ''} name="paper_status" class="form-control" >
                           <option value="">---请选择---</option>
                           <option value="1" ${paper.paper_status == 1 ? 'selected' : ''}>不合格</option>
                           <option value="3" ${paper.paper_status == 3 ? 'selected' : ''}>基本合格</option>
                           <option value="0" ${paper.paper_status == 0 ? 'selected' : ''}>合格</option>
                       </select>
                    </c:if>
                    <c:if test="${paper.paper_status ==  0}"><p style="font-size: 20px;color: green">合格</p></c:if>
                    <c:if test="${paper.paper_status ==  3}"><p style="font-size: 20px;color: red">基本合格</p></c:if>
                    <c:if test="${paper.paper_status ==  1}"><p style="font-size: 20px;color: red">不合格</p></c:if>
              </td>
          </tr>
          <tr>
            <td>评语：</td>
              <td>
                  <c:if test="${paper.paper_status == 2}">
                      <input type="text" class="comment"   name="comment" value="${paper.comment}" style="width: 100%" >
                  </c:if>
                  <c:if test="${paper.paper_status != 2}">
                      ${paper.comment}
                  </c:if>
              </td>
          </tr>
          <tr><td >预览</td><td> <a class='btn btn-default' target='_blank' onclick="goPreview( '${paper.uu_file_name}');" >预览</a> </td></tr>
          <c:if test="${paper.paper_status == 2}">
          <tr><td>上传评审</td><td><input type="file" id="paper_file" name="paper_file"> </td></tr>
          </c:if>
          <c:if test="${paper.paper_status != 2}">
            <tr><td >操作</td><td><a class='btn btn-default' target='_blank' onclick="goPreview( '${paper.uu_t_reply_name}');" >预览审批</a> </td></tr>
          </c:if>

           <c:if test="${paper.paper_status == 2}">
            <tr><td><input type="button"  class="btn btn-success" onclick="doReview(this)" value="评审"></td></tr>
           </c:if>

      </table>
       </form>
    </c:forEach>
</c:if>


  <h4>选题题目成绩</h4>
    <table class="table">
       <tr><td class="col-md-6">课题名称</td><td class="col-md-6">${requestScope.info.topic.topic }</td></tr>
      <tr><td>学生姓名</td><td>${requestScope.info.student.studentName }</td></tr>
      <tr><td>学生学号</td><td>${requestScope.info.student.studentNumber }</td></tr>
      <tr><td>联系电话</td><td>${requestScope.info.student.phone }</td></tr>
      <tr><td>专业</td><td>${requestScope.info.student.department.departmentName }</td></tr>
      <tr><td>完成进度</td><td>${requestScope.info.processing }</td>
      <tr><td>成绩评定</td><td>
        <select class="form-control" id="grade">
         	<c:forEach items="${requestScope.grade }" var="item">
         		<option var="${item}">${item}</option>
         	</c:forEach>
        </select>
      </td></tr>
    </table>
     <button class="btn btn-success" id="submit">提交</button>
   
  </section>
  <script type="text/javascript">
  $("#submit").click(function(){
    var grade = $("#grade").val();
    var comfirm_text = "提交之后不允许修改成绩了，请您确认该学生成绩和信息：\n学生：${requestScope.info.student.studentName }\n选题：${requestScope.info.topic.topic }\n成绩："+grade;
    if(confirm(comfirm_text)){
    	$.post("${pageContext.request.contextPath }/teacher/grade/update",{
				"studentNumber":"${requestScope.info.student.studentNumber}",
				"grade":grade
			},function(result){
			   if(result.status=='success'){
				   alert("更新成功!");
				   window.location.href="${pageContext.request.contextPath }"+"/";
			   }else{
				   alert("更新失败");
			   }
			});
    }else{
      alert("Cancle")
    }
  });
  </script>
</div>
</body>
</html>
 