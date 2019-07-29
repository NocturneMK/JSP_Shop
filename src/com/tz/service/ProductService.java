package com.tz.service;

import java.sql.SQLException;
import java.util.List;

import com.tz.dao.ProductDao;
import com.tz.domain.Category;
import com.tz.domain.Order;
import com.tz.domain.Product;
import com.tz.utils.C3P0UTils;
import com.tz.vo.PageBean;

public class ProductService {

	public List<Product> findAllProduct() throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.findAllProduct();
	}
	
	

	/**
	 * 将每一页的商品封装到PageBean对象中
	 * @param currentPage 当前页面
	 * @param currentCount 当前页面显示条数
	 * @return 返回每一页的商品数据
	 * @throws SQLException
	 */
	public PageBean<Product> findPageBean(int currentPage, int currentCount) throws SQLException {
		ProductDao dao = new ProductDao();
		
		//进行业务逻辑封装并返回
		PageBean<Product> pageBean = new PageBean<Product>();
		
		//设置当前页面
		pageBean.setCurrentPage(currentPage);
		//设置每页显示条数
		pageBean.setCurrentCount(currentCount);
		//设置商品总数
		int totalCount = dao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//设置productList属性
		int index = (currentPage-1)*currentCount;//当前页的起始数据 = （当前页 - 1） * 每页显示条数
		List<Product> productList = dao.findProductForPage(index, currentCount);
		pageBean.setProductList(productList);
		
		return pageBean;
	}


	/**
	 * 热门商品
	 * @return
	 */
	public List<Product> findHotProductList() {
		List<Product> hotProduct = null;
		ProductDao dao = new ProductDao();
		try {
			hotProduct = dao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return hotProduct;
	}


	/**
	 * 最新商品
	 * @return
	 */
	public List<Product> findNewProductList() {
		ProductDao dao = new ProductDao();
		List<Product> newProduct = null;
		try {
			newProduct = dao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProduct;
	}


	/**
	 * 查询商品类别
	 * @return
	 */
	public List<Category> findAllCategory() {
		ProductDao dao = new ProductDao();
		List<Category> category = null;
		try {
			category = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}


	/**
	 * 根据类别进行分页
	 * @return
	 */
	public PageBean<Product> findPageBeanByCid(String cid, int currentPage, int currentCount) {
		ProductDao dao = new ProductDao();
		PageBean<Product> pageBean = new PageBean<Product>();//业务逻辑封装，并返回
		
		pageBean.setCurrentPage(currentPage);
		
		pageBean.setCurrentCount(currentCount);
		
		int totalCountByCid = 0;
		try {
			totalCountByCid = dao.getTotalCountByCid(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCountByCid);
		
		int totalPage = (int) Math.ceil(1.0*totalCountByCid/currentCount);
		pageBean.setTotalPage(totalPage);
		
		List<Product> list = null;
		int index = (currentPage-1)*currentCount;
		try {
			list = dao.findProductForPageByCid(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setProductList(list);
		
		return pageBean;
	}


	/**
	 * 查询单个商品详细信息
	 * @param pid
	 * @return
	 */
	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product product = new Product();
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}



	/**
	 * 提交订单
	 * @param order
	 */
	public void submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		
		//要用事务的处理
		try {
			C3P0UTils.startTransaction();//开启事务
			
			//此方法：调用这个dao，存储order这个表的数据
			dao.addOrders(order);
			
			//此方法：调用这个dao，存储orderitem这个表的数据
			dao.addOrderItem(order);
			
			
			
		} catch (SQLException e) {
			try {
				C3P0UTils.rollback();//出现异常回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				C3P0UTils.commitAndRelease();//提交并且关闭资源
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void updateOrderAdrr(Order order) {
		ProductDao dao = new ProductDao();
		try {
			dao.updateOrderAdrr(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public void updateOrderState(String orderid) {
		ProductDao dao = new ProductDao();
		try {
			dao.updateOrderState(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
