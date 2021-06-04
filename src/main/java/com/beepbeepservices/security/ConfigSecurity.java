package com.beepbeepservices.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(passwordEncoder().encode("123"));
		auth.jdbcAuthentication().dataSource(dataSource).
		  usersByUsernameQuery("select username as principal,password as credentials,'true' from user where username=?"
		  )
		  .authoritiesByUsernameQuery("select username as principal ,role as role from user where username=?"
		  ) .rolePrefix("ROLE_") .passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//on va définir les stratégies de sécurité les régles 
		http.formLogin();
		
		http.authorizeRequests().antMatchers("/username").permitAll();
		http.authorizeRequests().antMatchers("/colis/**").hasAnyRole("CLIENT","ADMIN","CHAUFFEUR");
		http.authorizeRequests().antMatchers("/villes/**","/comptes/**","/users/**").hasRole("ADMIN");
		
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedPage("/403");
	}

}
