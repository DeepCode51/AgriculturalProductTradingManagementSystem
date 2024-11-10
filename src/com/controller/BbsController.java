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
public class BbsController extends BaseController {
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
	BbsDAO bbsDAO;
	
	
	
	//后台查询帖子列表
	@RequestMapping("/admin/bbsList")
	public String bbsList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		String key = request.getParameter("key");
		HashMap map = new HashMap();
		map.put("key", key);
		map.put("fid", "0");
		PageHelper.startPage(pageNum, 10);
		List<Bbs> list = bbsDAO.selectAll(map);
		for(Bbs bbs:list){
			bbs.setMember(memberDAO.findById(bbs.getMid()));
		}
		PageInfo<Bbs> pageInfo =  new PageInfo<Bbs>(list);
		request.setAttribute("key",key);
		request.setAttribute("pageInfo",pageInfo);
		return "admin/bbslist";
	}
	
	//后台查询帖子评论列表
		@RequestMapping("/admin/bbsplList")
		public String bbsplList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,int fid,HttpServletRequest request) {
			String key = request.getParameter("key");
			HashMap map = new HashMap();
			map.put("key", key);
			map.put("fid", fid);
			PageHelper.startPage(pageNum, 10);
			List<Bbs> list = bbsDAO.selectAll(map);
			for(Bbs bbs:list){
				bbs.setMember(memberDAO.findById(bbs.getMid()));
			}
			PageInfo<Bbs> pageInfo =  new PageInfo<Bbs>(list);
			request.setAttribute("key",key);
			request.setAttribute("fid",fid);
			request.setAttribute("pageInfo",pageInfo);
			return "admin/bbspllist";
		}
	
	
	
	//删除帖子评论
		@RequestMapping("/admin/bbsplDelforadmin")
		public String bbsplDelforadmin(int id,int fid, HttpServletRequest request,HttpServletResponse response){
			bbsDAO.delete(id);
			return "redirect:bbsplList.do?fid="+fid;
		}
	
		//删除
				@RequestMapping("/admin/bbsDelforadmin")
				public String bbsDelforadmin(int id, HttpServletRequest request,HttpServletResponse response){
					Bbs bbs = bbsDAO.findById(id);
					HashMap map = new HashMap();
					map.put("fid",id);
					List<Bbs> list = bbsDAO.selectAll(map);
					for(Bbs b:list){
						bbsDAO.delete(b.getId());
					}
					bbsDAO.delete(id);
					return "redirect:bbsList.do";
				}
	
	//会员帖子列表
		@RequestMapping("mybbsList")
		public String mybbsList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
			Member member = (Member) request.getSession().getAttribute("sessionmember");
			String key = request.getParameter("key");
			HashMap map = new HashMap();
			map.put("mid", member.getId());
			map.put("fid", "0");
			PageHelper.startPage(pageNum, 10);
			List<Bbs> list = bbsDAO.selectAll(map);
			for(Bbs bbs:list){
				bbs.setMember(memberDAO.findById(bbs.getMid()));
			}
			PageInfo<Bbs> pageInfo =  new PageInfo<Bbs>(list);
			request.setAttribute("key",key);
			request.setAttribute("pageInfo",pageInfo);
			saveobject.getCart(request);
			saveobject.getCategoryObject(request);
			return "mybbslist";
		}
		
		
		//前台查询帖子列表
		@RequestMapping("bbsLb")
		public String bbsLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
			String key = request.getParameter("key");
			HashMap map = new HashMap();
			map.put("key", key);
			map.put("fid", "0");
			PageHelper.startPage(pageNum, 10);
			List<Bbs> list = bbsDAO.selectAll(map);
			for(Bbs bbs:list){
				bbs.setMember(memberDAO.findById(bbs.getMid()));
			}
			PageInfo<Bbs> pageInfo =  new PageInfo<Bbs>(list);
			request.setAttribute("key",key);
			request.setAttribute("pageInfo",pageInfo);
			saveobject.getCart(request);
			saveobject.getCategoryObject(request);
			
			saveobject.hotSales("",request);
			List<Product> zphlist = saveobject.hotSales("",request);
			request.setAttribute("zphlist", zphlist);
			
			return "bbslb";
		}
		
	
	//去发布帖子
	@RequestMapping("toBbsAdd")
	public String toBbsAdd(HttpServletRequest request){
		Bbs bbs = new Bbs();
		bbs.setFid(0);
		request.setAttribute("bbs",bbs);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "bbsadd";
	}
	
	//帖子提交
	@RequestMapping("bbsAdd")
	public String bbsAdd(Bbs bbs,HttpServletRequest request){
		Member member = (Member) request.getSession().getAttribute("sessionmember");
		bbs.setSavetime(Info.getDateStr());
		bbs.setMid(member.getId());
		bbsDAO.add(bbs);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "redirect:mybbsList.do";
	}
	
	//去编辑帖子
	@RequestMapping("toBbsEdit")
	public String toBbsEdit(int id,HttpServletRequest request){
		Bbs bbs = bbsDAO.findById(id);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		request.setAttribute("bbs",bbs);
		return "bbsedit";
	}
	
	//编辑帖子提交
		@RequestMapping("bbsEdit")
		public String bbsEdit(Bbs bbs,HttpServletRequest request){
			bbsDAO.update(bbs);
			return "redirect:mybbsList.do";
		}
	
	
	//删除帖子
	@RequestMapping("bbsDel")
	public String bbsDel(int id, HttpServletRequest request,HttpServletResponse response){
		Bbs bbs = bbsDAO.findById(id);
		HashMap map = new HashMap();
		map.put("fid",id);
		List<Bbs> list = bbsDAO.selectAll(map);
		for(Bbs b:list){
			bbsDAO.delete(b.getId());
		}
		bbsDAO.delete(id);
		return "redirect:mybbsList.do";
	}
	
	//帖子详情
	@RequestMapping("bbsShow")
	public String bbsShow(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,int id,HttpServletRequest request){
		Bbs bbs = bbsDAO.findById(id);
		Member author = memberDAO.findById(bbs.getMid());
		bbs.setMember(author);
		HashMap map = new HashMap();
		map.put("fid",id);
		PageHelper.startPage(pageNum, 10);
		List<Bbs> list = bbsDAO.selectAll(map);
		for(Bbs b:list){
			b.setMember(memberDAO.findById(b.getMid()));
		}
		PageInfo<Bbs> pageInfo =  new PageInfo<Bbs>(list);
		request.setAttribute("pageInfo",pageInfo);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		request.setAttribute("bbs",bbs);
		return "bbsx";
	}
	
	//帖子回复
	@RequestMapping("tzhf")
	public String tzhf(HttpServletRequest request){
		Member member = (Member) request.getSession().getAttribute("sessionmember");
		int fid = Integer.valueOf(request.getParameter("fid"));
		String note = request.getParameter("note");
		Bbs bbs = new Bbs();
		bbs.setFid(fid);
		bbs.setNote(note);
		bbs.setSavetime(Info.getDateStr());
		bbs.setMid(member.getId());
		bbsDAO.add(bbs);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "redirect:bbsShow.do?id="+fid;
	}
	

}
