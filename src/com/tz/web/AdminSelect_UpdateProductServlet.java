package com.tz.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.tz.domain.Category;
import com.tz.domain.Product;
import com.tz.service.AdminProductService;

/**
 * 回显数据，将要编辑商品的原数据得到
 * @author Nocturne
 *
 */
public class AdminSelect_UpdateProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("pid");
		AdminProductService service = new AdminProductService();
		Product product = new Product();
		try {
			product = service.findProductById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//查询类别的信息
		List<Category> list = null;
		try {
			list = service.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//将查询到的数据放到request域中
		request.setAttribute("categoryList", list);
		
		//用转发
		request.setAttribute("product", product);
		request.getRequestDispatcher("admin/product/edit.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
