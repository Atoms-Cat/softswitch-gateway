//package com.atomscat.freeswitch.xml.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
//
///**
// * fs xml curl, setting auth-scheme with digest
// * @author th158
// */
//@ConditionalOnProperty(value = "com.atomscat.freeswitch.xml-curl.digest-enable")
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Value("${com.atomscat.freeswitch.xml-curl.credentials.username:#{null}}")
//    private String username;
//
//    @Value("${com.atomscat.freeswitch.xml-curl.credentials.password:#{null}}")
//    private String password;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilter(digestAuthenticationFilter()).exceptionHandling().authenticationEntryPoint(digestEntryPoint()).and().httpBasic().and().authorizeRequests().antMatchers("/xml/curl").permitAll().anyRequest().authenticated();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser(this.username).password(new BCryptPasswordEncoder().encode(this.password)).roles("USER");
//    }
//
//    DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
//        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
//        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
//        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
//        return digestAuthenticationFilter;
//    }
//
//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(User.withUsername(this.username).password(this.password).roles("USER").build());
//        return inMemoryUserDetailsManager;
//    }
//
//    @Bean
//    DigestAuthenticationEntryPoint digestEntryPoint() {
//        DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
//        bauth.setRealmName("Digest Realm");
//        bauth.setKey("SecureKey");
//        return bauth;
//    }
//
//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        return authenticationManager();
//    }
//}