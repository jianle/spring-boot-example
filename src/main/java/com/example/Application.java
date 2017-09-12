package com.example;

import javax.servlet.Filter;
import javax.sql.DataSource;

import com.example.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by jianle on 17-6-8
 */
@EnableCaching
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }


    @Primary
    @Bean(name="datasource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource datasource() {
        //http://wiki.jikexueyuan.com/project/tomcat/tomcat-jdbc-pool.html
        return DataSourceBuilder.create().type(org.apache.tomcat.jdbc.pool.DataSource.class).build();
    }


    @Bean(name="jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("datasource") DataSource datasource) {
        return new JdbcTemplate(datasource);
    }

    //配置多个数据源同上

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/login");
    }
}
