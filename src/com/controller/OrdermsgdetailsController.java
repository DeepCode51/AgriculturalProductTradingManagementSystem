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
import com.dao.*;
import com.entity.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.util.Saveobject;

import java.util.*;

@Controller
public class OrdermsgdetailsController extends BaseController {
	@Resource
	OrdermsgdetailsDAO ordermsgdetailsDAO;
	@Resource
	ProductDAO productDAO;
	@Resource
	Saveobject saveobject;
	
	
	//前台订单列表
	@RequestMapping("ordermsgDetailsLb")
	public String ordermsgDetailsLb(HttpServletRequest request) {
		Member member = (Member) request.getSession().getAttribute(
		"sessionmember");
		String ddno = request.getParameter("ddno");
		HashMap map = new HashMap();
		map.put("ddno",ddno);
		List<Ordermsgdetails> list = ordermsgdetailsDAO.selectAll(map);
		for(Ordermsgdetails ordermsgdetails:list){
			Product product = productDAO.findById(Integer.parseInt(ordermsgdetails.getProductid()));
			ordermsgdetails.setProduct(product);
		}
		request.setAttribute("list",list);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "ordermsgdetailslb";
	}
	
	
	@RequestMapping("/admin/ordermsgXq")
	public String ordermsgXq(HttpServletRequest request) {
		String ddno = request.getParameter("ddno");
		HashMap map = new HashMap();
		map.put("ddno",ddno);
		List<Ordermsgdetails> list = ordermsgdetailsDAO.selectAll(map);
		for(Ordermsgdetails ordermsgdetails:list){
			Product product = productDAO.findById(Integer.parseInt(ordermsgdetails.getProductid()));
			ordermsgdetails.setProduct(product);
		}
		request.setAttribute("list",list);
		return "admin/orderdetails";
	}

}
