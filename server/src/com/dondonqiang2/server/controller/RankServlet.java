package com.dondonqiang2.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dondonqiang2.server.dao.UsersDao;

/**
 * Servlet implementation class RankServlet
 */
@WebServlet("/RankServlet")
public class RankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RankServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//解决传输时中文乱码问题 在Servlet的doGet()方法中加入以下两行代码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("user_Name");
		String userScore = request.getParameter("user_Score");
		System.out.println("这是游戏结果界面的当前用户："+userName);
		UsersDao usersDao = new UsersDao();
		List<Map<String, Object>> list = usersDao.getRankNumber(userName,userScore);
		String msg = JSON.toJSONString(list);
		PrintWriter out = response.getWriter();
		System.out.println("这是游戏结果的用户排名信息"+msg);
		out.println(msg);
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
