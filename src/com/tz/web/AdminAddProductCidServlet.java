package com.tz.web;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.service.AdminProductService;
import com.tz.domain.Category;
/**
 * 这个servlet的作用是为了专门查询商品所属的类别
 * @author Nocturne
 *
 */
public class AdminAddProductCidServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		AdminProductService adminProductService = new AdminProductService();
		List<Category> list = null;
		try {
			list = adminProductService.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//将查询到的数据放到request域中
		request.setAttribute("categoryList", list);
		//转发到jsp页面
		request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
