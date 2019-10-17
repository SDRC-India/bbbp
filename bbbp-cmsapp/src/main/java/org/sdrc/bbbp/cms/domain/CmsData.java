package org.sdrc.bbbp.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * 13-Jul-2018 4:13:48 PM
 * This domain is for CMS data and will hold all page details in a jsonb format
 */
@Entity
@TypeDefs({
  @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
//  @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table( name = "cms_data")
//FUNCTION public.update_changetimestamp_updatedate() gets trigger to update timestamp
public class CmsData implements Serializable{


	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 31-Aug-2018 1:18:51 PM
	 */
	private static final long serialVersionUID = 10383792646697326L;

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
	
	@Column(length=200)
	private String cmsType;
	
	//page content
	//CmsDataJson 
//	@Type(type="CmsDataJsonType")
	@Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
	private JsonNode viewContent;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedDate;
	
//	private String filePath;
	
//	@PreUpdate
//	private void onUpdate() {
//		updatedDate = new Date();
//	}
	
	private String feature;
	
	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	public String getViewName() {
		return viewName;
	}

	public CmsData() {
		super();
	}

	public CmsData(Long id, String viewName) {
	super();
	this.id = id;
	this.viewName = viewName;
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

	public String getCmsType() {
		return cmsType;
	}

	public void setCmsType(String cmsType) {
		this.cmsType = cmsType;
	}

	
}
