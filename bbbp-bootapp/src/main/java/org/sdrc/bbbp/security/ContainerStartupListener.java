package org.sdrc.bbbp.security;

import java.util.List;

import org.sdrc.bbbp.domain.LoginAudit;
import org.sdrc.bbbp.repository.LoginAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Subham Ashish (subham@sdrc.co.in)
 * 
 * @Description It runs on container startup and makes all the active status to false in loginaudit table.
 */

@Component
public class ContainerStartupListener {

	@Autowired
	LoginAuditRepository loginAuditRepository;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

		List<LoginAudit> auditList = loginAuditRepository.findByActiveTrue();

		if(!auditList.isEmpty()){
			
			auditList.forEach(audit -> {
				audit.setActive(false);
			});
			
			loginAuditRepository.save(auditList);
		}
		
	}
	
}
