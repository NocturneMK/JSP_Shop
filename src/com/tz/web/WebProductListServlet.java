package com.tz.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.domain.Product;
import com.tz.service.ProductService;
import com.tz.vo.PageBean;

/**
 * 分页功能的商品查询
 * @author Nocturne
 *
 */
public class WebProductListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 所有商品在一个页面显示
		 */
		ProductService service = new ProductService();
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = service.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//request.setAttribute("productList", productList);//所有商品在一页显示、
		
		
		
		/**
		 * 分页显示
		 */
		//每页显示条数----固定的
		int currentCount = 12;
		//当前页数----不固定，模拟第一页
		//int currentPage = 1;
		//从浏览器实时获取当前页数
		String parameter = request.getParameter("currentPage");
		if (parameter==null) {//第一次访问时，没有点击第几页，此时置为1
			parameter = "1";
		}
		int currentPage = Integer.parseInt(parameter);
		
		PageBean<Product> pageBean = new PageBean<Product>();//每一页显示的商品数据封装到PageBean对象中去
		try {
			pageBean = service.findPageBean(currentPage, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("pageBean", pageBean);//分页显示，每一页的商品数据
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
