package com.tz.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.tz.domain.Category;
import com.tz.domain.Order;
import com.tz.domain.OrderItem;
import com.tz.domain.Product;
import com.tz.utils.C3P0UTils;

public class ProductDao {
	
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product";
		return qr.query(sql, new BeanListHandler<Product>(Product.class) );
	}
	
	
	/**
	 * 查询当前页面的商品数据
	 * @param index  起始参数
	 * @param currentCount  当前页面显示条数
	 * @return 返回当前页面的商品的集合
	 * @throws SQLException
	 */
	public List<Product> findProductForPage(int index, int currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		/*
		limit是mysql的语法
		select * from table limit m,n      其中m是指记录开始的index，从0开始，表示第一条记录；n是指从第m+1条开始，取n条。
		select * from tablename limit 2,4     即取出第3条至第6条，4条记录
		 */
		String sql = "select * from product limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), index, currentCount);
	}
	
	
	/**
	 * 返回商品总数
	 * @return
	 * @throws SQLException 
	 */
	public int getTotalCount() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select count(*) from product";
		Long query = (Long) qr.query(sql, new ScalarHandler());//ScalarHandler:获取结果集中第一行数据指定列的值,常用来进行单值查询
		return query.intValue();
	}


	/**
	 * 热门商品
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> findHotProductList() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";//只要前9条数据
		
		return qr.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
	}


	/**
	 * 最新商品
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> findNewProductList() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";//时间降序排序，只要最新的前9条数据
		
		return qr.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
	}


	/**
	 * 查询所有商品类别
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}


	/**
	 * 某类商品的总数
	 * @return
	 * @throws SQLException 
	 */
	public int getTotalCountByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) qr.query(sql, new ScalarHandler(), cid);
		return count.intValue();
	}


	/**
	 * 查询某类商品在当前页面的商品数据
	 * @param cid 商品类别
	 * @param index
	 * @param currentCount
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> findProductForPageByCid(String cid, int index, int currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
	}


	/**
	 * 根据Pid查询单个商品信息
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product where pid=?";
		
 		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}
	

	/**
	 * 存储order这个表的数据
	 * @param order
	 * @throws SQLException 
	 */
	public void addOrders(Order order) throws SQLException {
		//开启的事务，控制事务要同一个连接，所以有事务要用无参的
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = C3P0UTils.getConnection();
		runner.update(conn, sql, order.getoId(), order.getOrderTime(), order.getTotal(),
				order.getState(), order.getAddress(), order.getName(), order.getTelephone(),
				order.getUser().getUid());
	}


	/**
	 * 存储orderitem这个表的数据
	 * @param order
	 * @throws SQLException 
	 */
	public void addOrderItem(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection connection = C3P0UTils.getConnection();
		List<OrderItem> orderItems = order.getOrderItem();
		for (OrderItem orderItem : orderItems) {
			qr.update(connection, sql, orderItem.getItemId(), orderItem.getCount(), orderItem.getSubtotal(),
					orderItem.getProduct().getPid(), orderItem.getOrder().getoId());
		}
	}


	public void updateOrderAdrr(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "update orders set address=?, name=?, telephone=? where oid=?";
		qr.update(sql, order.getAddress(), order.getName(), order.getTelephone(), order.getoId());
	}


	public void updateOrderState(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "update orders set state=? where oid=?";
		qr.update(sql, 1, orderid);
	
	}

}
