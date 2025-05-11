package kr.co.koo;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;

@Controller
public class main_Controller {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "server_db1")
	server_db1 sd;

	@Resource(name = "movie_DTO")
	movie_DTO md;
	
	@Autowired
	movie_service msr;
	
	@Autowired
	DataSource dbs;
	
	//Cloud Oracle 정보값 가져오는 페이지
		@GetMapping("/data3.do")
		public String data3() throws Exception{
			try {		
				List<member_DTO> all = this.msr.member_all();
				for(member_DTO dto : all) {
					this.log.info(dto.getMNAME());
				}
			}catch (Exception e) {
				this.log.info(e.toString());
			}
			
			return null;
		}
	
	//Cloud Mysql 정보 가져오기 
	@GetMapping("/data2.do")
	public String data2() throws Exception{
		/*
		 */
		first_database fd = new first_database();
		
		try {
			//@Bean으로 현제 Database를 연결하여 데이터를 가져오는 방식 
			List<store_DTO> all = this.msr.store_all();
			for(store_DTO dto : all) {
				this.log.info(dto.getScode());
			}
		}catch (Exception e) {
			this.log.info(e.toString());
		}
		
		return null;
	}
	
	//본서버 MariaDB 정보 가져오기 
	@GetMapping("/data.do")
	public String data() {
		/*
		 */
		//실서버의 데이터베이스 연결 확인 
		List<movie_DTO> all = this.msr.movie_all();
		for(movie_DTO dto : all) {
			this.log.info(dto.getMname());
		}
		
		try {
			first_database fd = new first_database();
//			fd.test();
		}catch (Exception e) {
		}
		
		
		return null;
	}
	
	@GetMapping("/sample.do")
	public String sample(Model m) {
		String pdname="에어컨";
		m.addAttribute("pdname",pdname);
		
		return "/subpage.html";
	}
	
	//========
	
	@PostMapping("/webfileok.do")
	public String webfileok(@RequestParam("file1")MultipartFile file1,
			ServletRequest req
			) throws Exception {
		long filesize = file1.getSize();	//파일용량
//		this.log.info(file1.getOriginalFilename());
		String url = req.getServletContext().getRealPath("/upload/");
		File f = new File(url);
		this.log.info(url);
		
		String copys = url + file1.getOriginalFilename();
		try {
			FileCopyUtils.copy(file1.getBytes(), new File(copys));
		}catch (Exception e) {
			this.log.info(e.toString());
		}
		//spring-boot에서는 RealPath가 src 디렉토리에 생성을 하여 경로룰 생성
		//spring에서는 RealPath가 temp 디렉토리에 생성을 하여 경로를 생성 
		
		return null;
	}
	
	
	
	//================================

//	static movie_DTO mmd;

	// 최종예매 고객 정보 및 좌석 정보 저장 메소드
	@PostMapping("/movie_reservok2.do")
	public String movie_reservok2(Model m, @RequestParam(name = "seat_data") String seat_data) {
		Connection con = null;
		try {
			con = this.sd.dbinfo2();
			String sql = "insert into movie_res values('0',?,?,?,?,?,?,?,now())";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, this.md.getUsercode());
			ps.setString(2, this.md.getMname());
			ps.setInt(3, this.md.getMoviecode());
			ps.setString(4, this.md.getMovienm());
			ps.setString(5, this.md.getMdate());
			ps.setString(6, this.md.getMtime());
			ps.setInt(7, this.md.getMperson());
			int result = ps.executeUpdate();
			if (result > 0) { // 해당 고객에 인원수에 맞는 좌석번호를 저장
				String seat_no[] = seat_data.split(",");
				int w = 0;
				while (w < seat_no.length) {
					String sql2 = "insert into movie_seat values('0',?,?,?)";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, this.md.getUsercode());
					ps2.setInt(2, this.md.getMoviecode());
					ps2.setInt(3, Integer.parseInt(seat_no[w]));
					ps2.executeUpdate();
					w++;
				}
				m.addAttribute("msg", "alert('영화 예매가 모두 완료 되었습니다.'); location.href='./movie_reserv.html';");
			} else {
				m.addAttribute("msg", "alert('시스템 장애로 서비스가 원활하지 않습니다.'); location.href='./movie_reserv.html';");
			}
		} catch (Exception e) {
			this.log.info(e.toString());
		}
		return null;
	}

	// 예매 확인 정보 확인 페이지
	@PostMapping("/movie_reservok.do")
	public String movie_reservok(Model m, @ModelAttribute movie_DTO dto) {
		this.md = dto;
		m.addAttribute("person", dto.getMperson());
		return null;
	}
	
	/*

	@GetMapping("/test.do")
	public String test() {
//		String aaa = this.aaa();
//		this.log.info(aaa);

		this.log.info("test 실행 : " + this.md.getMname());

		return null;
	}

	public static String aaa() {
		String aaa = "hong";
		return aaa;
	}

	@PostMapping("/pollok.do")
	public String pollok(@RequestParam(name = "poll") String poll, Model m) {
		String msg = "";

		try {
			Connection con = this.sd.dbinfo1();
//			Connection con = this.sd.dbinfo2();
			System.out.println(con);

		} catch (Exception e) {
			System.out.println(e);
		}

		switch (poll) {
		case "1":
			msg = "JAVA";
			break;
		case "2":
			msg = "Spring";
			break;
		case "3":
			msg = "Spring-boot";
			break;
		}

		m.addAttribute("msg", msg);

		return null;
	}
	*/
}
