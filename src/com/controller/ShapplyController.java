package com.controller;

import javax.annotation.Resource;
import javax.mail.Session;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.util.Info;
import com.util.Saveobject;

import java.util.*;

@Controller
public class ShapplyController extends BaseController {
	@Resource
	NewsDAO newsDAO;
	@Resource
	CategoryDAO categoryDAO;
	@Resource
	MemberDAO memberDAO;
	@Resource
	ProductDAO productDAO;
	@Resource
	Saveobject saveobject;
	@Resource
	OrdermsgDAO ordermsgDAO;
	@Resource
	ShapplyDAO shapplyDAO;
	@Resource
	OrdermsgdetailsDAO ordermsgdetailsDAO;
	@Resource
	UserDAO userDAO;
	
	//后台查询资讯列表
	@RequestMapping("/admin/shapplyList")
	public String shapplyList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("admin");
		HashMap map = new HashMap();
		if(!user.getUsertype().equals("管理员")){
			map.put("shopid", user.getId());
		}
		PageHelper.startPage(pageNum, 10);
		List<Shapply> list = shapplyDAO.selectAll(map);
		for(Shapply sh:list){
			Product product = productDAO.findById(sh.getProductid());
			sh.setProduct(product);
			Member mb = memberDAO.findById(sh.getMemberid());
			sh.setMember(mb);
			User shop = userDAO.findById(Integer.valueOf(product.getSaver()));
			sh.setShop(shop);
		}
		PageInfo<Shapply> pageInfo =  new PageInfo<Shapply>(list);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/shapplylist";
	}
	
	//去申请售后页面 
	@RequestMapping("toShapply")
	public String toShapply(int id,HttpServletRequest request){
		request.setAttribute("orderid",id);
		Ordermsg ordermsg = ordermsgDAO.findById(id);
		HashMap map = new HashMap();
		map.put("ddno", ordermsg.getDdno());
		List<Ordermsgdetails> orderdetaillist = ordermsgdetailsDAO.selectAll(map);
		for(Ordermsgdetails ordermsgdetails: orderdetaillist){
			ordermsgdetails.setProduct(productDAO.findById(Integer.valueOf(ordermsgdetails.getProductid())));
		}
		request.setAttribute("orderdetaillist",orderdetaillist);
		
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "shapplyAdd";
	}
	
	//添加售后申请
	@RequestMapping("shapplyAdd")
	public String shapplyAdd(Shapply shapply,HttpServletRequest request){
		Member member = (Member) request.getSession().getAttribute("sessionmember");
		Ordermsg order = ordermsgDAO.findById(shapply.getOrderid());
		shapply.setShstatus("待受理");
		shapply.setMemberid(member.getId());
		shapply.setShopid(order.getSaver());
		shapplyDAO.add(shapply);
		
		return "redirect:shapply.do";
	}
	
	
	//前台售后申请列表
	@RequestMapping("shapply")
	public String shapply(HttpServletRequest request){
		Member member = (Member) request.getSession().getAttribute("sessionmember");
		HashMap map = new HashMap();
		map.put("memberid", member.getId());
		List<Shapply> shlist = shapplyDAO.selectAll(map);
		for(Shapply sh:shlist){
			Product product = productDAO.findById(sh.getProductid());
			sh.setProduct(product);
			Member mb = memberDAO.findById(sh.getMemberid());
			sh.setMember(mb);
		}
		request.setAttribute("shlist", shlist);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "myshapply";
	}
	
	//后台处理
	@RequestMapping("/admin/shapplyEdit")
	public String shapplyEdit(int id,int flag,HttpServletRequest request){
		Shapply shapply = shapplyDAO.findById(id);
		if(flag==1){
			shapply.setShstatus("已受理");
		}
		if(flag==2){
			shapply.setShstatus("拒绝");
		}
		shapplyDAO.update(shapply);
		return "redirect:shapplyList.do";
	}
	
	
	//后台删除
	@RequestMapping("admin/shapplyDel")
	public String shapplyDel(int id, HttpServletRequest request,HttpServletResponse response){
		shapplyDAO.delete(id);
		return "redirect:shapplyList.do";
	}
	
	//前台删除
	@RequestMapping("qtShapplyDel")
	public String qtShapplyDel(int id, HttpServletRequest request,HttpServletResponse response){
		shapplyDAO.delete(id);
		return "redirect:shapply.do";
	}
	

}
