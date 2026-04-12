package com.dondonqiang2.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class DBHelper
 */
@WebServlet("/DBHelper")
public class DBHelper extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String USERNAME="root";
	private static final String PASSWORD="12345678";
	//<=5.5.6
//	private static final String URL= 
	//		"jdbc:mysql://localhost:3306/d2d2?characterEncoding=utf8";
	//MYSQL8.X
	private static final String URL= 
				"jdbc:mysql://localhost:3306/db_dondonqiang20?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
	private static final String DRIVER=
			"com.mysql.cj.jdbc.Driver";
	//mysql5:"com.mysql.jdbc.Driver"
	
	//加载驱动
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//连接的方法
	public static Connection getConnection() throws SQLException {
		Connection conn=
				DriverManager.getConnection(URL,USERNAME,PASSWORD);
		return conn;
	}
	//关闭数据库连接
	public static void close(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	public DBHelper() {
		// TODO Auto-generated constructor stub

	}

}
