package com.tz.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 编辑商品
 * @author Nocturne
 *
 */
public class AdminUpdateProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("pid");
		AdminProductService service = new AdminProductService();
		
		// 从表单获得更改后的数据
		Product product = new Product();
		Map<String, String[]> map = request.getParameterMap();
		String cid = request.getParameter("cid");

		try {
			BeanUtils.populate(product, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		product.setPid(pid);
		product.setPimage("products/1/xiaomi9.jpg");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		product.setPflag(0);
		Category category = new Category();
		category.setCid(cid);
		product.setCategory(category);

		try {
			service.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/adminProductList");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
