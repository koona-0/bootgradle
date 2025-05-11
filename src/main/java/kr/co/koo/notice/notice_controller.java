package kr.co.koo.notice;

import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletResponse;

@Controller
public class notice_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	notice_service nsr;
	
	PrintWriter pw = null;

	//API 통신 (POST)
	@PostMapping("/idcheck.do")
	public String idcheck(@RequestParam("mid")String mid,
			ServletResponse res) throws Exception{
		
		this.pw = res.getWriter();
		this.log.info(mid);
		
		//select로 DB의 count 정보를 받은 후 조건에 맞는 값을 Front-end에 전달 
		Integer result = this.nsr.test_member(mid);
		this.log.info(result.toString());
		if(result > 0) {
			this.pw.print("no");
		}else {
			this.pw.print("ok");
		}
		this.pw.close();
		return null;
	}
	
	//API 통신 (GET)
	@GetMapping("/notice/eacheck.do")
	public String notice_ea(@RequestParam("ea")int ea,
			ServletResponse res) throws Exception {
//		GenericServlet res
//		HttpServletResponse res
//		ServletResponse res
		
		this.pw = res.getWriter();
		
		//DBMS의 부하를 최소화하기위해서 잠시 프로세서를 중지후 가동
		//Thread.sleep(3000);	//3초 멈췄다감 
		
		Integer total = this.nsr.notice_count();
		String msg = "no";
		if(ea < total) {
			msg="ok";
		}
		this.pw.print(msg);
		
		return null;
	}
	
	@GetMapping("/notice/list.do")
	public String notice_list(Model m) {
		List<notice_DTO> all = this.nsr.notice_list();
		m.addAttribute("all",all);
		m.addAttribute("ea",all.size());
		
		
		return "/subpage.html";
	}

}
