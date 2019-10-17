package org.sdrc.bbbp.cms.domain;

import java.io.Serializable;
import java.util.List;

public class CmsDataJson implements Serializable{

	private List<Language> hindi;
	private List<Language> english;
	
	public List<Language> getHindi() {
		return hindi;
	}
	public void setHindi(List<Language> hindi) {
		this.hindi = hindi;
	}
	public List<Language> getEnglish() {
		return english;
	}
	public void setEnglish(List<Language> english) {
		this.english = english;
	}

	
}
