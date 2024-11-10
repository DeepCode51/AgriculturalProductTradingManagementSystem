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
public class FavbbsController extends BaseController {
	@Resource
	FavbbsDAO favbbsDAO;
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
	
	
	
	//查询收藏列表
	@RequestMapping("favbbsLb")
	public String favbbsLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,HttpServletRequest request) {
		
		Member member = (Member)request.getSession().getAttribute("sessionmember");
		HashMap map = new HashMap();
		map.put("mid", member.getId());
		PageHelper.startPage(pageNum, 10);
		List<Favbbs> list = favbbsDAO.selectAll(map);
		for(Favbbs favbbs:list){
			Bbs bbs = bbsDAO.findById(favbbs.getBbsid());
			Member fbz = memberDAO.findById(favbbs.getMid());
			bbs.setMember(fbz);
			favbbs.setBbs(bbs);
		}
		PageInfo<Favbbs> pageInfo =  new PageInfo<Favbbs>(list);
		request.setAttribute("pageInfo",pageInfo);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		return "favbbslb";
	}
	
	
	//删除收藏
	@RequestMapping("favbbsDel")
	public String favbbsDel(int id, HttpServletRequest request,HttpServletResponse response){
		favbbsDAO.delete(id);
		return "redirect:favbbsLb.do";
	}
	
	//添加收藏
	@RequestMapping("addFavbbs")
	public void addFavbbs(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			Member member = (Member) request.getSession().getAttribute(
					"sessionmember");
			if (member != null) {
				String bbsid = request.getParameter("bbsid");
				HashMap map = new HashMap();
				map.put("mid", member.getId());
				map.put("bbsid", bbsid);
				List<Favbbs> list = favbbsDAO.selectAll(map);
				if(list.size()==0){
					Favbbs favbbs = new Favbbs();
					favbbs.setMid(member.getId());
					favbbs.setBbsid(Integer.valueOf(bbsid));;
					favbbsDAO.add(favbbs);
					out.print("1");
				}else{
					out.print("2");
				}
			} else {
				out.print("0");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
