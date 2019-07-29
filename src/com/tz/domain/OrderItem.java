package com.tz.domain;

/**
 * 对应数据库中的表orderItem，表示订单项对象
 * @author Nocturne
 *
 */
public class OrderItem {
	private String itemId;//订单项的ID
	private int count;//商品的数量
	private double subtotal;//小计
	private Product product;//订单项中的商品
	private Order order;//属于哪个订单
	
	public OrderItem() {}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
