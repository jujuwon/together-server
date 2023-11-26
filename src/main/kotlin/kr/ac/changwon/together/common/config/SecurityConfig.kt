package kr.ac.changwon.together.common.config

import kr.ac.changwon.together.common.auth.JwtRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests { authz ->
                authz
                    .antMatchers(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                    ).permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/health", "/exception").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
