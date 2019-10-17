package org.sdrc.bbbp.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RolePeriodicityMapping implements Serializable {
	
	
	/**
	 * Debiprasad(debiprasad@sdrc.co.in) 16-03-2018 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_periodicity_mapping_id")
	private Integer rolePeriodicityMappingId;
	
	
	@ManyToOne
	@JoinColumn(name="role_id_fk")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="periodicity_id_fk")
	private Periodicity periodicity;
	

	public Integer getRolePeriodicityMappingId() {
		return rolePeriodicityMappingId;
	}

	public void setRolePeriodicityMappingId(Integer rolePeriodicityMappingId) {
		this.rolePeriodicityMappingId = rolePeriodicityMappingId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Periodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Periodicity periodicity) {
		this.periodicity = periodicity;
	}
	
	

}
