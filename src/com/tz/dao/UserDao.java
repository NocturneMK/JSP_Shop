package com.tz.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.tz.domain.User;
import com.tz.utils.C3P0UTils;

public class UserDao {

	/**
	 * 注册：在User（用户）表中添加数据
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), 
				user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(),
				user.getState(), user.getCode());
		return row;
	}

	/**
	 * 检验注册时用户名是否存在
	 * @param username
	 * @throws SQLException 
	 */
	public int checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select count(*) from user where username=?";
		Long is = (Long) qr.query(sql, new ScalarHandler(), username);
		return is.intValue();
	}

	
	/**
	 * 用户登录查询
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class), username, password );
	}
	
}
