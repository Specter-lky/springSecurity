package com.example.demo.config.security;

import com.example.demo.config.security.handler.MyAuthenticationFailureHandler;
import com.example.demo.config.security.handler.MyAuthenticationSuccessHandler;
import com.example.demo.filter.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    MyAuthenticationSuccessHandler successHandler;
    @Resource
    MyAuthenticationFailureHandler failureHandler;
    @Resource
    UserDetailsService myUserDetailsService;
    @Resource
    JwtAuthTokenFilter jwtAuthTokenFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//    @Override
//    protected void configure(HttpSecurity httpSecurity)throws Exception{
//        httpSecurity
//                .formLogin()//开启formLogin模式
//                .loginPage("/login.html")//用户没登录时，访问任何资源都跳转到该路径，即登录页面
//                .loginProcessingUrl("/login")//登录表单form中action的地址，也就是处理认证请求的路径
//                .usernameParameter("username")//默认是username
//                .passwordParameter("password")//默认是password
////                .defaultSuccessUrl("/index")//登录成功跳转接口
////                .failureForwardUrl("/login.html")//登录失败跳转页面
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and()//使用and()连接
//                .authorizeRequests()//配置权限
//                .antMatchers("/login.html","/login")
//                .permitAll()//用户可以任意访问
//                .antMatchers("/order")//需要对外暴露的资源路径
//                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")//user角色和admin角色都可以访问
//                .antMatchers("/system/user", "/system/role", "/system/menu")
//                .hasAnyRole("ADMIN")//admin角色可以访问
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()//authenticated()要求在执行该请求 时，必须已经登录了应用
//                .and()
//                .csrf().disable();//禁用跨站csrf攻击防御，否则无法登陆成功
//        //登出功能
//        httpSecurity.logout().logoutUrl("/logout");
//    }
    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/order")
                .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/system/user", "/system/role", "/system/menu")
                .hasAnyRole("ADMIN") //admin角色可以访问
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().
                and()//authenticated()要求在执行该请 求时，必须已经登录了应用
                // CRSF禁用，因为不使用session
                .csrf().disable() ;//禁用跨站csrf攻击防御，否则无法登陆成功
        // 登出功能
        httpSecurity.logout().logoutUrl("/logout");
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Override public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(bCryptPasswordEncoder().encode("123456"))
//                .roles("user")
//                .and()
//                .withUser("admin")
//                .password(bCryptPasswordEncoder().encode("123456"))
//                .roles("admin")
//                .and()
//                .passwordEncoder(bCryptPasswordEncoder());//配置BCrypt加密
//    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
