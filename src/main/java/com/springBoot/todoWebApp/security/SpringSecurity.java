package com.springBoot.todoWebApp.security;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {
	
	@Bean
	public InMemoryUserDetailsManager createUserDeatilsManager() {
		
		UserDetails userDetails = createNewUser("manja", "dummy");
		UserDetails userDetails2 = createNewUser("maha", "dummy");
		return new InMemoryUserDetailsManager(userDetails, userDetails2);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder()
									  .passwordEncoder(passwordEncoder)
									  .username(username)
									  .password(password)
									  .roles("USER","ADMIN")
									  .build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		http.formLogin(withDefaults());    //these 2 are defaults but as we overriding, it needs to be created again.
		http.csrf().disable();
		http.headers().frameOptions().disable();	
		return http.build();
	}
	
}