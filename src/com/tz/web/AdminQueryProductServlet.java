package com.tz.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.tz.vo.Condition;

/**
 * 条件查询
 * @author Nocturne
 *
 */
public class AdminQueryProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//1.收集表单数据
		Map<String, String[]> map = request.getParameterMap();
		
		//2.将收集的表单数据封装进实体类
		Condition con = new Condition();
		
		try {
			BeanUtils.populate(con, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//3.查询到的结果
		AdminProductService service = new AdminProductService();
		List<Product> list = new ArrayList<Product>();
		try {
			list = service.queryProductByCondition(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//查询类别的信息
		List<Category> categoryList = null;
		try {
			categoryList = service.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//将查询到的数据放到request域中
		request.setAttribute("categoryList", categoryList);
		
		request.setAttribute("con", con);
		
		request.setAttribute("productList", list);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
