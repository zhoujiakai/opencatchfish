package com.dondonqiang2.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String userPassword = request.getParameter("user_Password");
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		if(userName != null && userName.length()>0) {
			UsersDao usersDao = new UsersDao();
			List<Map<String, Object>> list = usersDao.findByParams(userName);

			if(list.isEmpty()) {
				usersDao.save(userName,userPassword);
	
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("ifAdded", "yes");
				list2.add(map2);
				
			}else {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("ifAdded", "existed");
				list2.add(map2);

			}
		}else {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("ifAdded", "isNull");
				list2.add(map2);
		}
			String msg = JSON.toJSONString(list2);
			PrintWriter out = response.getWriter();
			out.println(msg);
			out.close();
		System.out.println("999999999999999");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
