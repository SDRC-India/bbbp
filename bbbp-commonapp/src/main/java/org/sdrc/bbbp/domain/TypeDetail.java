package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@JsonAutoDetect
public class TypeDetail implements Serializable {

	/**
	 * Debiprasad (debiprasad@sdrc.co.in)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_pk")
	private Integer id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "type_id_fk")
	private Type typeId;
	
	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	@Column(name = "order_level")
	private Integer orderLevel;
	
	/*@OneToMany(mappedBy = "typeDetail")
	private List<Question> question;*/
	
		

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getTypeId() {
		return typeId;
	}

	public void setTypeId(Type typeId) {
		this.typeId = typeId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(Integer orderLevel) {
		this.orderLevel = orderLevel;
	}
	
	public TypeDetail() {

	}

	public TypeDetail(Integer id) {
		this.id = id;
	}

	/*public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}*/

}
