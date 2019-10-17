package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 09-Aug-2019 11:02:53 AM
 */
@Entity
@Table( name = "visitorcount")
public class VisitorCount  implements Serializable {

	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 09-Aug-2019 10:58:27 AM
	 */
	private static final long serialVersionUID = -493289708225710502L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long count;
	
	private String ipAddress;
	
	private Date lastVisitedDate;
	
	private String lastSessionid;
	
	private boolean isLogout;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLastVisitedDate() {
		return lastVisitedDate;
	}

	public void setLastVisitedDate(Date lastVisitedDate) {
		this.lastVisitedDate = lastVisitedDate;
	}

	public String getLastSessionid() {
		return lastSessionid;
	}

	public void setLastSessionid(String lastSessionid) {
		this.lastSessionid = lastSessionid;
	}

	public boolean isLogout() {
		return isLogout;
	}

	public void setLogout(boolean isLogout) {
		this.isLogout = isLogout;
	}
	
}
