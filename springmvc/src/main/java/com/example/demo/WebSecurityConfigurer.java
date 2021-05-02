package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        // テストのためパスワードの暗号化はしない
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // spring securityで無視するリクエストパスを設定
        web.ignoring().antMatchers("/css/**", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ログイン処理の認証ルールを設定
        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll() // 認証なしでアクセス可能なパス
                .anyRequest().authenticated() // それ以外は認証が必要
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login") // ログインフォームのアクションに指定したURL[action="@{/login}"]を設定
                .usernameParameter("username") // ログインフォームのユーザー欄のname属性を設定
                .passwordParameter("password") // ログインフォームのパスワード欄のname属性を設定
                .successForwardUrl("/home") // ログイン成功時に遷移するURL
                .failureUrl("/login?error") // ログイン失敗時に遷移するURL
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
