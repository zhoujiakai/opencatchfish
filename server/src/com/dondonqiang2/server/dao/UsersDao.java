package com.dondonqiang2.server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.dondonqiang2.server.util.DBHelper;

public class UsersDao {
	private QueryRunner qr;
	private Connection conn;

	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> list = null;
		String sql = "select * from users";
		try {
			conn = DBHelper.getConnection();
			qr = new QueryRunner();
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBHelper.close(conn);
		}
		return list;

	}

	public int save(String user_name, String user_password) {
		int row = 1;
		String sql = "insert into users(user_name,user_password)" + " values(?,?)";
		try {
			conn = DBHelper.getConnection();
			qr = new QueryRunner();
			row = qr.update(conn, sql, user_name, user_password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBHelper.close(conn);
		}

		return row;

	}

	// 查询结果为多条用List<Map<String,Object>>,查询结果为一条使用Map<String,Object>
	public List<Map<String, Object>> findByParams(String userName) {
		List<Map<String, Object>> list = null;
		String sql = "select @n:=1+@n as 'user_rank' , a.* from users a,(select @n:=0)b order by user_maxscore DESC";
		// 创建一个容器存放要查询的条件
		List<String> params1 = new ArrayList<String>();
		if (userName != null && userName.length() > 0) {
			sql = "select c.* from ( select @n:=1+@n as user_rank , a.* from users a,(select @n:=0)b order by user_maxscore DESC) c where c.user_name = ?";
			params1.add(userName);
		}
		System.out.println(sql);
		System.out.println(params1);
		try {
			conn = DBHelper.getConnection();
			qr = new QueryRunner();
			list = qr.query(conn, sql, new MapListHandler(), params1.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBHelper.close(conn);
		}
		return list;
	}

	/**
	 * 创建一个容器存放要查询的条件 List<String> params = new ArrayList<String>();
	 * if(userName!=null && user.length()>0){ sql+=" and user_name like ?";
	 * params.add("%" + user_name + "%"); } 查商品价格区间判断 if(loPrice=null
	 * &&loPrice.length()>0){ sql+=" and proPrice>=?"; params.add(loPrice); }
	 * 
	 */

	public List<Map<String, Object>> findTopTen() {
		List<Map<String, Object>> list = null;
		String sql = "select @n:=1+@n as 'user_rank' , a.* from users a,(select @n:=0)b order by user_maxscore DESC limit 10";
		try {
			conn = DBHelper.getConnection();
			qr = new QueryRunner();
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBHelper.close(conn);
		}
		return list;

	}
	public List<Map<String, Object>> getRankNumber(String userName, String userScore) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		list = findByParams(userName);
		Map<String, Object> map = new HashMap<>();
		map = list.get(0);
		String value = map.get("user_maxscore").toString();
		int maxScore = Integer.parseInt(value);
		int score = Integer.parseInt(userScore);
		if (score > maxScore) {
			// 创建一个新容器存放要查询的条件
			List<String> params2 = new ArrayList<String>();
			//更新
			String sql = "update users set user_maxscore = ? where user_name = ?";
			params2.add(userScore);
			params2.add(userName);
			System.out.println("---------------");
			System.out.println(sql);
			System.out.println(params2);
			System.out.println("---------------");
			try {
				conn = DBHelper.getConnection();
				qr = new QueryRunner();
				//增删改用qr.update();
				qr.update(conn, sql, params2.toArray());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBHelper.close(conn);
			}
			
		//更新之后重新查询一遍
			list = findByParams(userName);
			
		}
		return list;
	}

	public static void main(String[] args) {
		//System.out.println( "= '?'");
		UsersDao dao = new UsersDao();
		List<Map<String, Object>> list = dao.getRankNumber("Mike","5000");
		//List<Map<String, Object>> list = dao.findByParams("Mike");
		System.out.println(list);
	}

	public UsersDao() {
		// TODO Auto-generated constructor stub
	}

//多个参数时
//	public List<Map<String, Object>> findByParams
//	(String proName,String loPrice,String hiPrice){
//	List<Map<String, Object>> list=null;
//	String sql="select * from tbl_pro_info"
//			+ " where 1=1 ";
//	//创建一个容器存放要查询的条件
//	List<String> params=new ArrayList<String>();
//	if(proName!=null && proName.length()>0) {
//		sql+=" and proName like ?";
//		params.add("%"+proName+"%");
//	}
//	if(loPrice!=null && loPrice.length()>0) {
//		sql+=" and proPrice>=?";
//		params.add(loPrice);
//	}
//	if(hiPrice != null && hiPrice.length()>0) {
//		sql+=" and proPrice <=?";
//		params.add(hiPrice);
//	}
//	try {
//		conn=DBHelper.getConnection();
//		qr=new QueryRunner();
//		list=qr.query(conn, sql,
//				new MapListHandler(),
//				params.toArray());
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}finally {
//		DBHelper.close(conn);
//	}
//	return list;
//}

}