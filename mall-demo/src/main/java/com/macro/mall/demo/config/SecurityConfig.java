package com.macro.mall.demo.config;

import com.macro.mall.demo.bo.AdminUserDetails;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsAdminExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * SpringSecurity相关配置
 * Created by macro on 2018/4/26.
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests//配置权限
//                .antMatchers("/").access("hasRole('TEST')")//该路径需要TEST角色
//                .antMatchers("/brand/list").hasAuthority("TEST")//该路径需要TEST权限
                .antMatchers("/**").permitAll())//ÆôÓÃ»ùÓÚhttpµÄÈÏÖ¤
                .httpBasic(basic -> basic
                        .realmName("/"))//启用基于http的认证
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login?error=true"))//配置登录页面
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))//记住密码功能
                .csrf(csrf -> csrf
                        .disable())//关闭跨域伪造
                .headers(headers -> headers//去除X-Frame-Options
                        .frameOptions()
                        .disable());
    }

    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UmsAdminExample example = new UmsAdminExample();
                example.createCriteria().andUsernameEqualTo(username);
                List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
                if (umsAdminList != null && umsAdminList.size() > 0) {
                    return new AdminUserDetails(umsAdminList.get(0));
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }
}
