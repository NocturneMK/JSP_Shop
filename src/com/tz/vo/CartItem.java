package com.tz.vo;

import java.util.ArrayList;
import java.util.List;

import com.tz.domain.Product;

/**
 * 购物项对象
 * @author Nocturne
 *
 */
public class CartItem {
	//商品
	private Product product;
	//商品数量
	private int buyNum;
	//小计价格
	private double subTotal;
	public CartItem() {}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}
