package dmytro.bozhor.universitypetprojectwebsite.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static dmytro.bozhor.universitypetprojectwebsite.config.Role.*;
import static dmytro.bozhor.universitypetprojectwebsite.util.EndpointValuesContainer.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(HttpMethod.GET, C_PLUS_PLUS, C_SHARP, JAVA, PYTHON)
                        .hasAnyAuthority(USER.getAuthority(), ADMIN.getAuthority())
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage(LOGIN)
                        .loginProcessingUrl(LOGIN)
                        .defaultSuccessUrl(HOME, true)
                        .usernameParameter(SecurityUtil.USERNAME_PARAMETER)
                        .passwordParameter(SecurityUtil.PASSWORD_PARAMETER)
                        .failureUrl(SecurityUtil.FAILURE_URL)
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl(LOGOUT)
                        .logoutSuccessUrl(HOME))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

// TODO: add logout button
// TODO: add "already have an account? -> login"
// TODO: add "dont have an account? -> register"

// TODO: add email validation (error if already exists)

// TODO: Google Authorization
// TODO: JWT token
// TODO: OAuth2.0
