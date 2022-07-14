package xyz.cglzwz.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.cglzwz.chatroom.service.CustomUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService myCustomUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myCustomUserService());
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("a").password(encoder.encode("123")).roles("USER")
                .and()
                .withUser("b").password(encoder.encode("123")).roles("USER");
    }
    */

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Authorization request
                .authorizeRequests()
                //do not intercept "/login"
                .antMatchers("/login").permitAll()
                //Homepage requires certification
                .antMatchers("/", "/index").authenticated()
                .and()
                .formLogin()
                //Customize the login page, and make sure that the request to submit the form post is also this. . . (my own)
                .loginPage("/login").permitAll()
                .successForwardUrl("/index")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }
}
