package com.onsemi.hms.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(getUserQuery())
                .passwordEncoder(new BCryptPasswordEncoder())
                .authoritiesByUsernameQuery(getAuthoritiesQuery());
    }

    private String getUserQuery() {
        return "SELECT login_id AS username, password AS password, is_active AS enabled "
                + "FROM hms_user "
                + "WHERE login_id = ?";
    }

    private String getAuthoritiesQuery() {
        return "SELECT login_id AS username, group_id AS authority "
                + "FROM hms_user "
                + "WHERE login_id = ?";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/logout/**").permitAll()
                //.antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .failureUrl("/error")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/");
    }
}
