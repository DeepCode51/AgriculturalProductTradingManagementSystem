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
public class ApplyController extends BaseController {
	@Resource
	ApplyDAO applyDAO;
	@Resource
	UserDAO userDAO;
	
	
	
	
	
	//后台查询入驻申请列表
	@RequestMapping("admin/applyList")
	public String applyList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		String key = request.getParameter("key");
		HashMap map = new HashMap();
		map.put("key", key);
		PageHelper.startPage(pageNum, 10);
		List<Apply> list = applyDAO.selectAll(map);
		PageInfo<Apply> pageInfo =  new PageInfo<Apply>(list);
		request.setAttribute("key",key);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/applylist";
	}
	
	//商家申请列表
	@RequestMapping("admin/applyLb")
	public String applyLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		User admin = (User)request.getSession().getAttribute("admin");
		User user = userDAO.findById(admin.getId());
		String key = request.getParameter("key");
		HashMap map = new HashMap();
		map.put("memberid", user.getId());
		PageHelper.startPage(pageNum, 10);
		List<Apply> list = applyDAO.selectAll(map);
		PageInfo<Apply> pageInfo =  new PageInfo<Apply>(list);
		request.setAttribute("key",key);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/applylb";
	}
	//添加入驻申请 
	@RequestMapping("admin/applyAdd")
	public String applyAdd(Apply apply,HttpServletRequest request){
		User admin = (User)request.getSession().getAttribute("admin");
		apply.setShstatus("待审核");
		apply.setSavetime(Info.getDateStr());
		apply.setMemberid(String.valueOf(admin.getId()));
		applyDAO.add(apply);
		return "redirect:applyLb.do";
	}
	
	//审核申请
	@RequestMapping("admin/updateApply")
	public String updateApply(Apply apply,HttpServletRequest request){
		applyDAO.update(apply);
		if(apply.getShstatus().equals("通过")){
			Apply app = applyDAO.findById(apply.getId());
			User user = userDAO.findById(Integer.parseInt(app.getMemberid()));
			user.setShstatus("通过审核");
			user.setFilename(app.getFilename());
			user.setIdcard(app.getIdcard());
			user.setRealname(app.getRealname());
			userDAO.update(user);
		}
		return "redirect:applyList.do";
	}
	
	//删除入驻申请
	@RequestMapping("admin/applyDel")
	public String applyDel(int id, HttpServletRequest request,HttpServletResponse response){
		applyDAO.delete(id);
		return "redirect:applyList.do";
	}
	
	//删除入驻申请
	@RequestMapping("admin/applySc")
	public String applySc(int id, HttpServletRequest request,HttpServletResponse response){
		applyDAO.delete(id);
		return "redirect:applyLb.do";
	}
	
	
	
	
	
	

}
