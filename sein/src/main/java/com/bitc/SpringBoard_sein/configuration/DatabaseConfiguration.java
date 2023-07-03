package com.bitc.SpringBoard_sein.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {

  @Autowired
  private ApplicationContext app;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  public HikariConfig hikariConfig() { return new HikariConfig();}

//  생성된 HikariCP의 설정 내용을 사용하여, 데이터 베이스를 연결
  @Bean
  public DataSource dataSource() throws Exception {
    DataSource dataSource = new HikariDataSource(hikariConfig());
    System.out.println(dataSource.toString());
    return dataSource;
  }

//  DB를 사용하기 위한 커넥션풀 생성
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws  Exception {
    SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
    ssfb.setDataSource(dataSource);
    ssfb.setMapperLocations(app.getResources("classpath:/sql/**/sql-*.xml"));
    ssfb.setConfiguration(mybatisConfig());

    return ssfb.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory ssf) {
    return new SqlSessionTemplate(ssf);}

//  mybatis.configuration 이름으로 구성된 설정 내용을 가져오기
  @Bean
  @ConfigurationProperties(prefix = "mybatis.configuration")
  public org.apache.ibatis.session.Configuration mybatisConfig() {
    return new org.apache.ibatis.session.Configuration();
  }
}









