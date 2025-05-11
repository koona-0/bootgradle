package kr.co.koo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class movie_service {
	
	//한 개의 디비만 연결되었을 때 
//	@Autowired
//	private mapper mapper;
	
	//여러개의 디비가 연결되었을 때 
	@Autowired
	@Qualifier(value="sqltemplate")
	private SqlSession sql1;
	
	@Autowired
	@Qualifier(value="sqltemplate2")
	private SqlSession sql2;
	
	@Autowired
	@Qualifier(value="sqltemplate3")
	private SqlSession sql3;
	
	public List<movie_DTO> movie_all(){
		//한 개의 디비만 연결되었을 때 
//		List<movie_DTO> all = mapper.movie_all();

		//여러개의 디비가 연결되었을 때 
		List<movie_DTO> all = this.sql1.selectList("movie_all");
		return all;
	}
	
	public List<store_DTO> store_all(){
		//한 개의 디비만 연결되었을 때 
//		List<store_DTO> all = mapper.store_all();

		//여러개의 디비가 연결되었을 때 
		List<store_DTO> all = this.sql2.selectList("store_all");
		
		return all;
	}
	
	public List<member_DTO> member_all(){
		//여러개의 디비가 연결되었을 때 
		List<member_DTO> all = this.sql3.selectList("member_all");
		
		return all;
	}

}
