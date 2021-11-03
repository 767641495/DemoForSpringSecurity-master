package com.example.demo.config;

import com.example.demo.utils.MyAuthenticationFailureHandler;
import com.example.demo.utils.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，功能页有相应权限才能访问
        //链式编程

        // 自定义表单认证
        http.formLogin()
                // 当发现/login 时认为是登录，需要执行 UserDetailsServiceImpl
                // 这不是controller里的映射，是spring security自带的
                .loginProcessingUrl("/login")
                // 此处是 post 请求,参数是登录成功后跳转地址
                .successForwardUrl("/toMain")
                // .successHandler(myAuthenticationFailureHandler)
                // 此处是 post 请求,参数是登录失败后跳转地址
                // .failureForwardUrl("/error")
                // .failureHandler(myAuthenticationFailureHandler)
                // 登陆界面
                .loginPage("/login")
                .and()
                // 登出页面
                .logout()
                .logoutSuccessUrl("/toLogin")
                .and()
                // url 拦截
                .authorizeRequests()
                // login.html 不需要被认证
                .antMatchers("/toLogin", "/swagger-ui").permitAll()
                // 静态资源允许访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.jpg",
                        "/profile/**"
                ).permitAll()
                //所有的请求都必须被认证。必须登录后才能访问。
                .anyRequest().authenticated()
                .and()
                //关闭 csrf 防护
                .csrf().disable();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
