<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
	//点击添加按钮后的操作
	function addProduct() {
		window.location.href = "${pageContext.request.contextPath}/adminAddProductCid";
	}
	
	//点击删除按钮后的操作
	function deleteProduct(pid){
		var isDel = confirm("您确定要删除该数据吗？");
		if(isDel){//进来就表示要删除
			window.location.href = "${pageContext.request.contextPath}/adminDeleteProduct?pid="+pid;//路径做一个拼接，可以在servlet中可以获取到pid
		}
	}
	
	//点击编辑按钮
	function updateProduct(pid){
		window.location.href = "${pageContext.request.contextPath}/adminSelectUpdateProduct?pid="+pid;
	}
</script>
</HEAD>
<body>
	<br>
	<!-- 点击搜索按钮时，是提交到这个from表单的链接 -->
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/adminQueryProduct"
		method="post">
		
		名称：<input type="text" name="pname" value="${con.pname }"/>&nbsp;&nbsp;
		热门商品：<select id="is_hot" name="is_hot">
					<option value="">不限</option>
					<option value="1">是</option>
					<option value="0">否</option>
			   </select>&nbsp;&nbsp;
		商品类别：<select name="cid" id="cid">
					<c:forEach items="${categoryList }" var="category">
						<option value="${category.cid }">${category.cname }</option>
					</c:forEach>
				</select>
		<input type="submit" value="搜索"/>
		
		
		
		<!-- 下面代码中的提交，是使用js在对应的函数中提交 -->
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">删除</td>
							</tr>

							<c:forEach items="${productList }" var="pro" varStatus="vs">

								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${vs.count }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><img width="40" height="45"
										src="${pageContext.request.contextPath}/${pro.pimage}"></td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.pname }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.shop_price }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.is_hot==1 ? "是" : "否" }</td>
										
									<td align="center" style="HEIGHT: 22px">
									<a href="javascript:void(0);" onclick="updateProduct('${pro.pid}')">
									<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
									</a></td>

									<!-- href=”javascript:void(0);”这个的含义是，让超链接去执行一个js函数，而不是去跳转到一个地址，
										  而void(0)表示一个空的方法，也就是不执行js函数。
										  为什么要使用href=”javascript:void(0);”
										 javascript:是伪协议，表示url的内容通过javascript执行。
										 void(0)表示不作任何操作，这样会防止链接跳转到其他页面。
										  这么做往往是为了保留链接的样式，但不让链接执行实际操作， 
									-->
									<td align="center" style="HEIGHT: 22px">
									<a href="javascript:void(0);" onclick="deleteProduct('${pro.pid}')"> 
									<img src="${pageContext.request.contextPath}/images/i_del.gif"
										 width="16" height="16" border="0" style="CURSOR: hand">
									</a></td>
								</tr>

							</c:forEach>

						</table>
					</td>
				</tr>

			</TBODY>
		</table>
	</form>
</body>
</HTML>

