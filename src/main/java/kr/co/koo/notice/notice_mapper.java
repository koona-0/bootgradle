package kr.co.koo.notice;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface notice_mapper {
	public List<notice_DTO> notice_list();
	Integer notice_count();
	Integer test_member(String mid);

}
