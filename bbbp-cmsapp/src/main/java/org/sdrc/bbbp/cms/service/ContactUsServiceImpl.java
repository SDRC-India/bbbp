package org.sdrc.bbbp.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sdrc.bbbp.cms.repository.ContactUsRepository;
import org.sdrc.bbbp.domain.ContactUs;
import org.sdrc.bbbp.domain.TypeDetail;
import org.sdrc.bbbp.models.ContactUsModel;
import org.sdrc.bbbp.models.MailModel;
import org.sdrc.bbbp.models.ValueObject;
import org.sdrc.bbbp.repository.TypeDetailRepository;
import org.sdrc.bbbp.service.MailService;
import org.sdrc.bbbp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 02-Aug-2018 4:56:13 PM
 * This service is exclusive for contact us module
 */
@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	private ContactUsRepository contactUsRepository;

	@Autowired
	private TypeDetailRepository typeDetailRepository;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private MailService mailService;

	@Override
	public List<ValueObject> fetchOrganisationType() {

		List<TypeDetail> typeDetails = typeDetailRepository
				.findByTypeIdId(Integer.parseInt(messageSource.getMessage(Constants.ORGANISATION_TYPE_ID, null, null)));
		List<ValueObject> valueObjects = new ArrayList<>();

		typeDetails.forEach(typeDetail -> {
			valueObjects.add(new ValueObject(typeDetail.getId(), typeDetail.getName()));
		});

		return valueObjects;
	}
	
	@Override
	@Transactional
	public Boolean saveContactUs(ContactUsModel contactUsModel) throws Exception {
		
		ContactUs contactUs = new ContactUs();
		
		contactUs.setContactNumber(contactUsModel.getContactNumber());
		contactUs.setDesignation(contactUsModel.getDesignation());
		contactUs.setEmail(contactUsModel.getEmail());
		contactUs.setFeedback(contactUsModel.getFeedback());
		contactUs.setName(contactUsModel.getName());
		contactUs.setOrganisation(contactUsModel.getOrganisation());
		contactUs.setOrganisationType(contactUsModel.getOrganisationTypeId()!=null ? new TypeDetail(contactUsModel.getOrganisationTypeId()) : null);
		
		MailModel mail=new MailModel(); 
		mail.setToEmailIds(Arrays.asList(messageSource.getMessage(Constants.CONTACT_US_TO_EMAIL, null, null)));
		mail.setToUserName("Ministry of Women and Child Development, Government of India");
		mail.setSubject("Feedback Submission");
		mail.setMessage("<html><body> You have got a new feedback from "+"<b> Mr./Mrs.&nbsp;"+contactUsModel.getName()+". </b><br><br>  "
				+ "<br> Email: "
				+"<b>"+ contactUsModel.getEmail()
				+ "</b><br> Phone: "
				+"<b>"+ (contactUsModel.getContactNumber() == "" ? "Not Available" : contactUsModel.getContactNumber())
				+"</b><br> Designation: "
				+"<b>"+ (contactUsModel.getDesignation() == null ? "Not Available" : contactUsModel.getDesignation())
				+ "</b><br> Organisation: "
				+"<b>"+ (contactUsModel.getOrganisation() == null ? "Not Available" : contactUsModel.getOrganisation())
				+"</b><br> Organisation Type: "
				+"<b>"+ (contactUsModel.getOrganisationType() == null ? "Not Available" : contactUsModel.getOrganisationType())
				+ "</b><br> Feedback: "
				+"<b>"+ contactUsModel.getFeedback() + "</b></body></html>");
		mail.setFromUserName("Team BBBP");
		
		
		mailService.sendMail(mail);
		
		contactUsRepository.save(contactUs);
		
		return true;
	}

}
