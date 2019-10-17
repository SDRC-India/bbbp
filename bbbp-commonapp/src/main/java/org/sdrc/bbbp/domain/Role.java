package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 262708472299623890L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "description")
	private String description;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "last_updated_date")
	private Timestamp lastUpdatedDate;

	

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(int roleId) {
		this.roleId=roleId;
	}
	
}
