package com.tz.service;


import java.sql.SQLException;

import com.tz.dao.UserDao;
import com.tz.domain.User;

public class UserService {
	/**
	 * 会员注册是否成功的业务逻辑
	 * @param user
	 * @return
	 */
	public boolean register(User user) {
		UserDao dao = new UserDao();
		int row = 0;
		try {
			row = dao.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row>0 ? true : false;
	}

	/**
	 * 校验注册时用户名是否存在的业务逻辑
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkUsername(String username) {
		UserDao dao = new UserDao();
		int checkUsername = 0;
		try {
			checkUsername = dao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkUsername>0 ? true : false;
	}

	
	/**
	 * 用户登陆
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public User login(String username, String password) throws SQLException {
		UserDao dao = new UserDao();
	
		return dao.login(username,password);
	}

}
