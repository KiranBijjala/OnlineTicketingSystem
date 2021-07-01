package com.capstone.ticket.controller;



import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/").permitAll().and()
                .formLogin();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

    }
}