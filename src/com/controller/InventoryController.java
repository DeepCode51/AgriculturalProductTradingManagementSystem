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
public class InventoryController extends BaseController {
	@Resource
	CategoryDAO categoryDAO;
	@Resource
	InventoryDAO inventoryDAO;
	@Resource
	ProductDAO productDAO;
	@Resource
	Saveobject saveobject;
	@Resource
	UserDAO userDAO;
	
	
	
	
	//库存
	@RequestMapping("/admin/inventoryList")
	public String fdinventoryList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		User admin = (User)request.getSession().getAttribute("admin");
		String key = request.getParameter("key");
		String fid = request.getParameter("fid")==null?"":request.getParameter("fid");
		String sid = request.getParameter("sid")==null?"":request.getParameter("sid");
		HashMap map = new HashMap();
		map.put("key", key);
		map.put("fid", fid);
		map.put("sid", sid);
		if(!admin.getUsertype().equals("管理员")){
			map.put("saver", admin.getId());
		}
		PageHelper.startPage(pageNum, 10);
		List<Product> list = productDAO.selectAll(map);
		for(Product product:list){
			Category fcategory = categoryDAO.findById(Integer.parseInt(product.getFid()));
			Category scategory = categoryDAO.findById(Integer.parseInt(product.getSid()));
			product.setFcategory(fcategory);
			product.setScategory(scategory);
			int totalnum = saveobject.getInvertory(product.getId(), request);
			product.setKc(totalnum);
		}
		if(!fid.equals("")){
		   	List<Category> scategorylist = categoryDAO.selectScategory(Integer.parseInt(fid));
		   	request.setAttribute("scategorylist", scategorylist);
		}
		PageInfo<Product> pageInfo =  new PageInfo<Product>(list);
		request.setAttribute("key", key);
		request.setAttribute("fid", fid);
		request.setAttribute("sid", sid);
		request.setAttribute("pageInfo", pageInfo);
		saveobject.getCategoryObject(request);
		return "admin/inventorylist";
	}
	
	
	//入库
	@RequestMapping("/admin/inventoryAdd")
	public String inventoryAdd(Inventory inventory, HttpServletRequest request) {
		inventoryDAO.add(inventory);
		return "redirect:inventoryList.do";
	}
	
	
	
	
	
	
	
	

}
