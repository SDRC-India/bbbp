package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Type implements Serializable {

	/**
	 * Debiprasad (debiprasad@sdrc.co.in)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_pk")
	private Integer id;

	private String name;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_date")
	private Timestamp updatedDate;
	

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

}
