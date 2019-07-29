package com.tz.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.domain.Product;
import com.tz.service.ProductService;
import com.tz.vo.PageBean;

/**
 * 根据类别进行分页
 * @author Nocturne
 *
 */
public class ProductListByCidServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String currentPagestr = request.getParameter("currentPage");
		if (currentPagestr == null) {//默认显示第一页
			currentPagestr = "1";
		}
		int  currentPage = Integer.parseInt(currentPagestr);

		int currentCount = 12;
		
		ProductService service = new ProductService();
		PageBean<Product> pageBean = service.findPageBeanByCid(cid, currentPage, currentCount);
		
		
		//商品浏览记录
		//定义一个记录历史商品的集合
		ArrayList<Product> arrayList = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] split = pids.split("~");
					for (String pid : split) {
						Product pro = service.findProductByPid(pid);
						arrayList.add(pro);
					}
				}
			}
		}
		request.setAttribute("arrayList", arrayList);
		
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
