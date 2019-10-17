package org.sdrc.bbbp.utils;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		// if server is being stopped
		if (event instanceof HttpSessionDestroyedEvent) {
			
			
		}
		// if server is starting up
		else if (event instanceof ContextRefreshedEvent) {
			//logged out all users.
		
		}
	}

}
