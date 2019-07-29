package com.tz.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tz.domain.Product;
import com.tz.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String currentPage = request.getParameter("currentPage");
		String cid = request.getParameter("cid");
		
		//实现浏览记录，当查看某个商品详细信息时，将pid储存到cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					//pids:pid-pid-pid
					pids = cookie.getValue();
					
					//将pids拆成一个数组
					String[] split = pids.split("~");
					List<String> list = Arrays.asList(split);//将数组转成List集合
					LinkedList<String> linkedList = new LinkedList<String>(list);
					
					//判断集合中是否存在当前的pid
					if(linkedList.contains(pid)) {
						//包含当前查看的这个pid
						linkedList.remove(pid);
						linkedList.addFirst(pid);
					}else {
						//不包含当前查看的这个pid
						linkedList.addFirst(pid);
					}
					
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i<linkedList.size() && i<6; i++) {
						sb.append(linkedList.get(i));
						sb.append("~");
					}
					pids = sb.substring(0, sb.length()-1);//不要最后一个“~”
				}
			}
		}
		Cookie cookie = new Cookie("pids", pids);
		response.addCookie(cookie);
		
		
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
