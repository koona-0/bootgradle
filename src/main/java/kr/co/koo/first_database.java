package kr.co.koo;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


//실서버 MariaDB
@Configuration
//@EnableTransactionManagement	//AOP, PROXY class, Database config를 사용할 때 적용하는 어노테이션 
public class first_database {
	//Properties파일을 로드 
	Properties pp = new Properties();
	String pname = "database.properties";	//Properties파일명 
	
	//정통 방법 
	//MatiaDB 정보 세팅 
	//여기에 @Bean 못하는이유 빈은 태그를 찾음 <bean first>태그는 없음 그래서 에러남 => 아래 메소드를 즉시실행 메소드로 변경! 
	public first_database() throws Exception{
		this.pp.load(this.getClass().getClassLoader().getResourceAsStream(pname));
		this.pp.setProperty("spring.datasource.driver-class-name", this.pp.getProperty("spring.first.datasource.driver-class-name"));
		this.pp.setProperty("spring.datasource.url", this.pp.getProperty("spring.first.datasource.url"));
		this.pp.setProperty("spring.datasource.username", this.pp.getProperty("spring.first.datasource.username"));
		this.pp.setProperty("spring.datasource.password", this.pp.getProperty("spring.first.datasource.password"));
	}
	
	//first가 값을 세팅해주는데 얘는 컨트롤러가 호출을 시켜줘야함
	//근데 아래Bean은 클래스에 넣어놨지만 바로 작동해버림 그래서 에러가 발생함 (Mybatis가 작동시켜버림)
	//@Bean은 개발자가 실행하는게 아니라 Mybatis가 구동시 각 클래스별로 @Bean이 있는거를 작동시킴 
	//@Bean은 환경설정이라서 컨트롤러보다 먼저 인식됨  
	
	@Bean(name="mysql1")	//주석하면 안돌아감 
	@Primary
	public DataSource datasource() {
		//DataSource룰 변경시 Mybatis에서 Database 서버 정보응 변경함 
		String classnm = this.pp.getProperty("spring.datasource.driver-class-name");
		String url = this.pp.getProperty("spring.datasource.url");
		String user = this.pp.getProperty("spring.datasource.username");
		String pass = this.pp.getProperty("spring.datasource.password");
		
//		return new DriverManagerDataSource(url,user,pass);
		
		//DataSource 정보를 저장하는 배열값 
		DataSourceBuilder dsb = DataSourceBuilder.create();
		dsb.driverClassName(classnm);
		dsb.url(url);
		dsb.username(user);
		dsb.password(pass);
		return dsb.build();
	}
	
	//@Qualifier : @Bean name값을 가져와서 객체에 주입하는 방식 
	@Bean(name="sqlfactory")
	@Primary
	public SqlSessionFactory sqlfactory(@Qualifier("mysql1")DataSource ds,
			ApplicationContext ac) throws Exception{
		SqlSessionFactoryBean sqlf = new SqlSessionFactoryBean();
		sqlf.setDataSource(ds);
//		sqlf.setConfigLocation("classpath:/config/config.xml");	//config파일 사용시 씀 
//		sqlf.setMapperLocations("classpath:/mapper/mapper.xml");	//원래이런식으로
//		sqlf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(this.pp.getProperty("mybatis.mapper-locations")));
		sqlf.setMapperLocations(ac.getResources("classpath:/mapper/*.xml"));
		
		return sqlf.getObject();
	}
	
	@Bean(name="sqltemplate")
	public SqlSessionTemplate sqltemplate(@Qualifier("sqlfactory")SqlSessionFactory sf) throws Exception{
		SqlSessionTemplate stp = new SqlSessionTemplate(sf);
		return stp;
	}
	
	/*
	//주의할점 @Bean때리면 무조건 발동됨 그래서 없는데!!하면서 에러남 얘는잇는데 쟤는없어 충돌남 
	
	//Properties에 있는 Database 정보를 가져오는 방법, 변경 방법 
	public void test() throws Exception {	//원래 트캐해야함 
		//해당 Properties 파일안의 내용을 로드하는 메소드 
		this.pp.load(this.getClass().getClassLoader().getResourceAsStream(pname));
		//getProperty 가 해당 파일의 클래스 및 메소드의 값을 가져옴 
		String user = this.pp.getProperty("spring.first.datasource.username");
		System.out.println(user);
		String url = this.pp.getProperty("spring.first.datasource.url");
		System.out.println(url);
		String dcn = this.pp.getProperty("spring.first.datasource.driver-class-name");
		System.out.println(dcn);
		
		//이렇게 직접 넣을 수 있음 (setter)
		this.pp.setProperty("spring.first.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
	}
	*/

}
