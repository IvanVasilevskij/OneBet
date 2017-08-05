package ru.onebet.exampleproject.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.onebet.exampleproject.Authenticator;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/css/**", "/img/**", "/fonts/**", "/free").permitAll()
                        .antMatchers("/pages/admin/**", "/pages/user/**").hasRole("ROLE_ADMIN")
                        .antMatchers("/pages/client/**", "/pages/user/**").hasRole("ROLE_CLIENT")
                        .antMatchers("/pages/withoutrole/**", "/signin", "/login").anonymous()
                .and()
                    .formLogin()
                        .loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/free/access-denied-page")
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder, Authenticator authenticator) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(authenticator);

        return provider;
    }
}
