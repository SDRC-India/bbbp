package org.sdrc.bbbp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class LoginAudit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	@ManyToOne
	@JoinColumn(name="user_id_fk")
	private User user;
	
	private Date loggedInDate;
	
	private Date logoutDate;
	
	private boolean active;
	
	private String sessionId;
	
	private String userAgent;
	
	private String actualUserAgent;
	
	private String ipAddress;

	public LoginAudit(Long id) {
		this.id=id;
	}
	
	public LoginAudit() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoggedInDate() {
		return loggedInDate;
	}

	public void setLoggedInDate(Date loggedInDate) {
		this.loggedInDate = loggedInDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActualUserAgent() {
		return actualUserAgent;
	}

	public void setActualUserAgent(String actualUserAgent) {
		this.actualUserAgent = actualUserAgent;
	}

	
	
	
	
}
