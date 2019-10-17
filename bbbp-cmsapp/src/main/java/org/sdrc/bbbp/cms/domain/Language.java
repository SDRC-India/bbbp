package org.sdrc.bbbp.cms.domain;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Language implements Serializable{

	private String section;
	private String key;
	
	@Type(type = "jsonb")
	private List<DataModel> data;
	private Integer order;
	private String url;
	//this model is specific to District level ley contacts
	private List<KeyContactRoleModel> keyContactRoles;
	
	//this model is specific to National and state level key contacts
	private List<KeyContactModel> keyContacts;
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public List<DataModel> getData() {
		return data;
	}
	public void setData(List<DataModel> data) {
		this.data = data;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<KeyContactRoleModel> getKeyContactRoles() {
		return keyContactRoles;
	}
	public void setKeyContactRoles(List<KeyContactRoleModel> keyContactRoles) {
		this.keyContactRoles = keyContactRoles;
	}
	public List<KeyContactModel> getKeyContacts() {
		return keyContacts;
	}
	public void setKeyContacts(List<KeyContactModel> keyContacts) {
		this.keyContacts = keyContacts;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
