package by.bsuir.clinic.security.jwt;

import by.bsuir.clinic.security.RestAuthenticationEntryPoint;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtTokenProvider jwtTokenProvider;

    private RestAuthenticationEntryPoint entryPoint;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, RestAuthenticationEntryPoint entryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.entryPoint = entryPoint;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, entryPoint);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
