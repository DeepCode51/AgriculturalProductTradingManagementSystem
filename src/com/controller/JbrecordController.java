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
public class JbrecordController extends BaseController {
	@Resource
	JbrecordDAO jbrecordDAO;
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
	BbsDAO bbsDAO;
	
	
	//查询举报列表
	@RequestMapping("/admin/jbList")
	public String jbList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		
		HashMap map = new HashMap();
		PageHelper.startPage(pageNum, 10);
		List<Jbrecord> list = jbrecordDAO.selectAll(map);
		for(Jbrecord jbrecord:list){
			Bbs bbs = bbsDAO.findById(jbrecord.getBbsid());
			bbs.setMember(memberDAO.findById(bbs.getMid()));
			jbrecord.setBbs(bbs);
			jbrecord.setMember(memberDAO.findById(jbrecord.getMid()));
		}
		PageInfo<Jbrecord> pageInfo =  new PageInfo<Jbrecord>(list);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/jblist";
	}
	
	
	
	
	//删除举报
	@RequestMapping("/admin/jbDel")
	public String jbDel(int id, HttpServletRequest request,HttpServletResponse response){
		jbrecordDAO.delete(id);
		return "redirect:jbList.do";
	}
	
	//举报前的验证
	@RequestMapping("tojbAddYz")
	public void tojbAddYz(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			Member member = (Member) request.getSession().getAttribute(
					"sessionmember");
			if (member != null) {
				String bbsid = request.getParameter("bbsid");
				out.print("1");
			} else {
				out.print("0");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//去举报页面
	@RequestMapping("tojbAdd")
	public String tojbAdd(int bbsid, HttpServletRequest request,HttpServletResponse response){
		Jbrecord jb = new Jbrecord();
		jb.setBbsid(bbsid);
		request.setAttribute("jb",jb);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "jbadd";
	}
	
	//举报
	@RequestMapping("jbAdd")
	public String jbAdd(Jbrecord jb, HttpServletRequest request,HttpServletResponse response){
		Member member = (Member) request.getSession().getAttribute(
				"sessionmember");
		jb.setMid(member.getId());
		jbrecordDAO.add(jb);
		request.setAttribute("suc", "举报成功!");
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "jbadd";
	}

}
