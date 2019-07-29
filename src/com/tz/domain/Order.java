package com.tz.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对应数据库中的orders表，表示订单对象
 * @author Nocturne
 *
 */
public class Order {
	private String oId;//订单id
	private Date orderTime;//订单的时间
	private double total;//总金额
	private int state;//订单状态
	
	private String address;//收货地址
	private String name;//收货人名字
	private String telephone;//收货人电话
	
	private User user;//订单属于哪个用户
	
	List<OrderItem> orderItem = new ArrayList<>();//订单项，存到数据库，直接用List集合
	
	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public Order() {}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
