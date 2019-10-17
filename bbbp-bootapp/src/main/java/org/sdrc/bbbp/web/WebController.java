package org.sdrc.bbbp.web;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.exceptions.UUIDAuthenticationException;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.repository.UserRepository;
import org.sdrc.bbbp.service.ConfigurationService;
import org.sdrc.bbbp.service.JobService;
import org.sdrc.bbbp.service.MobileUserAuthenticationService;
import org.sdrc.bbbp.service.RestExceptionHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebController {

	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	RestExceptionHandlerService restExceptionHandlerService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	MobileUserAuthenticationService mobileUserAuthenticationService;
	
	private Logger log = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping("/user")
	public UserModel login() {
		return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@RequestMapping("/mobileUser")
	public UserModel mobileUserLogin(@RequestParam("uuid") String uuid) {
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User existUUID = userRepository.findByUuid(uuid);
		if (existUUID == null) {//when existing uuid is not present 
			if (uuid.equals(user.getUuId()) || user.getUuId() == null) {//when user is a newuse or uuid is equals to user uuid
				User userDomain =null;
				if (user.getUuId() == null) {//when newuser ,uuid persist into DB
					userDomain = userRepository.findByUserId(user.getUserId());
					userDomain.setUuId(uuid);
					userRepository.save(userDomain);
				}
				if(userDomain!=null) {
					mobileUserAuthenticationService.authenticateUserAgainstPasswordChange();
				}
				return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}else{
				throw new UUIDAuthenticationException("This user is already logged in from other device");
			}
		} else {
			if (existUUID.getUserName().equals(user.getUsername())) {//when existing uuid user is same as user logged in
				mobileUserAuthenticationService.authenticateUserAgainstPasswordChange();
				return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			} else {
				throw new UUIDAuthenticationException("Another user is already logged in this device, kindly use another device");
			}
		} 
	}
	
	@RequestMapping("/testException")
	public void exception() {
		
		try {
			int i = 1/0;
		}catch(Exception e) {
			log.error("Exception ",e);
		}
	}
	
	@RequestMapping("/getRawDataReport")
	public ResponseEntity<InputStreamResource>  getRawDataReport(@RequestParam("stateId")String stateId, 
								@RequestParam("districtId")String districtId,
								/*@RequestParam("yearId")Integer yearId, */HttpServletResponse response, HttpServletRequest request) {
		String filePath = "";
		try {
			filePath = jobService.dailyMailReportJob(stateId, districtId/*, yearId*/);
			File file = new File(filePath);

			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("Content-Disposition", "attachment; filename=" + file.getName());
			InputStreamResource isr = new InputStreamResource(new FileInputStream(file));

			file.delete();
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
