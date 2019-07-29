package com.tz.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.mchange.v2.codegen.bean.BeangenUtils;
import com.tz.domain.Order;
import com.tz.domain.OrderItem;
import com.tz.domain.Product;
import com.tz.domain.User;
import com.tz.service.ProductService;
import com.tz.utils.CommonsUtils;
import com.tz.vo.Cart;
import com.tz.vo.CartItem;

/**
 * Servlet代码抽取
 * @author Nocturne
 *
 */
public class ProductServlet extends BaseServlet {

	/*
	简化代码方法一：
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//模块中的功能方法进行区分
		String method = request.getParameter("method");   //在请求的Servlet后面拼接上：?method=请求方法名
		if ("addProductToCart".equals(method)) {
			addProductToCart(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		
	}
	*/
	
	
	//将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		ProductService service = new ProductService();
		
		String pid = request.getParameter("pid");
		String buyNum = request.getParameter("buyNum");
		
		Product product = service.findProductByPid(pid);//得到要加入购物车商品的整个信息
		
		//小计
		int num = Integer.parseInt(buyNum);
		double subtotal = product.getShop_price() * num;
		
		//封装购物项
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setBuyNum(num);
		cartItem.setSubTotal(subtotal);
		
		//获得购物车   判断session中是否存在cart
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			//没有购物车
			cart = new Cart();
		}
		cart.getCartItems().put(pid, cartItem);
		double total = cart.getTotal() + subtotal;
		cart.setTotal(total);
		//将购物车再次存放到session中
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");//用重定向，由于两次请求，不能用request域
		
	}
	
	
	/**
	 * 删除购物车中的购物项
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delProCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> cartItems = cart.getCartItems();
			
			//修改总价
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubTotal());
			
			cartItems.remove(pid);//不需要删除数据库中的数据，只用删除session域中的数据
			cart.setCartItems(cartItems);
			
			
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void removeCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	
	/**
	 * 提交订单
	 */
	public void buttonOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//判断用户是否登陆，没有登陆就不需要封装下面的数据
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			//没有登陆
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		//封装order对象，传递到service层
		Order order = new Order();
		order.setoId(CommonsUtils.getUUID());
		order.setOrderTime(new Date());
		
		Cart cart = (Cart) session.getAttribute("cart");
		double total = cart.getTotal();
		order.setTotal(total);
		
		order.setState(0);
		
		order.setAddress(null);
		order.setName(user.getName());
		order.setTelephone(user.getTelephone());
		
		order.setUser(user);
		
		//该订单下面有多个订单项，在购物车中，从购物车里面拿
		//List<OrderItem> orderItem = new ArrayList<>();
		Map<String, CartItem> cartItems = cart.getCartItems();
		for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
			//取出每一个购物项
			CartItem cartItem = entry.getValue();
			//放到订单项中
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId(CommonsUtils.getUUID());
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			//将该订单项添加到订单的订单项集合中
			order.getOrderItem().add(orderItem);
		}
		
		//传递到service层
		ProductService service = new ProductService();
		service.submitOrder(order);
		
		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");
	}
	
	
	/**
	 * 确认订单（支付）
	 */
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1.更新收货人信息
		Map<String, String[]> parameterMap = request.getParameterMap();
		Order order = new Order();//修改订单表中的电话等信息
		try {
			BeanUtils.populate(order, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		ProductService service = new ProductService();
		service.updateOrderAdrr(order);
		
		//2.在线支付，修改state
		String orderid = request.getParameter("orderid");
		service.updateOrderState(orderid);
	}
}
