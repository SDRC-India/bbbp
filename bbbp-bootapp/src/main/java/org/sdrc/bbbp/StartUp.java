package org.sdrc.bbbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableGlobalMethodSecurity(prePostEnabled=true)
//@EnableScheduling
@PropertySource(value = { "classpath:messages.properties", "classpath:notification.properties", "classpath:quartzconfiguration.properties" })
public class StartUp extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartUp.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(StartUp.class, args);
	}

	/**
	 * Create a CacheManager implementation class to be used by Spring where <code>@Cacheable</code> annotations are
	 * applied.
	 * 
	 * @return A CacheManager instance.
	 */
	Cache transactionAwareUserCache( CacheManager cacheManager ) {
		return new TransactionAwareCacheDecorator( cacheManager.getCache("cmsdata") );
	}
	
	@Bean
	public MessageDigestPasswordEncoder passwordEncoder() {
		return new MessageDigestPasswordEncoder("SHA1");
	}
	
	
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
		
	}
	
	@Bean
	public MessageSource notificationSource() {
		ReloadableResourceBundleMessageSource notificationSource = new ReloadableResourceBundleMessageSource();
		notificationSource.setBasename("classpath:notification");
		notificationSource.setDefaultEncoding("UTF-8");
		
		return notificationSource;
		
	}
	
}
