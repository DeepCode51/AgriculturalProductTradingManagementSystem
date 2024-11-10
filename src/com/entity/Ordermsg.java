package com.entity;

import java.io.Serializable;
import java.util.*;

public class Ordermsg implements Serializable {
	private int id;
	private String ddno;
	private String memberid;
	private String productid;
	private int num;
	private double total;
	private String fkstatus;
	private String shstatus;
	private String addr;
	private String savetime;
	private String delstatus;
	private String shfs;
	private String zffs;
	private Product product;
	private Address address;
	private String saver;
	private String isdd;
	private String fid;
	private String goodsid;
	private String goodstype;
	private String remark;
	
	private List<Wlrecord> wllist;
	

	private Member member;
	private List<Ordermsgdetails> dddetailist;

	
	public String getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public List<Wlrecord> getWllist() {
		return wllist;
	}

	public void setWllist(List<Wlrecord> wllist) {
		this.wllist = wllist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDdno() {
		return ddno;
	}

	public void setDdno(String ddno) {
		this.ddno = ddno;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getFkstatus() {
		return fkstatus;
	}

	public void setFkstatus(String fkstatus) {
		this.fkstatus = fkstatus;
	}

	public String getShstatus() {
		return shstatus;
	}

	public void setShstatus(String shstatus) {
		this.shstatus = shstatus;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getDelstatus() {
		return delstatus;
	}

	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

	public String getShfs() {
		return shfs;
	}

	public void setShfs(String shfs) {
		this.shfs = shfs;
	}

	public String getZffs() {
		return zffs;
	}

	public void setZffs(String zffs) {
		this.zffs = zffs;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getSaver() {
		return saver;
	}

	public void setSaver(String saver) {
		this.saver = saver;
	}

	public String getIsdd() {
		return isdd;
	}

	public void setIsdd(String isdd) {
		this.isdd = isdd;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public List<Ordermsgdetails> getDddetailist() {
		return dddetailist;
	}

	public void setDddetailist(List<Ordermsgdetails> dddetailist) {
		this.dddetailist = dddetailist;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	

	

}
