package org.sdrc.bbbp.cms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;

@Entity
@TypeDefs({
	  @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
	})
@Table( name = "archive_cms_data")
public class ArchiveCmsData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cms_data_id")
	private Long id;
	
	//page name
	@Column(length=200)
	private String viewName;
	
	//page template 
	@Column(length=1500)
	private String viewType;
	
	//page content
	//CmsDataJson 
	@Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
	private JsonNode viewContent;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedDate;
	
//	@PreUpdate
//	private void onUpdate() {
//		updatedDate = new Date();
//	}

	public String getViewName() {
		return viewName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public JsonNode getViewContent() {
		return viewContent;
	}

	public void setViewContent(JsonNode viewContent) {
		this.viewContent = viewContent;
	}


}
