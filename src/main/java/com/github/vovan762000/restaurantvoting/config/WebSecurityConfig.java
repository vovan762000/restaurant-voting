package com.github.vovan762000.restaurantvoting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vovan762000.restaurantvoting.security.JwtConfigurer;
import com.github.vovan762000.restaurantvoting.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private void setMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

    private final JwtConfigurer jwtConfigurer;

    public WebSecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/profile").anonymous()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
    }

//
//    private final UserRepository userRepository;
//

//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(
//                        email -> {
//                            log.debug("Authenticating '{}'", email);
//                            Optional<User> optionalUser = userRepository.getByEmail(email);
//                            return new AuthUser(optionalUser.orElseThrow(
//
//                                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
//                        })
//                .passwordEncoder(PASSWORD_ENCODER);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.POST, "/api/profile").anonymous()
//                .antMatchers("/api/**").authenticated()
//                .and().httpBasic()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().csrf().disable();
//    }
}
