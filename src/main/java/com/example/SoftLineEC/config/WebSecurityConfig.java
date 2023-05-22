package com.example.SoftLineEC.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.List;
/**
 * Конфигурационный класс для настройки безопасности веб-приложения.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Источник данных для аутентификации пользователей.
     */
    @Autowired
    private DataSource dataSource;
    /**
     * Создает и возвращает объект кодировщика паролей для проверки паролей пользователей.
     * @return объект кодировщика паролей.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
    /**
     * Настраивает менеджер аутентификации пользователей.
     * @param auth менеджер аутентификации пользователей.
     * @throws Exception если произошла ошибка при настройке менеджера аутентификации.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(getPasswordEncoder())
                    .usersByUsernameQuery("select username, password, active from user where username =?")
                    .authoritiesByUsernameQuery("select u.username, ur.roles from user u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }
    /**
     * Настраивает HTTP-безопасность приложения.
     * @param http объект конфигурации HTTP-безопасности.
     * @throws Exception если произошла ошибка при настройке HTTP-безопасности.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/registration", "/login","/accessRecovery","/user-data-recovery","/user-data-recovery-code-send","/user-data-confirmation-message","/user-data-password-send","/main","/images/**","/fonts/**","/js/**","/blocks/**","/css/**").permitAll()
                .anyRequest().authenticated().and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/main").failureUrl("/login?error=true").permitAll().and().logout().permitAll()
                .and().csrf().disable().cors().disable();

    }
}
