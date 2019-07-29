<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${empty user }"><!-- user为空值，没有登陆 -->
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${!empty user }">
				<li style="color:red">欢迎${user.username }</li>
				<li><a href="${pageContext.request.contextPath }/user?method=logout">退出</a></li>
			</c:if>
			
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/index">首页</a>
			</div>
			
			<%-- 
			<%
			头部是所有页面共有的，所有每访问一个页面都要查询商品类别
			解决方法一：将Java脚本写在header.jsp中。引入包即可
				//准备导航栏中的商品类别
				List<Category> category = service.findAllCategory();
				
				request.setAttribute("hotProduct", hotProduct);
			%> 
			--%>
			<!-- 方法二：使用Ajax的异步访问 -->
			<script type="text/javascript">
			$(function(){
				var content = "";
				$.post(
					"${pageContext.request.contextPath }/categoryList",
					function(data){
						//json格式：[{"cid":"1","cname":"手机数码"}, {"cid":"2","cname":"电脑办公"}]
						for(var i=0; i<data.length; i++){
							content += "<li><a href='${pageContext.request.contextPath }/productListByCid?cid=" + data[i].cid + "'>" + data[i].cname + "</a></li>";//显示商品类别，并且点击链接之后，按照类型显示
						}
						$("#categoryUl").html(content);
					},
					"json"
				);
			});
				
			
			</script>
			
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
					
					<!-- <li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li> -->
					<%-- <c:forEach items="${category }" var="ca">
						<li><a href="#">${ca.cname }</a></li>
					</c:forEach> --%>
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>