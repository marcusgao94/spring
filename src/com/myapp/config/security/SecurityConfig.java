package com.myapp.config.security;

import com.myapp.service.SecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by gy on 16/7/14.
 */

@Configuration
@EnableWebSecurity
@ComponentScan("com.myapp.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private SecurityUserDetailService securityUserDetailService;

    @Autowired
    public void configureAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(securityUserDetailService);
    }

    @Bean
    public MyUsernamePasswordAuthenticationFilter getAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/signin"));
        filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
        return filter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/secure/**").authenticated()
                .and().exceptionHandling()
                    .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and().formLogin()
                    .loginProcessingUrl("/signin")
                    .usernameParameter("username").passwordParameter("password")
                    .loginPage("/view/login").permitAll()
                .and().addFilterBefore(getAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .httpBasic();
    }
}
