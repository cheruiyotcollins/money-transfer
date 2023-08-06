package com.pezesha.moneytransfer.config;


import com.pezesha.moneytransfer.security.JwtAuthenticationEntryPoint;
import com.pezesha.moneytransfer.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter){
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((authorize) ->
                                       //Swagger access
                               authorize.requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/**").permitAll()
                                       //Auth access
                                .requestMatchers("/user/auth/signup").permitAll()
                                .requestMatchers("/user/auth/signin").permitAll()
                                .requestMatchers("/user/auth/update").hasAuthority("ADMIN")
                                 .requestMatchers("/user/auth/add").hasAuthority("ADMIN")
                                .requestMatchers("/user/auth/findById/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/auth/list/all").hasAuthority("ADMIN")
                                .requestMatchers("/user/auth/deleteById/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/auth/new/role").hasAuthority("ADMIN")
                                .requestMatchers("/user/auth/current").hasAnyAuthority("ADMIN","USER")
                                      //Account access
                                .requestMatchers("/api/accounts/new").hasAuthority("ADMIN")
                                .requestMatchers("/api/accounts/list").hasAuthority("ADMIN")
                                 .requestMatchers("/api/accounts/type/new").hasAuthority("ADMIN")
                                .requestMatchers("/api/accounts/find/**").hasAnyAuthority("ADMIN","USER")
                                       //Customer access
                                .requestMatchers("/api/customers/list").hasAuthority("ADMIN")
                                 .requestMatchers("/api/customers/find/**").hasAnyAuthority("ADMIN","USER")
                                       //Transaction access
                                .requestMatchers("/api/transactions/new").hasAnyAuthority("ADMIN","USER")
                                .requestMatchers("/api/transactions/list").hasAuthority("ADMIN")
                                .requestMatchers("/api/transactions/find/**").hasAnyAuthority("ADMIN","USER")
                                .anyRequest().authenticated()

                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
}


}
