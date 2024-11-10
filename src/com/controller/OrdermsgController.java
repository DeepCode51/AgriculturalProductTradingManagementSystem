package com.controller;

import javax.annotation.Resource;
import javax.mail.Session;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.*;
import com.entity.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.CheckCode;
import com.util.Info;
import com.util.Saveobject;

import java.util.*;

@Controller
public class OrdermsgController extends BaseController {
	@Resource
	CartDAO cartDAO;
	@Resource
	MemberDAO memberDAO;
	@Resource
	ProductDAO productDAO;
	@Resource
	Saveobject saveobject;
	@Resource
	OrdermsgdetailsDAO ordermsgdetailsDAO;
	@Resource
	OrdermsgDAO ordermsgDAO;
	@Resource
	InventoryDAO inventoryDAO;
	@Resource
	AddressDAO addressDAO;
	@Resource
	TicketDAO ticketDAO;
	@Resource
	UserDAO userDAO;
	
	
	
	
	
	

	//创建订单
	@RequestMapping("createOrder")
	public String createOrder(HttpServletRequest request) {
		Member member = (Member)request.getSession().getAttribute("sessionmember");
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		int addrid = 0;
		List<Address> addresslist = addressDAO.selectAll(map);
		for(Address address:addresslist){
			if(address.getIsmr().equals("yes")){
				addrid=address.getId();
			}
		}
		List<Ticket> ticketlist = ticketDAO.selectAll(map);
		request.setAttribute("addresslist", addresslist);
		request.setAttribute("ticketlist", ticketlist);
		request.setAttribute("addrid", addrid);
		return "createorder";
	}
	
	//付款页面
	@RequestMapping("skipFukuan")
	public String skipFukuan(HttpServletRequest request) {
		Member member = (Member)request.getSession().getAttribute("sessionmember");
		String id = request.getParameter("id");
		Ordermsg ordermsg = ordermsgDAO.findById(Integer.parseInt(id));
		HashMap map1 = new HashMap();
		map1.put("ddno", ordermsg.getDdno());
		List<Ordermsgdetails> dddetailist = ordermsgdetailsDAO.selectAll(map1);
		for(Ordermsgdetails orderdetail:dddetailist){
			orderdetail.setProduct(productDAO.findById(Integer.valueOf(orderdetail.getProductid())));
		}
		ordermsg.setDddetailist(dddetailist);
		
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		String addrid="";
		List<Address> addresslist = addressDAO.selectAll(map);
		List<Ticket> ticketlist = ticketDAO.selectAll(map);
		for(Address address:addresslist){
			if(address.getIsmr().equals("yes")){
				addrid=String.valueOf(address.getId());
			}
		}
		request.setAttribute("addresslist", addresslist);
		request.setAttribute("ticketlist", ticketlist);
		request.setAttribute("ordermsg", ordermsg);
		request.setAttribute("addrid", addrid);
		double sjtotal = ordermsg.getTotal();

		request.setAttribute("sjtotal", sjtotal);
		return "fukuan";
	}
	
	//创建订单
	@RequestMapping("createDd")
	public String createDd(HttpServletRequest request) {
		
		String totalstr = request.getParameter("totalstr");
		String addrid = request.getParameter("addrid");
		Member member = (Member) request.getSession().getAttribute(
				"sessionmember");
		double zk = saveobject.getzk(member.getId());
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		String memberid = String.valueOf(member.getId());
		String fkstatus = "待付款";
		String addrstr = addrid;
		String savetime = Info.getDateStr();
		
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		List<Cart> list = cartDAO.selectAll(map);
		if(list.size()==0){
			return "redirect:cartList.do";
		}else{
			List<Integer> shopidlist = new ArrayList<Integer>();
			for(int i=0;i<list.size();i++){
				Cart c = list.get(i);
				shopidlist.add(c.getShopid());
			}
			HashSet h = new HashSet(shopidlist);   
			shopidlist.clear();   
			shopidlist.addAll(h);
			for(Integer distinctshopid : shopidlist){//购物车商家ID去重后的循环
				CheckCode cc = new CheckCode();
				String ddno = cc.getCheckCode();
				Ordermsg ordermsg = new Ordermsg();
				ordermsg.setDdno(ddno);
				ordermsg.setMemberid(memberid);
				ordermsg.setSaver(distinctshopid.toString());
				
				ordermsg.setGoodstype("购买商品");
				ordermsg.setAddr(addrstr);
				
				ordermsg.setFkstatus(fkstatus);
				ordermsg.setDelstatus("0");
				ordermsg.setSavetime(savetime);
				Double ddfee = 0.0;
				map.put("memberid", member.getId());
				map.put("shopid", distinctshopid);
				List<Cart> finallist = cartDAO.selectAll(map);
				for(Cart cart:finallist){
					Product product = productDAO.findById(cart.getProductid());
					double sjprice = 0D;
					double doublesubtotal = 0D;
					if(product.getTprice()>0){
						sjprice = product.getTprice();
						ddfee += Double.parseDouble(String.valueOf(cart.getNum()))* sjprice;
						doublesubtotal = Double.parseDouble(String.valueOf(cart.getNum()))*sjprice;
					}else{
						sjprice = product.getPrice();
						ddfee += Double.parseDouble(String.valueOf(cart.getNum()))* sjprice;
						doublesubtotal = Double.parseDouble(String.valueOf(cart.getNum()))*sjprice;
					}
					cart.setSubtotal(String.format("%.2f", doublesubtotal));
					
					Ordermsgdetails ordermsgdetails = new Ordermsgdetails();
					ordermsgdetails.setDdno(ddno);
					ordermsgdetails.setNum(cart.getNum());
					ordermsgdetails.setMemberid(String.valueOf(member.getId()));
					ordermsgdetails.setProductid(String.valueOf(cart.getProductid()));
					ordermsgdetails.setStatus(fkstatus);
					ordermsgdetailsDAO.add(ordermsgdetails);
					cartDAO.delCart(cart.getId());
				}
				
				ordermsg.setTotal(ddfee);
				ordermsgDAO.add(ordermsg);
			}
			return "redirect:orderLb.do";
		}
	}
	
	//前台订单列表
	@RequestMapping("orderLb")
	public String orderLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		PageHelper.startPage(pageNum, 5);
		Member member = (Member) request.getSession().getAttribute(
		"sessionmember");
		String msg = request.getParameter("msg")==null?"":request.getParameter("msg");
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		map.put("delstatus", "0");
		map.put("goodstype", "购买商品");
		List<Ordermsg> list = ordermsgDAO.selectAll(map);
		for(Ordermsg ordermsg:list){
			Member mem = memberDAO.findById(Integer.parseInt(ordermsg.getMemberid()));
			ordermsg.setMember(mem);
			HashMap map1 = new HashMap();
			map1.put("ddno", ordermsg.getDdno());
			List<Ordermsgdetails> dddetailist = ordermsgdetailsDAO.selectAll(map1);
			for(Ordermsgdetails orderdetail:dddetailist){
				orderdetail.setProduct(productDAO.findById(Integer.valueOf(orderdetail.getProductid())));
			}
			ordermsg.setDddetailist(dddetailist);
			if(ordermsg.getAddr()!=null&&!ordermsg.getAddr().equals("")){
				Address address = addressDAO.findById(Integer.parseInt(ordermsg.getAddr()));
				ordermsg.setAddress(address);
			}
			
			//查看物流信息
			saveobject.getWlrecord(ordermsg.getDdno(), request);
			List<Wlrecord> wllist = (List<Wlrecord>)request.getAttribute("wllist");
			ordermsg.setWllist(wllist);
		}
		PageInfo<Ordermsg> pageInfo =  new PageInfo<Ordermsg>(list);
		request.setAttribute("pageInfo",pageInfo);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		if(!msg.equals("")){
			request.setAttribute("msg", "恭喜你抽中了&nbsp;"+msg+"优惠券");
		}
		return "orderlb";
	}
	
	@RequestMapping("fubiOrder")
	public String fubiOrder(HttpServletRequest request) {
		Member member = (Member) request.getSession().getAttribute(
		"sessionmember");
		String msg = request.getParameter("msg")==null?"":request.getParameter("msg");
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		map.put("delstatus", "0");
		map.put("goodstype", "积分商品");
		List<Ordermsg> list = ordermsgDAO.selectAll(map);
		for(Ordermsg ordermsg:list){
			Member mem = memberDAO.findById(Integer.parseInt(ordermsg.getMemberid()));
			ordermsg.setMember(mem);
			Product product = productDAO.findById(Integer.parseInt(ordermsg.getProductid()));
			ordermsg.setProduct(product);
		}
		request.setAttribute("list",list);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "fubiorder";
	}
	
	
	
	//后台查询订单列表
	@RequestMapping("/admin/orderList")
	public String orderList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		User admin = (User)request.getSession().getAttribute("admin");
		String key = request.getParameter("key");
		String suc = request.getParameter("suc")==null?"":request.getParameter("suc");
		HashMap map = new HashMap();
		map.put("ddno", key);
		map.put("delstatus", "0");
		if(!admin.getUsertype().equals("管理员")){
			map.put("saver", String.valueOf(admin.getId()));
		}
		map.put("fkstatus", "待付款");
		map.put("goodstype", "购买商品");
		List<Ordermsg> list = ordermsgDAO.selectAll(map);
		for(Ordermsg ordermsg:list){
			Member member = memberDAO.findById(Integer.parseInt(ordermsg.getMemberid()));
			HashMap map1 = new HashMap();
			map1.put("ddno", ordermsg.getDdno());
			List<Ordermsgdetails> dddetailist = ordermsgdetailsDAO.selectAll(map1);
			for(Ordermsgdetails orderdetail:dddetailist){
				orderdetail.setProduct(productDAO.findById(Integer.valueOf(orderdetail.getProductid())));
			}
			ordermsg.setDddetailist(dddetailist);
			ordermsg.setMember(member);
			if(ordermsg.getAddr()!=null){
			Address address = addressDAO.findById(Integer.parseInt(ordermsg.getAddr()));
			
			ordermsg.setAddress(address);
			}
			//查看物流信息
			saveobject.getWlrecord(ordermsg.getDdno(), request);
			List<Wlrecord> wllist = (List<Wlrecord>)request.getAttribute("wllist");
			ordermsg.setWllist(wllist);
			
		}
		PageInfo<Ordermsg> pageInfo =  new PageInfo<Ordermsg>(list);
		request.setAttribute("key",key);
		request.setAttribute("pageInfo",pageInfo);
		if(suc.equals("suc")){
			request.setAttribute("suc","操作成功");
		}else if(suc.equals("error")){
			request.setAttribute("suc","库存不足");
		}
		
		return "admin/orderlist";
	}
	
	
	@RequestMapping("/admin/fubiorderList")
	public String fubiorderList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		User admin = (User)request.getSession().getAttribute("admin");
		String key = request.getParameter("key");
		String suc = request.getParameter("suc")==null?"":request.getParameter("suc");
		HashMap map = new HashMap();
		map.put("ddno", key);
		map.put("delstatus", "0");
		map.put("saver", admin.getId());
		map.put("fkstatus", "待付款");
		map.put("goodstype", "积分商品");
		List<Ordermsg> list = ordermsgDAO.selectAll(map);
		for(Ordermsg ordermsg:list){
			Member member = memberDAO.findById(Integer.parseInt(ordermsg.getMemberid()));
			Product product = productDAO.findById(Integer.parseInt(ordermsg.getProductid()));
			ordermsg.setMember(member);
			ordermsg.setProduct(product);
			
			
		}
		PageInfo<Ordermsg> pageInfo =  new PageInfo<Ordermsg>(list);
		request.setAttribute("key",key);
		request.setAttribute("pageInfo",pageInfo);
		if(suc.equals("suc")){
			request.setAttribute("suc","操作成功");
		}else if(suc.equals("error")){
			request.setAttribute("suc","库存不足");
		}
		
		return "admin/fubiorder";
	}
	
	//发货
	@RequestMapping("/admin/orderFh")
	public String orderFh(int id,HttpServletRequest request) {
		boolean flag = true;
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		
		HashMap map1 = new HashMap();
		map1.put("ddno", ordermsg.getDdno());
		List<Ordermsgdetails> dddetailist = ordermsgdetailsDAO.selectAll(map1);
		for(Ordermsgdetails orderdetail:dddetailist){
			int kc = saveobject.getInvertory(Integer.valueOf(orderdetail.getProductid()), request);
			if(kc<orderdetail.getNum()){
				flag = false;
			}
		}
		if(flag == true){
			for(Ordermsgdetails orderdetail:dddetailist){
				if(ordermsg.getShfs().equals("到店自取")){
			    	ordermsg.setIsdd("抵达目的地");
			    }
				ordermsg.setFkstatus("已发货");
				ordermsgDAO.update(ordermsg);
				Inventory inv = new Inventory();
				inv.setNum(orderdetail.getNum());
				inv.setProductid(orderdetail.getProductid());
				inv.setType("out");
				inventoryDAO.add(inv);
			}
			return "redirect:orderList.do?suc=suc";
		}else{
			return "redirect:orderList.do?suc=error";
		}
	}
	
	
	//发货
	@RequestMapping("/admin/fubiorderFh")
	public String fubiorderFh(int id,HttpServletRequest request) {
		Ordermsg ordermsg = ordermsgDAO.findById(id);
	    if(ordermsg.getShfs().equals("到店自取")){
	    	ordermsg.setIsdd("抵达目的地");
	    }
		ordermsg.setFkstatus("已发货");
		ordermsgDAO.update(ordermsg);
				
		return "redirect:fubiorderList.do?suc=suc";
	}
	
	//收货
	@RequestMapping("qianShou")
	public String qianShou(int id,HttpServletRequest request){
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		ordermsg.setFkstatus("交易完成");
		ordermsgDAO.update(ordermsg);
		return "redirect:orderLb.do";
	}
	
	@RequestMapping("fubiqianshou")
	public String qs(int id,HttpServletRequest request){
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		ordermsg.setFkstatus("交易完成");
		ordermsgDAO.update(ordermsg);
		return "redirect:fubiOrder.do";
	}
	
	@RequestMapping("fubiorderSc")
	public String fubiorderSc(int id,HttpServletRequest request){
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		ordermsg.setDelstatus("1");
		ordermsgDAO.update(ordermsg);
		return "redirect:fubiOrder.do";
	}
	
	//删除订单
		@RequestMapping("orderSc")
		public String orderSc(int id,HttpServletRequest request){
			Ordermsg ordermsg = ordermsgDAO.findById(id);
			ordermsg.setDelstatus("1");
			ordermsgDAO.update(ordermsg);
			return "redirect:orderLb.do";
		}
		
		//删除订单
		@RequestMapping("admin/fubiorderDel")
		public String fubiorderDel(int id,HttpServletRequest request){
			Ordermsg ordermsg = ordermsgDAO.findById(id);
			ordermsg.setDelstatus("1");
			ordermsgDAO.update(ordermsg);
			return "redirect:orderList.do";
		}
	
	
	//使用优惠券
		@RequestMapping("updateYhtotal")
		public void updateYhtotal(HttpServletRequest request, HttpServletResponse response) {
			PrintWriter out;
			try {
				out = response.getWriter();
				String id = request.getParameter("id");
				String ordermsgid = request.getParameter("ordermsgid");
				Ticket ticket = ticketDAO.findById(Integer.parseInt(id));
				Ordermsg ordermsg = ordermsgDAO.findById(Integer.parseInt(ordermsgid));
				int money = ticket.getMoney();
				saveobject.getCart(request);
				double total = (Double)ordermsg.getTotal();
//				if(total<99){
//					total=total+12;
//				}
				if(total>money){
					double yhtotal = total-money;
					String yhtotalstr = String.format("%.2f", yhtotal);
					out.println(yhtotalstr);
				}else{
					out.println("-1");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
     //支付方式页面
	@RequestMapping("skipZffs")
	public String skipZffs(HttpServletRequest request){
		Member member = (Member)request.getSession().getAttribute("sessionmember");
		String suc = request.getParameter("suc")==null?"":request.getParameter("suc");
		String id = request.getParameter("id");
		String totalstr = request.getParameter("totalstr");
		String ticketid = request.getParameter("ticketid");
		String addrid = request.getParameter("addrid");
		request.setAttribute("id", id);
		request.setAttribute("sjtotal", totalstr);
		request.setAttribute("ticketid", ticketid);
		request.setAttribute("addrid", addrid);
		saveobject.getCategoryObject(request);
		
		
		return "zffs";
	}
	
	//订单付款
	@RequestMapping("fukuan")
	public String fukuan(HttpServletRequest request){
		Member member = (Member)request.getSession().getAttribute("sessionmember");
		Member mmm = memberDAO.findById(member.getId());
		String id = request.getParameter("id");
		String sjtotal = request.getParameter("sjtotal");
		String ticketid = request.getParameter("ticketid");
		String addrid = request.getParameter("addrid");
		String zffs = request.getParameter("zffs");
		String shfs = request.getParameter("shfs");
		//double yue = mmm.getMoney();
		//查优惠券
		
		
		double doublesjtotal = Double.parseDouble(sjtotal);
		if(ticketid != null && !ticketid.equals("")){
			Ticket ticket = ticketDAO.findById(Integer.valueOf(ticketid));
			doublesjtotal = doublesjtotal - Double.valueOf(ticket.getMoney());
		}
		//boolean flag = true;
		//double sxyue = yue-doublesjtotal;

		//if(sxyue>=0){
		
			Ordermsg ordermsg = ordermsgDAO.findById(Integer.parseInt(id));
			

			Address address = addressDAO.findById(Integer.parseInt(addrid));
			ordermsg.setAddress(address);
			HashMap map1 = new HashMap();
			map1.put("ddno", ordermsg.getDdno());
			List<Ordermsgdetails> dddetailist = ordermsgdetailsDAO.selectAll(map1);
			for(Ordermsgdetails orderdetail:dddetailist){
				orderdetail.setProduct(productDAO.findById(Integer.valueOf(orderdetail.getProductid())));
				orderdetail.setStatus("已付款");
				ordermsgdetailsDAO.update(orderdetail);
			}
			ordermsg.setDddetailist(dddetailist);
			ordermsg.setAddr(addrid);
			ordermsg.setShfs(shfs);
			ordermsg.setZffs(zffs);
			ordermsg.setFkstatus("已付款");
			ordermsg.setTotal(doublesjtotal);
			
			String fktime = Info.getDateStr();
//			try {
//				if(Info.compare(fktime,fktime.substring(0, 10)+" 18:00:00")){
//					ordermsg.setRemark("当日达");
//				}else{
//					ordermsg.setRemark("次日达");
//				}
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ordermsgDAO.update(ordermsg);
			request.setAttribute("ordermsg", ordermsg);
			if(ticketid!=null&&!ticketid.equals("")){
			ticketDAO.delete(Integer.parseInt(ticketid));
			}
			
			//mmm.setMoney(sxyue);
			//memberDAO.updateYue(mmm);
			
			double xftotal = mmm.getXftotal()+doublesjtotal;
			mmm.setXftotal(xftotal);
			memberDAO.updateXftotal(mmm);
			
			int jf = mmm.getJf()+new Double(doublesjtotal).intValue();
			mmm.setJf(jf);
			memberDAO.updateJf(mmm);
			
//			if(!mmm.getCardtype().equals("白金卡")){
//			if(xftotal>300&&xftotal<800){
//				mmm.setCardtype("银卡");
//			}else if(xftotal>800&&xftotal<1500){
//				mmm.setCardtype("金卡");
//			}else if(xftotal>1500){
//				mmm.setCardtype("白金卡");
//			}
//			}
			memberDAO.update(mmm);
			saveobject.getCategoryObject(request);
			return "success";
		//}else{
			
		//	request.setAttribute("id", id);
		//	request.setAttribute("sjtotal", sjtotal);
		//	request.setAttribute("ticketid", ticketid);
		//	request.setAttribute("addrid", addrid);
		//	return "redirect:skipZffs.do?suc=error&totalstr="+sjtotal+"&ticketid="+ticketid+"&addrid="+addrid+"&id="+id;
		//}
		
		
		
		
	}
	
	//取消订单
	@RequestMapping("qxOrdermsg")
	public String qxOrdermsg(int id,HttpServletRequest request){
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		ordermsg.setFkstatus("已取消");
		ordermsgDAO.update(ordermsg);
		return "redirect:orderLb.do";
	}
	
	//
	@RequestMapping("addOrder")
	public void addOrder(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			Member member = (Member) request.getSession().getAttribute(
					"sessionmember");
			if (member != null) {
				Member mmm = memberDAO.findById(member.getId());
				String productid = request.getParameter("productid");
				String num = request.getParameter("num");
				Product product = productDAO.findById(Integer.parseInt(productid));
				double total = Double.parseDouble(num)*product.getPrice();
				int intotal = new Double(total).intValue();
				if(intotal<=mmm.getJf()){
				int yjf = mmm.getJf()-intotal;
				CheckCode cc = new CheckCode();
				String ddno = cc.getCheckCode();
				Ordermsg ordermsg = new Ordermsg();
				ordermsg.setDdno(ddno);
				ordermsg.setMemberid(String.valueOf(member.getId()));
				ordermsg.setProductid(productid);
				ordermsg.setNum(Integer.parseInt(num));
				ordermsg.setTotal(intotal);
				ordermsg.setFkstatus("已付款");
				ordermsg.setSavetime(Info.getDateStr());
				ordermsg.setDelstatus("0");
				ordermsg.setShfs("到店自取");
				ordermsg.setSaver(product.getSaver());
				ordermsg.setGoodstype("积分商品");
				ordermsgDAO.add(ordermsg);
				
				mmm.setJf(yjf);
				memberDAO.updateJf(mmm);
				out.print("1");
				}else{
				out.print("2");//积分不足
				}
			} else {
				out.println("0");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
}
