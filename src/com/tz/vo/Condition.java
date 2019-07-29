package com.tz.vo;

/**
 * 封装搜索条件的几个数据：
 * 名称
 * 热门商品
 * 商品类别
 * @author Nocturne
 *
 */
public class Condition {
	private String pname;
	private String is_hot;
	private String cid;
	
	public Condition() {}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
}
