package ru.onebet.exampleproject.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.onebet.exampleproject.Authenticator;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                    .authorizeRequests()
                        .antMatchers("/").permitAll()

                        .antMatchers("/users",
                                "/updateAdminDetails",
                                "/toUpdateAdminDetails",
                                "/enterAdminPrivateRoom").hasRole("ADMIN")
                        .antMatchers("/createNewAdmin",
                                "/toCreateNewAdmin").hasRole("ADMINROOT")
                        .antMatchers("/toCreateClientPage",
                                "/createClient",
                                "/updateClientDetails",
                                "/toUpdateClientDetails",
                                "/enterClientPrivateRoom").hasRole("CLIENT")

                        .antMatchers("/toLoginPage",
                                "/signin").anonymous()

                        .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false)
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder, Authenticator authenticator) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(authenticator);

        return provider;
    }
}