package test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class test_controller {
	
	@Autowired
	public DataSource datasource;
	

}
