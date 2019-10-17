package org.sdrc.bbbp.cms.controller;

import java.util.List;

import org.sdrc.bbbp.cms.service.ContactUsService;
import org.sdrc.bbbp.models.ContactUsModel;
import org.sdrc.bbbp.models.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactUsController {

	@Autowired
	private ContactUsService contactUsService;
	
	@GetMapping("/getOrganisationType")
	public List<ValueObject> fetchOrganisationType(){
		
		return contactUsService.fetchOrganisationType();
	}
	
	@PostMapping("/saveContactUs")
	public Boolean contactUs(@RequestBody ContactUsModel contactUsModel) throws Exception{
		return contactUsService.saveContactUs(contactUsModel);
	}
	
}
