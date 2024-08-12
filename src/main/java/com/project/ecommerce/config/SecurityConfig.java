package com.project.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.ecommerce.security.JwtAuthenticationEntrypoint;
import com.project.ecommerce.security.JwtAuthenticationFilter;
import com.project.ecommerce.security.JwtHelper;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  private JwtHelper jwtHelper;

  @Autowired
  private JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;

  @Autowired
  private JwtAuthenticationFilter jAuthenticationFilter;

  @Autowired
  private UserDetailsService userDetailsService;

  private final String[] PUBLIC_URLS = {
      "/swagger-ui/index.html",
      "/swagger-ui/**",
      "/webjars/**",
      "/swagger-resources/**",
      "/v3/api-docs",
      "/v2/api-docs",
      "/test"

  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security.authorizeHttpRequests(request -> {
      request.requestMatchers(HttpMethod.GET, "/swagger-ui/index.html")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/users")
          .permitAll()
          .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
          .requestMatchers(PUBLIC_URLS)
          .permitAll()
          .requestMatchers(HttpMethod.GET)
          .permitAll()
          .anyRequest()
          .authenticated();
      // .requestMatchers(HttpMethod.POST, "/users")
      // .permitAll()
      // .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

      // .requestMatchers(HttpMethod.GET)

      // .permitAll()
      // .requestMatchers(HttpMethod.POST, "/auth/genrate-token").permitAll()
      // .anyRequest()
      // .authenticated();
    });

    // security.httpBasic(Customizer.withDefaults());
    security.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntrypoint));
    security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    security.addFilterBefore(jAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return security.build();
  }
  // @Bean
  // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
  // Exception {

  // http.csrf(csrf -> csrf.disable())
  // .cors(cors -> cors.disable())
  // .authorizeHttpRequests(auth -> auth.requestMatchers("/**").authenticated())
  // .httpBasic(Customizer.withDefaults());
  // return http.build();
  // }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
    return builder.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  // @Bean
  // public UserDetailsService userDetailsService() {

  // UserDetails admin =

  // User.builder().username("admin123").password(passwordEncoder().encode("admin123"))
  // .roles("ADMIN")
  // .build();

  // return new InMemoryUserDetailsManager(admin);
  // }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }
}
