package kr.co.koo;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("movie_DTO")
public class movie_DTO {
	/*
	midx : 자동증가값
	moviecode : 영화 고유값
	usercode : 고객 고유값
	mperson : 예매 인원수 
	mname : 고객명
	movienm : 영화이름 
	mdate : 예매일자
	mtime : 예매시간 
	mtoday : 예매날짜
	*/
	
	//movie_res
	int midx, moviecode, usercode, mperson;
	String mname, movienm, mdate, mtime, mtoday;

	//movie_seat
	int sidx, seatno;
	
}