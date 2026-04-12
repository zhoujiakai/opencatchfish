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
 * Servlet implementation class ListViewServlet
 */
@WebServlet("/ListViewServlet")
public class ListViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListViewServlet() {
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
		UsersDao usersDao = new UsersDao();
		List<Map<String, Object>> list = usersDao.findTopTen();
		String msg = JSON.toJSONString(list);
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.close();
		//System.out.println(msg);
		System.out.println("333333333333");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
