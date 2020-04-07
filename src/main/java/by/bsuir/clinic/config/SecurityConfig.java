package by.bsuir.clinic.config;

import by.bsuir.clinic.security.RestAuthenticationEntryPoint;
import by.bsuir.clinic.security.jwt.JwtConfigurer;
import by.bsuir.clinic.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@ComponentScan({ "by.bsuir.clinic.**" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private RestAuthenticationEntryPoint entryPoint;

    private static final String ADMIN_ENDPOINT = "/panel/**";
    private static final String LOGIN_ENDPOINT = "/auth/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, RestAuthenticationEntryPoint entryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.entryPoint = entryPoint;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider, entryPoint));
    }

    @Override
    public void configure(WebSecurity web)
            throws Exception {
        web.ignoring().antMatchers(LOGIN_ENDPOINT);
    };
}
