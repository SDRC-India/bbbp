package org.sdrc.bbbp.models;

import java.util.Collection;
import java.util.Set;

import org.sdrc.bbbp.domain.UserAreaMapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Azaruddin(azaruddin@sdrc.co.in)
 *
 */

@JsonAutoDetect
public class UserModel extends User {

	private static final long serialVersionUID = 3070385867750194519L;

	// add all extra required details for your application
//	@JsonIgnore
	private long userLoginMetaId;

	private Set<String> roles;
	
	private Set<Integer> roleIds;
	
	private Integer userId;
	
	private String uuId;
	
	private String passwordHashInCurrentSession;
	
	@JsonIgnore
	Collection<UserAreaMapping> areaMappings;
	// Using private constructor to force initialization of extra parameters
	private UserModel(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public UserModel(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer userLoginMetaId,Collection<UserAreaMapping> areaMappings,Set<String> roles,Integer userId,Set<Integer> roleIds,String uuId,String passwordHash) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.areaMappings = areaMappings;
		this.userLoginMetaId = userLoginMetaId;
		this.roles = roles;
		this.userId = userId;
		this.roleIds = roleIds;
		this.uuId=uuId;
		this.passwordHashInCurrentSession = passwordHash;
	}

	public long getUserLoginMetaId() {
		return userLoginMetaId;
	}

	public void setUserLoginMetaId(long userLoginMetaId) {
		this.userLoginMetaId = userLoginMetaId;
	}

	

	public Collection<UserAreaMapping> getAreaMappings() {
		return areaMappings;
	}

	public void setAreaMappings(Collection<UserAreaMapping> areaMappings) {
		this.areaMappings = areaMappings;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Set<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getPasswordHashInCurrentSession() {
		return passwordHashInCurrentSession;
	}

	public void setPasswordHashInCurrentSession(String passwordHashInCurrentSession) {
		this.passwordHashInCurrentSession = passwordHashInCurrentSession;
	}

	@Override
	public String toString() {
		return "UserModel [userLoginMetaId=" + userLoginMetaId + ", roles=" + roles + ", roleIds=" + roleIds
				+ ", userId=" + userId + ", uuId=" + uuId + ", passwordHashInCurrentSession="
				+ passwordHashInCurrentSession;
	}

	
	

	

}
