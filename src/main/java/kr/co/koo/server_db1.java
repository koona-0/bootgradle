package kr.co.koo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Repository;

@Repository("server_db1")
// 클라우드 디비와 로컬 디비 나눠서 핸들링 

// 하나는 마리아 하나는 마이에스큐엘일때 둘다 마이에스큐엘 씀
// 둘은 그냥 똑같음 
public class server_db1 {
	String dbinfo = "";
	String dburl = "";
	String dbuser = "";
	String dbpass = "";
	Connection con = null;

	// localhost의 mysql
	public Connection dbinfo1() throws Exception {
		this.dbinfo = "com.mysql.cj.jdbc.Driver";
//		this.dburl = "jdbc:mysql://172.30.1.53:53306/mrp";
		this.dburl = "jdbc:mysql://localhost:3306/mrp";
		this.dbuser = "koo";
		this.dbpass = "koo1004";

		Class.forName(this.dbinfo);
		this.con = DriverManager.getConnection(this.dburl, this.dbuser, this.dbpass);

		return this.con;
	}

	// Cloud DB
	public Connection dbinfo2() throws Exception {
		this.dbinfo = "com.mysql.cj.jdbc.Driver";
		//로컬용 디비연결
//		this.dburl = "jdbc:mysql://172.30.1.53:13306/webapi";
		//배포용 디비연결 
		this.dburl = "jdbc:mysql://localhost:13306/webapi";
		this.dbuser = "webapi";
		this.dbpass = "b402402";

		Class.forName(this.dbinfo);
		this.con = DriverManager.getConnection(this.dburl, this.dbuser, this.dbpass);

		return this.con;
	}

}