package com.tz.web;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.domain.Product;
import com.tz.service.AdminProductService;

/**
 * 商品主页 ，显示所有的商品
 * @author Nocturne
 *
 */
public class AdminproductListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		AdminProductService adminproductService = new AdminProductService();
		List<Product> list = null;
		try {//findAllProduct()查询所有商品，此方法在service层实现
			list = adminproductService.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//将查询到的数据放到request域中
		request.setAttribute("productList", list);
		//转发到jsp页面
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
