package com.hopin.HopIn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hopin.HopIn.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // <-- Obavezno za @PreAuthorize
public class WebSecurityConfiguration {
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() // csrf->disabled, pošto nam JWT odrađuje zaštitu od CSRF napada
				.requestMatchers("/*").permitAll()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // ne koristimo HttpSession i kukije
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT procesiramo pre autentikacije

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();// PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//System.out.println(encoder.encode("admin"));
		return encoder;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
