package com.tz.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tz.domain.User;
import com.tz.service.UserService;

/**
 * 用户层面的代码抽取
 * @author Nocturne
 *
 */
public class UserServlet extends BaseServlet {

	/**
	 * 用户登陆
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		HttpSession session = request.getSession();
		//获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
		UserService service = new UserService();
		try {
			user = service.login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user != null) {
			//登陆成功
			if("autoLogin".equals(request.getParameter("autoLogin"))) {//如果选中的自动登陆
				//自动登陆
				Cookie cookie_username = new Cookie("cookie_username", user.getUsername());
				cookie_username.setMaxAge(30*60);
				Cookie cookie_password = new Cookie("cookie_password", user.getPassword());
				cookie_password.setMaxAge(30*60);
			}
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/index");
		}else {
			request.setAttribute("error", "用户名或密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * 用户退出
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		Cookie cookie_username = new Cookie("cookie_username", "");
		cookie_username.setMaxAge(0);
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_password.setMaxAge(0);
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		response.sendRedirect(request.getContextPath() + "/index");
	}
	
}
