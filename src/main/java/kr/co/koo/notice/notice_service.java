package kr.co.koo.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class notice_service {

	@Autowired
	@Qualifier(value = "sqltemplate")
	private SqlSession sql1;

	public List<notice_DTO> notice_list() {
		List<notice_DTO> all = this.sql1.selectList("notice_list");
		return all;
	}
	
	public Integer notice_count() {
		Integer result = this.sql1.selectOne("notice_count");
		return result;
	}
	
	//실시간 아이디 체크
	public Integer test_member(String mid) {
		Integer result = this.sql1.selectOne("test_member",mid);
		return result;
	}
	
}
