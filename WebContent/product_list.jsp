<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="row" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
			</ol>
		</div>

<%-- 		<c:forEach items="${productList }" var="product">   //所有商品在一页显示
			<div class="col-md-2" style="height:250px">
				<a href="product_info.htm"> <img src="${pageContext.request.contextPath }/${product.pimage }"
					width="170" height="170" style="display: inline-block;">
				</a>
				<p>
					<a href="product_info.html" style='color: green'>${product.pname }</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${product.shop_price }</font>
				</p>
			</div>
		</c:forEach> --%>
		
		<!-- 分页显示 -->
		<c:forEach items="${pageBean.productList }" var="product">
			<div class="col-md-2" style="height:250px">
				<a href="${pageContext.request.contextPath }/productInfo?pid=${product.pid }&cid=${cid }&currentPage=${pageBean.currentPage }"> <img src="${pageContext.request.contextPath }/${product.pimage }"
					width="170" height="170" style="display: inline-block;">
				</a>
				<p>
					<a href="${pageContext.request.contextPath }/productInfo?pid=${product.pid }&cid=${cid }&currentPage=${pageBean.currentPage }" style='color: green'>${product.pname }</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${product.shop_price }</font>
				</p>
			</div>
		</c:forEach>


	</div>
	
	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<!-- 实现上一页 -->
			<c:if test="${pageBean.currentPage == 1 }"><!-- 判断当前页是否为第一页 -->
				<li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span
					aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:if test="${pageBean.currentPage != 1 }">
				<!-- 全部商品
				<li><a href="${pageContext.request.contextPath }/webProductList?currentPage=${pageBean.currentPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				 -->
				 
				 <!-- 商品分类之后 -->
				<li><a href="${pageContext.request.contextPath }/productListByCid?cid=${cid}&currentPage=${pageBean.currentPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
					
			<!-- 实现导航栏 -->
			<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
				<c:if test="${pageBean.currentPage == page }">
					<li class="active"><a href="javascript:void(0);">${page }</a></li><!-- 此时在当前页面，颜色改变，不可再点击-->
				</c:if>
				<c:if test="${pageBean.currentPage != page }">
					<%-- <li><a href="${pageContext.request.contextPath }/webProductList?currentPage=${page }">${page }</a></li>   全部商品分页显示--%><!-- 将点击的页数传递到后台 -->
					<li><a href="${pageContext.request.contextPath }/productListByCid?cid=${cid}&currentPage=${page }">${page }</a></li> <!-- 商品类别分页显示 -->
				</c:if>
			</c:forEach>
			
			<!-- 实现下一页 -->
			<c:if test="${pageBean.currentPage == pageBean.totalPage }"><!-- 判断当前页是否为第一页 -->
				<li class="disabled"><a href="javascript:void(0)" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
			<c:if test="${pageBean.currentPage != pageBean.totalPage }">
				<!-- 分页显示全部商品时
				<li><a href="${pageContext.request.contextPath }/webProductList?currentPage=${pageBean.currentPage+1 }" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				 -->
				 
				 <!-- 商品分类之后 -->
				<li><a href="${pageContext.request.contextPath }/productListByCid?cid=${cid}&currentPage=${pageBean.currentPage+1 }" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
		</ul>
	</div>	
	<!-- 分页结束 -->
	
	
	

	<!--商品浏览记录-->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="">more</a>
		</div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden;">

			<ul style="list-style: none;">
				<c:forEach items="${arrayList }" var="pro">
					<li style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
						<img src="${pageContext.request.contextPath}/${pro.pimage}" width="130px" height="130px" />
					</li>
				</c:forEach>
			</ul>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>