package com.tz.vo;

import java.util.HashMap;
import java.util.Map;

//购物车对象，存储到session域中
public class Cart {
	/*
	 * 可以买多个不同的商品，即购物车中存储的多个购物项
	 * 使用Map集合，键值对形式，键为商品的id，值为购物项
	 */
	private Map<String, CartItem> cartItems = new HashMap<String, CartItem>();
	
	//总金额
	private double total;
	
	public Cart() {}

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
