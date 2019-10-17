package org.sdrc.bbbp.cms.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyContactRoleModel implements Serializable{

	private String section;
	private KeyContactModel[] keyContacts;
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public KeyContactModel[] getKeyContacts() {
		return keyContacts;
	}
	public void setKeyContacts(KeyContactModel[] keyContacts) {
		this.keyContacts = keyContacts;
	}
	
	
}
