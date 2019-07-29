package com.tz.service;

import java.sql.SQLException;
import java.util.List;

import com.tz.dao.AdminProductDao;
import com.tz.domain.Category;
import com.tz.domain.Product;
import com.tz.vo.Condition;;

public class AdminProductService {
	/**
	 * 查询所有的商品
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> findAllProduct() throws SQLException{
		//没有复杂的业务逻辑，直接传递到dao层做数据库的访问
		AdminProductDao adminproductdao = new AdminProductDao();
		return adminproductdao.findAllProduct();
	}

	/**
	 * 添加商品信息
	 * @param product
	 * @throws SQLException 
	 */
	public void addProduct(Product product) throws SQLException {
		//没有复杂的业务逻辑，直接传递到dao层
		AdminProductDao adminproductdao = new AdminProductDao();
		adminproductdao.addProduct(product);
	}
	//获得类别的信息
	public List<Category> findAllCategory() throws SQLException {
		//没有复杂的业务逻辑，直接传递到dao层
		AdminProductDao adminproductdao = new AdminProductDao();
		return adminproductdao.findAllCategory();
	}

	/**
	 * 删除商品信息
	 * @param pid
	 * @throws SQLException 
	 */
	public void deleteProductById(String pid) throws SQLException {
		//没有复杂的业务逻辑，直接传递到dao层
		AdminProductDao dao = new AdminProductDao();
		dao.deleteProductById(pid);
	}

	/**
	 * 要编辑商品的原本内容
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public Product findProductById(String pid) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		return dao.findProductById(pid);
		
	}
	//更新的数据
	public void updateProduct(Product product) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		dao.updateProduct(product);
	}

	/**
	 * 条件查询
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<Product> queryProductByCondition(Condition con) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		return dao.queryProductByCondition(con);
	}
}
