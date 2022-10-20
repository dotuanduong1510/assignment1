package com.planning.HibernateEcommerce.config;

import com.planning.HibernateEcommerce.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class WebSecurityConfig {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Set service to find user in database
		// Set PasswordEncoder.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Login with ROLE_EMPLOYEE or ROLE_MANAGER.
		// If not login, will redirect to /admin/login.
		http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")//
				.access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");

		// Site only for MANAGER
		http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_MANAGER')");

		// When user logs in with role XX.
		// But wants to get access with role YY,
		// AccessDeniedException will be called.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		//Login Form.
		http.authorizeRequests().and().formLogin()//

				// 
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/admin/login")//
				.defaultSuccessUrl("/admin/accountInfo")//
				.failureUrl("/admin/login?error=true")//
				.usernameParameter("userName")//
				.passwordParameter("password")

				// Logout.
				// (After logout, move to home)
				.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");
		return http.build();

	}
}