// package com.dawayo.packing.Controller;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// import org.springframework.security.config.ann0otation.web.configurers.LogoutConfigurer;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.RememberMeServices;
// import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

// import jakarta.servlet.http.HttpSession;
// import lombok.RequiredArgsConstructor;

// @EnableWebSecurity
// @RequiredArgsConstructor
// @Configuration
// public class SecurityConfig {
  
//     @Bean
//     @Order(2)
//     public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .securityMatcher("/**") // 모든 요청
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/admin/**").denyAll() // admin 경로는 여기선 deny
//                 .anyRequest().permitAll()
//             )
            
//             .formLogin(form -> form
//                 .loginPage("/login")
//                 .loginProcessingUrl("/loginProcess")
//                 .successHandler(successHandler) 
//                 .failureUrl("/login?error=true")
//                 .permitAll()
//             )
//             .logout(logout -> logout
//                 .logoutUrl("/logout")
//                 .logoutSuccessUrl("/")
//                 .invalidateHttpSession(true)
//                 .addLogoutHandler((request, response, authentication) -> {
//                 HttpSession session = request.getSession();
//                 session.invalidate();
//                     })
//             .logoutSuccessHandler((request, response, authentication) ->
//                 response.sendRedirect("/ "))
//             );

//         return http.build();
//     }

//     @Bean
//     @Order(1) // 어드민 필터 체인이 먼저 적용되도록
//     public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
            
//             .securityMatcher("/admin/**") // /admin 이하 경로만 처리
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/admin/login", "/admin/loginProcess").permitAll()
//                 .anyRequest().hasAuthority("ROLE_ADMIN")
             
//             )
//             .formLogin(form -> form
//                 .loginPage("/admin/login")
//                 .loginProcessingUrl("/admin/loginProcess")
//                 .successHandler(successHandler) 
//                 .failureUrl("/admin/login?error=true")
//                 .permitAll()
//             )
//             .csrf((csrf) -> csrf.disable());

//         return http.build();
//     }
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
//         TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
//         TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("rememberMeKey", userDetailsService, encodingAlgorithm);
//         rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
//         return rememberMe;
//     }


// }
