package com.example.kiemtra1;

import com.example.kiemtra1.Repo.MemberAccountRepo;
//import com.example.kiemtra1.Service.ProductService;
//import com.example.kiemtra1.WebRocket.WebSocketApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class Kiemtra1Application {
	private final static Logger logger = LoggerFactory.getLogger(Kiemtra1Application.class);

	private  final MemberAccountRepo memberAccountRepo;
	public Kiemtra1Application(MemberAccountRepo memberAccountRepo) {
		this.memberAccountRepo = memberAccountRepo;
	}
	public static void main(String[] args) {
		SpringApplication.run(Kiemtra1Application.class, args);
//		String serverUrl = "ws://localhost:8080/product"; // Điều chỉnh URL máy chủ WebSocket
//		WebSocketApiClient client = new WebSocketApiClient(URI.create(serverUrl));
//		client.connect();
	}
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> (UserDetails) memberAccountRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}


