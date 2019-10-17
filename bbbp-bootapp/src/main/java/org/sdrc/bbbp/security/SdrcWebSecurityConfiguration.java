package org.sdrc.bbbp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(1)
@EnableWebSecurity
public class SdrcWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;

	// @Autowired
	// private CustomConcurrentSessionControlAuthenticationStrategy
	// sessionControlStrategy;

	@Autowired
	private void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userAuthenticationProvider);
	}

	/*
	 * To alter any configuration related to WEB-Application please update the
	 * configuration. This Authentication provider internally manages the
	 * authentication creation mechanism, using the UserRoleFeaturePermissionMapping
	 * tables.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/login.html", "/", "/login", "/resources/**", "/pages/**",
						"/api/logout","/session-out")
				.permitAll()
				.antMatchers("/css/**", "/js/**", "/assets/**", "/assets/images/**", "/assets/icons/**",
						"**/favicon.ico", "/api/portal/*","/api/cms/downloadCmsDoc", "/api/contact/*","/timeout","/api/getAllPeriodReference","/api/getAllArea")
				.permitAll().anyRequest().authenticated().and().csrf()
//				.disable();
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		http.headers().frameOptions().disable();
		http.logout().logoutUrl("/api/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID", "XSRF-TOKEN",
				"access_token");
		http.sessionManagement().sessionFixation().newSession().
		maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/session-out").and().invalidSessionUrl("/session-out");
//		http.sessionManagement().sessionAuthenticationStrategy(sessionRegistry());// admin can login only in
																							// single machine / device
		http.sessionManagement().enableSessionUrlRewriting(false); // disable url rewriting, to pass session information
																	// in URL if cookie is disabled.
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.css");
		web.ignoring().antMatchers("/*.js");
		web.ignoring().antMatchers("/*.html");
		web.ignoring().antMatchers("/*.woff2");
		web.ignoring().antMatchers("/assets/*");
		web.ignoring().antMatchers("/*.jpg");
		web.ignoring().antMatchers("/*.ttf");
	}

	/*@Bean
	public CustomConcurrentSessionControlAuthenticationStrategy sessionControlStrategy() {
		return new CustomConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
	}*/

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

}
