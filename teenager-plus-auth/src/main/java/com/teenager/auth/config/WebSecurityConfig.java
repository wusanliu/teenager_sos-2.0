package com.teenager.auth.config;


import com.teenager.auth.security.JwtAuthenticationProvider;
import com.teenager.auth.security.JwtAuthenticationTokenFilter;
import com.teenager.auth.security.MyUnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyUnauthorizedHandler unauthorizedHandler;
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider();
    }
//我们自定义的拦截器
@Bean
public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
}

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//由于使用的是JWT，这里不需要csrf防护
        httpSecurity.csrf().disable()
                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //允许对于网站静态资源的无授权访问
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html"
                ).permitAll()
                //对登录注册允许匿名访问
                .antMatchers("/user/login", "/user/register").permitAll()
                //测试时全部运行访问.permitAll();
                .antMatchers("/test/**").permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        //将我们的JWT filter添加到UsernamePasswordAuthenticationFilter前面，因为这个Filter是authentication开始的filter，我们要早于它
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //使用自定义provider
        httpSecurity.authenticationProvider(jwtAuthenticationProvider());
//        认证失败处理
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler);

    }

//@Bean
//public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    //由于使用的是JWT，这里不需要csrf防护
//    httpSecurity.csrf().disable()
//            //基于token，所以不需要session
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            //允许对于网站静态资源的无授权访问
//            .antMatchers(HttpMethod.GET,
//                    "/",
//                    "/*.html"
//            ).permitAll()
//            //对登录注册允许匿名访问
//            .antMatchers("/user/login", "/user/register").permitAll()
//            //测试时全部运行访问.permitAll();
//            .antMatchers("/test/**").permitAll()
//            .anyRequest()// 除上面外的所有请求全部需要鉴权认证
//            .authenticated();
//    // 禁用缓存
//    httpSecurity.headers().cacheControl();
//    //将我们的JWT filter添加到UsernamePasswordAuthenticationFilter前面，因为这个Filter是authentication开始的filter，我们要早于它
//    httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    return httpSecurity.build();
//}
}