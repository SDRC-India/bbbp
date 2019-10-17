/**
 * 
 */
package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.sdrc.bbbp.dashboard.core.IcSectorType;
import org.sdrc.bbbp.domain.TypeDetail;
/**
 * @author debiprasad (debiprasad@sdrc.co.in)
 * 
 *
 */
@Entity
public class UtIndicatorClassificationsEn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IC_NId;

	@Lob
	private String IC_Info;

	private String IC_Name;

	private int IC_Order;

	@JoinColumn(name = "IC_Parent_NId")
	@ManyToOne
	private UtIndicatorClassificationsEn IC_Parent_NId;

	private String IC_Short_Name;

	private String IC_Type;
	
	

	@JoinColumn(name = "checklist_type_detail_id_fk")
	@ManyToOne
	private TypeDetail typeDetail;

//	@OneToMany(mappedBy = "IC_Parent_NId")
//	private List<UtIndicatorClassificationsEn> childrenSector;
	
	@Enumerated(EnumType.STRING)
	private IcSectorType icSectorType;	
	
	public UtIndicatorClassificationsEn(){
		super();
	}

	public UtIndicatorClassificationsEn(int IC_NId) {
		this.IC_NId=IC_NId;
	}

	public int getIC_NId() {
		return IC_NId;
	}

	public void setIC_NId(int iC_NId) {
		IC_NId = iC_NId;
	}

	public String getIC_Info() {
		return IC_Info;
	}

	public void setIC_Info(String iC_Info) {
		IC_Info = iC_Info;
	}

	public String getIC_Name() {
		return IC_Name;
	}

	public void setIC_Name(String iC_Name) {
		IC_Name = iC_Name;
	}

	public int getIC_Order() {
		return IC_Order;
	}

	public void setIC_Order(int iC_Order) {
		IC_Order = iC_Order;
	}

	public UtIndicatorClassificationsEn getIC_Parent_NId() {
		return IC_Parent_NId;
	}

	public void setIC_Parent_NId(UtIndicatorClassificationsEn iC_Parent_NId) {
		IC_Parent_NId = iC_Parent_NId;
	}

	public String getIC_Short_Name() {
		return IC_Short_Name;
	}

	public void setIC_Short_Name(String iC_Short_Name) {
		IC_Short_Name = iC_Short_Name;
	}

	public String getIC_Type() {
		return IC_Type;
	}

	public void setIC_Type(String iC_Type) {
		IC_Type = iC_Type;
	}

	/*public List<UtIndicatorClassificationsEn> getChildrenSector() {
		return childrenSector;
	}

	public void setChildrenSector(List<UtIndicatorClassificationsEn> childrenSector) {
		this.childrenSector = childrenSector;
	}*/

	public TypeDetail getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(TypeDetail typeDetail) {
		this.typeDetail = typeDetail;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	private Integer level;
	
	public IcSectorType getIcSectorType() {
		return icSectorType;
	}

	public void setIcSectorType(IcSectorType icSectorType) {
		this.icSectorType = icSectorType;
	}

}
