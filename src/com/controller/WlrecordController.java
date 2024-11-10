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
import com.util.Saveobject;

import java.util.*;

@Controller
public class WlrecordController extends BaseController {
	@Resource
	WlrecordDAO wlrecordDAO;
	@Resource
	Saveobject saveobject;
	@Resource
	OrdermsgDAO ordermsgDAO;
	
	
	
	
	
	//后台查询物流列表
	@RequestMapping("/admin/wlrecordList")
	public String wlrecordList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		String ddno = request.getParameter("ddno");
		HashMap map = new HashMap();
		map.put("ddno", ddno);
		PageHelper.startPage(pageNum, 10);
		List<Wlrecord> list = wlrecordDAO.selectAll(map);
		PageInfo<Wlrecord> pageInfo =  new PageInfo<Wlrecord>(list);
		request.setAttribute("ddno",ddno);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/wlrecordlist";
	}
	
	
	
	//添加物流 
	@RequestMapping("/admin/wlrecordAdd")
	public String wlrecordAdd(Wlrecord wlrecord,HttpServletRequest request){
		String ordermsgid = request.getParameter("ordermsgid");
		Ordermsg ordermsg = ordermsgDAO.findById(Integer.parseInt(ordermsgid));
		HashMap map = new HashMap();
		map.put("ddno", ordermsg.getDdno());
		List<Wlrecord> list = wlrecordDAO.selectAll(map);
		if(list.size()==0){
			ordermsg.setIsdd("派件中");
			ordermsgDAO.update(ordermsg);
		}
		wlrecord.setDdno(ordermsg.getDdno());
		wlrecordDAO.add(wlrecord);
		if(wlrecord.getStatus().equals("抵达目的地")){
			ordermsg.setIsdd("抵达目的地");
			ordermsgDAO.update(ordermsg);
		}
		return "redirect:orderList.do?suc=suc";
	}
	
	
	//前台台查询物流列表
	@RequestMapping("wlLb")
	public String wlLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		String ddno = request.getParameter("ddno");
		HashMap map = new HashMap();
		map.put("ddno", ddno);
		PageHelper.startPage(pageNum, 10);
		List<Wlrecord> list = wlrecordDAO.selectAll(map);
		PageInfo<Wlrecord> pageInfo =  new PageInfo<Wlrecord>(list);
		request.setAttribute("ddno",ddno);
		request.setAttribute("pageInfo",pageInfo);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "wllb";
	}
	
	
	

}
