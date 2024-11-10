package com.util;

import org.springframework.stereotype.Component;

import com.dao.CartDAO;
import com.dao.CategoryDAO;
import com.dao.InventoryDAO;
import com.dao.MemberDAO;
import com.dao.OrdermsgDAO;
import com.dao.OrdermsgdetailsDAO;
import com.dao.ProductDAO;
import com.dao.WlrecordDAO;
import com.entity.Cart;
import com.entity.Category;
import com.entity.Inventory;
import com.entity.Member;
import com.entity.Ordermsg;
import com.entity.Ordermsgdetails;
import com.entity.Product;
import com.entity.User;
import com.entity.Wlrecord;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class Saveobject {
	@Resource
	CategoryDAO categoryDao;
	@Resource
	ProductDAO productDAO;
	@Resource
	CartDAO cartDao;
	@Resource
	MemberDAO memberDAO;
	@Resource
	WlrecordDAO wlrecordDAO;
	@Resource
	InventoryDAO inventoryDAO;
	@Resource
	OrdermsgDAO ordermsgDAO;
	@Resource
	OrdermsgdetailsDAO ordermsgdetailsDAO;
	

	public void getCategoryObject(HttpServletRequest request) {
		HashMap map = new HashMap();
		List<Category> fcategorylist = categoryDao.selectFcategory(map);// 一级分类
		for (Category fcategory : fcategorylist) {
			List<Category> scategorylist = categoryDao
					.selectScategory(fcategory.getId());
			fcategory.setScategorylist(scategorylist);
		}
		request.setAttribute("ctlist", fcategorylist);
	}

	public void getCart(HttpServletRequest request) {
		Member member = (Member) request.getSession().getAttribute(
		"sessionmember");
		if (member != null) {
			double zk = getzk(member.getId());
			HashMap bbb = new HashMap();
			bbb.put("memberid", member.getId());
			List<Cart> cartlist = cartDao.selectAll(bbb);
			String totalstr = "";
			double total = 0.0;
			for(Cart cart:cartlist){
				Member m = memberDAO.findById(cart.getMemberid());
				Product product = productDAO.findById(cart.getProductid());
				cart.setMember(m);
				cart.setProduct(product);
				double sjprice = 0D;
				double doublesubtotal = 0D;
				if(product.getTprice()>0){
					sjprice = product.getTprice();
					total += Double.parseDouble(String.valueOf(cart.getNum()))* sjprice;
					doublesubtotal = Double.parseDouble(String.valueOf(cart.getNum()))*sjprice;
				}else{
					sjprice = product.getPrice();
					total += Double.parseDouble(String.valueOf(cart.getNum()))* sjprice*zk;
					doublesubtotal = Double.parseDouble(String.valueOf(cart.getNum()))*sjprice*zk;
				}
				cart.setSubtotal(String.format("%.2f", doublesubtotal));
			}
			request.setAttribute("total", total);
			if(total<99){
				total=total+12;
			}
			totalstr = String.format("%.2f", total);
			request.setAttribute("totaldoubel", Double.parseDouble(totalstr));
			request.setAttribute("cartlist", cartlist);
			request.setAttribute("totalstr", totalstr);
		}

	}
	
	public double getzk(int memberid){
		Member bbb = memberDAO.findById(memberid);
		double zk = 0D;
		if(bbb.getCardtype().equals("普卡")){
			zk=1.0;
		}else if(bbb.getCardtype().equals("银卡")){
			zk=0.9;
		}else if(bbb.getCardtype().equals("金卡")){
			zk=0.88;
		}else if(bbb.getCardtype().equals("白金卡")){
			zk=0.85;
		}
		return zk;
	}
	
	public void getWlrecord(String ddno,HttpServletRequest request){
		HashMap map = new HashMap();
		map.put("ddno", ddno);
		List<Wlrecord> wllist = wlrecordDAO.selectAll(map);
		request.setAttribute("wllist", wllist);
	}
	
	public int getInvertory(int productid,HttpServletRequest request){
		User admin = (User)request.getSession().getAttribute("admin");
		int kc = 0;
		HashMap map = new HashMap();
		map.put("productid", productid);
		map.put("type", "in");
		int innum = 0;
		int outnum = 0;
		List<Inventory> inlist = inventoryDAO.selectAll(map);
		for(Inventory inventory:inlist){
			innum+=inventory.getNum();
		}
		map.put("type", "out");
		List<Inventory> outlist = inventoryDAO.selectAll(map);
		for(Inventory inv:outlist){
			outnum+=inv.getNum();
		}
		if(innum>outnum){
			kc=innum-outnum;
		}
		return kc;
	}
	
	
	public int getfdInvertory(int productid,String saver,HttpServletRequest request){
		int kc = 0;
		HashMap map = new HashMap();
		map.put("goodsid", productid);
		map.put("saver",saver);
		map.put("type", "in");
		int innum = 0;
		int outnum = 0;
		List<Inventory> inlist = inventoryDAO.selectAll(map);
		for(Inventory inventory:inlist){
			innum+=inventory.getNum();
		}
		map.put("type", "out");
		List<Inventory> outlist = inventoryDAO.selectAll(map);
		for(Inventory inv:outlist){
			outnum+=inv.getNum();
		}
		if(innum>outnum){
			kc=innum-outnum;
		}
		return kc;
	}
	
	

	
	/**热销商品
	 * @param fid
	 * @param request
	 * @return
	 */
	public ArrayList<Product> hotSales(String fid, HttpServletRequest request){
		HashMap map = new HashMap();
		map.put("leibie", "购买商品");
		if(!fid.equals("")){
			map.put("fid", fid);
		}
		System.out.println(map);
		List<Product> startlist = productDAO.selectProductAll(map);
		System.out.println("aaaaa="+startlist.size());
		ArrayList<Product> zphlist =  new ArrayList<Product>();
		if(startlist.size()>0){
			ArrayList<Product> newslist =  new ArrayList<Product>();
			for(Product product:startlist){
				int salenum = 0;
				HashMap map1 = new HashMap();
				map1.put("productid", product.getId());
				map1.put("status", "已付款");
				List<Ordermsgdetails> ordermsgdetailslist = ordermsgdetailsDAO.selectAll(map1);
				for(Ordermsgdetails ordermsgdetails:ordermsgdetailslist){
					salenum += ordermsgdetails.getNum();
				}
				product.setSalenum(salenum);
				newslist.add(product);
			}
			
			ListSortUtil<Product> sortList = new ListSortUtil<Product>();  
	        sortList.sort(newslist, "salenum", "desc");
	        
	        
	        for(int i=0;i<newslist.size();i++){
	        	Product pro = newslist.get(i);
	        	if(i<5 && pro.getSalenum()>0){
	        		zphlist.add(pro);
	        	}else{
	        		break;
	        	}
	        }
	        //request.setAttribute("zphlist", zphlist);
		}
		return zphlist;
		
	}
	
	
	
}
