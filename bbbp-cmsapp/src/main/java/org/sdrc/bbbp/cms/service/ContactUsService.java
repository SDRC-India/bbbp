package org.sdrc.bbbp.cms.service;

import java.util.List;

import org.sdrc.bbbp.models.ContactUsModel;
import org.sdrc.bbbp.models.ValueObject;

public interface ContactUsService {

	List<ValueObject> fetchOrganisationType();

	Boolean saveContactUs(ContactUsModel contactUsModel) throws Exception;

}
