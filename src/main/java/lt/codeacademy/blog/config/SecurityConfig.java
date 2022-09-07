package lt.codeacademy.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
//                .inMemoryAuthentication()
//                .withUser("admin")
//                      .password(encoder.encode("admin")).roles("ADMIN")
//                .password("{bcrypt}$2a$10$JM11cOpmVZMhEIjwp4gfTuztM2YUEs7FbWJYrpG6pLDEk6NYib/TO").roles("ADMIN")
//                .and()
//                .withUser("user")
////                      .password(encoder.encode("user")).roles("USER");
//                .password("{bcrypt}$2a$10$eL1q3ek7KbWBAo2tRr.IueMSxy5P.7qtkUcHELSVPdeTlN1CLny6u").roles("USER")
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, TRUE AS enabled FROM blog_user WHERE username = ?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT username, authority FROM blog_user WHERE username = ?"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/main").permitAll()
//                .and().authorizeRequests().antMatchers("/login", "logout").permitAll()
                .and().authorizeRequests().antMatchers("/static/**").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/main").permitAll()
                .and().logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
