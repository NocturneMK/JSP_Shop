package com.tz.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.tz.domain.Category;
import com.tz.domain.Product;
import com.tz.service.AdminProductService;

/**
 * 添加商品
 * @author Nocturne
 *
 */
public class AdminAddProductServlet extends HttpServlet {

	/**
	 * 添加商品的步骤：
	 * 1.获取表单数据
	   2.封装数据
	   3.传递数据到service层
	   4.转发跳转
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//1.获取表单数据
		Map<String, String[]> map = request.getParameterMap();//Map getParameterMap()返回所有的参数和值组成的Map对象
		String cid = request.getParameter("cid");
		
		//2.封装数据
		Product product = new Product();
		try {
			BeanUtils.populate(product, map);//将map封装到实体类product里面
			//异常出现的原因：实体类中的属性要和表单中name属性的值对应
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		/*手动设置表单中没有但实体类（数据库）有的数据*/
		//private String pid;
		product.setPid(UUID.randomUUID().toString());// UUID 来作为数据库数据表主键是非常不错的选择，保证每次生成的UUID 是唯一的。
		//private String pimage;
		product.setPimage("products/1/xiaomi9.jpg");
		//private String pdate;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		//private int pflag;此处表示商品是否下架。0代表商品未下架
		product.setPflag(0);
		//private Category category;商品所属分类。在product表中是cid（varchar），同时是外键，对应category表
		Category category = new Category();
		category.setCid(cid);
		product.setCategory(category);
		
		//3.传递数据到service层
		AdminProductService adminproductService = new AdminProductService();
		try {
			adminproductService.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//4.重定向，回到商品列表页面（要重新查询到所以商品，所以用重定向）
		response.sendRedirect(request.getContextPath() + "/adminProductList");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
