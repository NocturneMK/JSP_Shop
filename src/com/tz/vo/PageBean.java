package com.tz.vo;

import java.util.ArrayList;
import java.util.List;

import com.tz.domain.Product;

/**
 * 分页查询的思想：
 * 将每一页显示的商品封装成一个对象
 * @author Nocturne
 *
 */
public class PageBean<T> {
	//当前页面
	private int currentPage;
	//当前页面显示条数
	private int currentCount;
	//总条数（商品总数）
	private int totalCount;
	//总页数
	private int totalPage;
	//每页显示的商品数据
	private List<T> productList = new ArrayList<>();
	
	public PageBean() {}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getProductList() {
		return productList;
	}
	public void setProductList(List<T> productList) {
		this.productList = productList;
	}
}
