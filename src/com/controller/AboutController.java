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
public class AboutController extends BaseController {
	@Resource
	AboutDAO aboutDAO;
	@Resource
	CategoryDAO categoryDAO;
	@Resource
	Saveobject saveobject;
	
	@RequestMapping("/admin/showAbout")
	public String showAbout(int id,HttpServletRequest request){
		String pagemsg = request.getParameter("pagemsg");
		About about =  aboutDAO.findById(id);
		request.setAttribute("about", about);
		request.setAttribute("pagemsg", pagemsg);
		return "admin/aboutedit";
	}
	
	@RequestMapping("/admin/aboutEdit")
	public String aboutEdit(About about,HttpServletRequest request){
		aboutDAO.update(about);
		return "redirect:showAbout.do?id=1";
	}
	
	@RequestMapping("About")
	public String About(int id,HttpServletRequest request){
		About about =  aboutDAO.findById(id);
		request.setAttribute("about", about);
		saveobject.getCart(request);
		saveobject.getCategoryObject(request);
		saveobject.hotSales("",request);
		List<Product> zphlist = saveobject.hotSales("",request);
		request.setAttribute("zphlist", zphlist);
		return "about";
	}
	
	
	
	
	

}
