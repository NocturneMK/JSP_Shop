package com.tz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.service.ProductService;
import com.tz.domain.Category;
import com.tz.domain.Product;
/**
 * 商城首页数据显示
 * @author Nocturne
 *
 */
public class IndexServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		//准备热门商品
		List<Product> hotProduct = service.findHotProductList();
		
		//准备最新商品
		List<Product> newProduct = service.findNewProductList();
		
		//准备导航栏中的商品类别
		List<Category> category = service.findAllCategory();
		
		request.setAttribute("hotProduct", hotProduct);
		request.setAttribute("newProduct", newProduct);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/index.jsp").forward(request, response);;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
