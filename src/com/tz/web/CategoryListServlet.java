package com.tz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tz.domain.Category;
import com.tz.service.ProductService;

/**
 * 商品类别，以json类型返回
 * @author Nocturne
 *
 */
public class CategoryListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		ProductService service = new ProductService();
		List<Category> categoriesList = service.findAllCategory();
		
		//转换成json类型
		Gson gson = new Gson();
		String json = gson.toJson(categoriesList);
		response.getWriter().write(json);//在前台输出
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
