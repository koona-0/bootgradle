package kr.co.koo;

import org.springframework.stereotype.Repository;

import lombok.Data;

//Oracle membership
@Data
@Repository("member_DTO")
public class member_DTO {
	int MIDX, MCODE;
	String MID,MNAME,MNICK,MPASS,MEMAIL,MHP,MJOIN,MDATE;

}
