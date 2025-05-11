package kr.co.koo.notice;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("notice_DTO")
public class notice_DTO {
	int idx, nview;
	String subject, writer, pw, texts, filenm, nfile, ndate;

}
