package com.tz.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.tz.domain.Category;
import com.tz.domain.Product;
import com.tz.utils.C3P0UTils;
import com.tz.vo.Condition;

public class AdminProductDao {
	/**
	 * 真正去操作数据库的数据，查询数据，得到product（商品表）表中所有数据
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());//拿到连接池获得连接
		String sql = "select * from product";
		List<Product> querylist = runner.query(sql, new BeanListHandler<Product>(Product.class));
		return querylist;
	}

	/**
	 * 增加商品
	 * @param product
	 * @throws SQLException
	 */
	public void addProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(), 
				product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(), 
				product.getCategory().getCid());
	}
	/**
	 * 得到category（商品类别表）表中所有数据
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from Category";
		List<Category> querylist = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return querylist;
	}

	/**
	 * 执行删除操作
	 * @param pid
	 * @throws SQLException 
	 */
	public void deleteProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "delete from product where pid=?";
		runner.update(sql,pid);
	}

	/**
	 * 编辑功能，先将要编辑的商品查询出来
	 * @param pid
	 * @throws SQLException 
	 */
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}
	//修改语句
	public void updateProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0UTils.getDataSource());
		String sql = "update product set pid=?,pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		runner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(), 
				product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(), 
				product.getCategory().getCid(), product.getPid());
	}

	/**
	 * 条件查询
	 * @param con 查询的条件
	 * @return 返回查询到的结果
	 * @throws SQLException 
	 */
	public List<Product> queryProductByCondition(Condition con) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0UTils.getDataSource());
		
		List<String> list = new ArrayList<String>();
		String sql = "select * from product where 1=1";
		if(con.getPname() != null && !con.getPname().trim().equals("")) {
			sql += " and pname like ?";
			list.add("%" + con.getPname().trim() + "%");
		}
		if (con.getIs_hot() != null && !con.getIs_hot().trim().equals("")) {
			sql += " and is_hot=?";
			list.add(con.getIs_hot().trim());
		}
		if (con.getCid()!=null && !con.getCid().trim().equals("")) {
			sql += " and cid=?";
			list.add(con.getCid().trim());
		}
		
		List<Product> productList = qr.query(sql, new BeanListHandler<Product>(Product.class), list.toArray());
		return productList;
	}

}


/*
QreryRunner类(org.apache.commons.dbutils.QueryRunner) 是Dbutils的核心类之一，它显著的简化了SQL查询，
并与ResultSetHandler协同工作将使编码量大为减少。它包含以下几个方法：

1.       query(Connection conn, String sql, Object[] params, ResultSetHandler rsh)
			执行选择查询，在查询中，对象阵列的值被用来作为查询的置换参数。

2.       query(String sql, Object[] params, ResultSetHandler rsh)
			方法本身不提供数据库连接，执行选择查询，在查询中，对象阵列的值被用来作为查询的置换参数。

3.       query(Connection conn, String sql, ResultSetHandler rsh)
			执行无需参数的选择查询。

4.       update(Connection conn, String sql, Object[] params)
			被用来执行插入、更新或删除（DML）操作。

其中ResultSetHandler接口(org.apache.commons.dbutils.ResultSethandler)
执行处理一个结果集对象，将数据转变并处理为任何一种形式，供其他应用使用。实现类如下：

ArrayHandler：把结果集中的第一行数据转成对象数组。
ArrayListHandler：把结果集中的每一行数据都转成一个对象数组，再存放到List中。
BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。//重点
MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。//重点
MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
ColumnListHandler：将结果集中某一列的数据存放到List中。
KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里(List<Map>)，再把这些map再存到一个map里，其key为指定的列。
ScalarHandler:将结果集第一行的某一列放到某个对象中。
*/
