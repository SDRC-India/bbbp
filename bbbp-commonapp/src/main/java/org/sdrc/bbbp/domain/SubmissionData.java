package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.sdrc.bbbp.models.FileModel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"user_id_fk","year_id_fk","periodreference_id_fk"})
	})
public class SubmissionData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "submission_id")
	private Long submissionId;
	


	@ManyToOne
	@JoinColumn(name = "a01")
	private TypeDetail a01; // typedetails

	@Column(name = "a02")
	private Integer a02;

	@Column(name = "a03")
	private Integer a03;

	@Column(name = "a04")
	private Integer a04;

	@Column(name = "a05")
	private Integer a05;

	@Transient
	private List<FileModel> a06; // filetype
	


	@Column(name = "b01")
	private Integer b01;

	@Column(name = "b02")
	private Integer b02;

	/*@ManyToOne
	@JoinColumn(name = "b03")
	private TypeDetail b03; // typedetails
*/
	@Column(name = "b03")
	private String b03;
	
	@ManyToOne
	@JoinColumn(name = "c01")
	private TypeDetail c01;// typedetails

	@Column(name = "c02")
	private Integer c02;

	@Column(name = "c03")
	private Integer c03;

	@Column(name = "c04")
	private Integer c04;

	@Column(name = "c05")
	private Integer c05;

	@Column(name = "c06")
	private Integer c06;

	@Column(name = "c07")
	private Integer c07;

	@Column(name = "c08")
	private Integer c08;
	
	//Added By Azar
	@Column(name = "c09")
	private Integer c09;

	@Column(name = "d01")
	private Integer d01;

	@Column(name = "d02")
	private Integer d02;

	@Column(name = "d03")
	private Integer d03;

	@Column(name = "d04")
	private Integer d04;

	@Column(name = "d05")
	private Integer d05;

	@Column(name = "e01")
	private Integer e01;

	@Column(name = "e02")
	private Integer e02;

	@Column(name = "e03")
	private Integer e03;


	@Column(name = "f02")
	private Integer f02;

	@Column(name = "f03")
	private Integer f03;
	
	//Added by Azhar
	@Column(name = "f04")
	private Integer f04;
	
	//Added by Azhar
	@Column(name = "f05")
	private Integer f05;
	
	//Added by Azhar
	//Added after consulation with Gayatri from FRS V10
	@Column(name = "f06")
	private Integer f06;

	@ManyToOne
	@JoinColumn(name = "g01")
	private TypeDetail g01; // typedetails

	@Column(name = "g02")
	private Integer g02;

	@Column(name = "g03")
	private Integer g03;

	//Added by Azhar
	@Column(name = "g04")
	private Integer g04;
	
	@ManyToOne
	@JoinColumn(name = "h01")
	private TypeDetail h01; // typedetails

	@ManyToOne
	@JoinColumn(name = "h02")
	private TypeDetail h02; // typedetails

	@Column(name = "h03")
	private Integer h03;

	@ManyToOne
	@JoinColumn(name = "ch04")
	private TypeDetail h04;// typedetails

	@Column(name = "h05")
	private Integer h05;

	@Column(name = "h06")
	private Integer h06;

	@Column(name = "h07")
	private Integer h07;

	//Deleted by Azhar
//	@Column(name = "h08")
//	private Integer h08;

	@Column(name = "i01")
	private Integer i01;

	@Column(name = "i02")
	private Integer i02;
	
	//Added by Azhar
	@Column(name = "i03")
	private Integer i03;

	@Column(name = "j01")
	private Integer j01;

	@Column(name = "j02")
	private Integer j02;

	@Column(name = "j03")
	private Integer j03;

	@Column(name = "j04")
	private Integer j04;

	@Column(name = "j05")
	private Integer j05;

	@Column(name = "j06")
	private Integer j06;

	@Column(name = "j07")
	private Integer j07;

	@Column(name = "j08")
	private Integer j08;

	@Column(name = "j09")
	private Integer j09;
	
	@Transient
	private List<FileModel> j10; // filetype

	@Column(name = "k01")
	private Integer k01;

	@Column(name = "k02")
	private Integer k02;
	
	//Added by Azhar
	@Column(name = "k03")
	private Integer k03;
	
	//Added by Azhar
	@Column(name = "k04")
	private Integer k04;
	
	//Added by Azhar
	@Column(name = "k05")
	private Integer k05;
	
	//Added by Azhar
	@Column(name = "k06")
	private Integer k06;

	@Column(name = "l01")
	private Integer l01;

	@Column(name = "l02")
	private Integer l02;

	@Column(name = "l03")
	private Integer l03;

	@Column(name = "m01")
	private Integer m01;

	@Column(name = "m02")
	private Integer m02;

	@Column(name = "n01")
	private Integer n01;

	@Column(name = "o01")
	private Integer o01;

	@Column(name = "o02")
	private Integer o02;

	@Column(name = "p01")
	private Integer p01;

	@Column(name = "p02")
	private Integer p02;

	@Column(name = "p03")
	private Integer p03;

	@Transient
	private List<FileModel> q01; // filetype

	@Column(name = "r01")
	private Integer r01;

	@Column(name = "s01")
	private Integer s01;

	@Column(name = "t01")
	private Integer t01;

	@Column(name = "u01")
	private Integer u01;

	@Column(name = "u02")
	private Integer u02;

	@Column(name = "u03")
	private Integer u03;

/*	@Column(name = "u04")
	private Integer u04;*/

	@Column(name = "v01")
	private Integer v01;

	@Column(name = "v02")
	private Integer v02;

	@Column(name = "w01")
	private Integer w01;

	@Column(name = "w02")
	private Integer w02;

	@Column(name = "w03")
	private Integer w03;

	@Column(name = "w04")
	private Integer w04;

	@Column(name = "x01")
	private Integer x01;

	@Column(name = "x02")
	private Integer x02;

	@Column(name = "y01")
	private Integer y01;

	@Column(name = "y02")
	private Integer y02;

	//Deleted by Azhar
//	@Column(name = "y03")
//	private Integer y03;

	@Column(name = "z01")
	private Integer z01;

	@Column(name = "z02")
	private Integer z02;
	
	//Added by Azhar
	@Column(name = "z03")
	private Integer z03;
	
	//Added by Azhar
	@Column(name = "z04")
	private Integer z04;
	
	//Added by Azhar
	//Deleted after consulting with Gayatri
//	@Column(name = "z05")
//	private Integer z05;

	@Column(name = "aa01")
	private Integer aa01;
	
//	//Added by Azhar
//Deleted after consulting with Gayatri
//	@Column(name = "ab00")
//	private Integer ab00;


	@Column(name = "ab01")
	private Integer ab01;

	@Column(name = "ab02")
	private Integer ab02;

	@Column(name = "ac01")
	private Integer ac01;

//	@Column(name = "ac02")
//	private Integer ac02;
//
//	@Column(name = "ac03")
//	private Integer ac03;
//
//	@Column(name = "ac04")
//	private Integer ac04;

	@Column(name = "ad01")
	private Integer ad01;

	@Transient
	private TypeDetail ad02; // typedetails

	@Transient
	private List<Map<String, List<FileModel>>> ad03; // filetype

//	@Column(name = "ad04")
//	private Integer ad04;
//
//	@Column(name = "ad05")
//	private Integer ad05;

	@Column(name = "created_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	

    @PrePersist
    private void onCreate() {
    	updatedDate = createdDate = new Date();
    }

    @PreUpdate
    private void onUpdate() {
    	updatedDate = new Date();
    }
    

	public SubmissionData() {

	}

	public SubmissionData(Long submissionId) {
		this.submissionId = submissionId;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_id_fk")
	private User user;

	@ManyToOne
	@JoinColumn(name = "periodreference_id_fk")
	private PeriodReference periodReference;

	@ManyToOne
	@JoinColumn(name = "year_id_fk")
	private Year year;
	
	@Column(name="is_submit")
	private Boolean isSubmit;
	
	@Column(name="file_count")
	private Integer fileCount;
	
	@Column(name="status")
	private String status;
		
	@Column(name="other_value")
	private String othersValue;

	@Column(name="agreeCorrectInformation")
	private Boolean agreeCorrectInformation;

	public Boolean getAgreeCorrectInformation() {
		return agreeCorrectInformation;
	}

	public void setAgreeCorrectInformation(Boolean agreeCorrectInformation) {
		this.agreeCorrectInformation = agreeCorrectInformation;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public TypeDetail getA01() {
		return a01;
	}

	public void setA01(TypeDetail a01) {
		this.a01 = a01;
	}

	public Integer getA02() {
		return a02;
	}

	public void setA02(Integer a02) {
		this.a02 = a02;
	}

	public Integer getA03() {
		return a03;
	}

	public void setA03(Integer a03) {
		this.a03 = a03;
	}

	public Integer getA04() {
		return a04;
	}

	public void setA04(Integer a04) {
		this.a04 = a04;
	}

	public Integer getA05() {
		return a05;
	}

	public void setA05(Integer a05) {
		this.a05 = a05;
	}

	public List<FileModel> getA06() {
		return a06;
	}

	public void setA06(List<FileModel> a06) {
		this.a06 = a06;
	}

	public Integer getB01() {
		return b01;
	}

	public void setB01(Integer b01) {
		this.b01 = b01;
	}

	public Integer getB02() {
		return b02;
	}

	public void setB02(Integer b02) {
		this.b02 = b02;
	}

	public String getB03() {
		return b03;
	}

	public void setB03(String b03) {
		this.b03 = b03;
	}

	public TypeDetail getC01() {
		return c01;
	}

	public void setC01(TypeDetail c01) {
		this.c01 = c01;
	}

	public Integer getC02() {
		return c02;
	}

	public void setC02(Integer c02) {
		this.c02 = c02;
	}

	public Integer getC03() {
		return c03;
	}

	public void setC03(Integer c03) {
		this.c03 = c03;
	}

	public Integer getC04() {
		return c04;
	}

	public void setC04(Integer c04) {
		this.c04 = c04;
	}

	public Integer getC05() {
		return c05;
	}

	public void setC05(Integer c05) {
		this.c05 = c05;
	}

	public Integer getC06() {
		return c06;
	}

	public void setC06(Integer c06) {
		this.c06 = c06;
	}

	public Integer getC07() {
		return c07;
	}

	public void setC07(Integer c07) {
		this.c07 = c07;
	}

	public Integer getC08() {
		return c08;
	}

	public void setC08(Integer c08) {
		this.c08 = c08;
	}

	public Integer getC09() {
		return c09;
	}

	public void setC09(Integer c09) {
		this.c09 = c09;
	}

	public Integer getD01() {
		return d01;
	}

	public void setD01(Integer d01) {
		this.d01 = d01;
	}

	public Integer getD02() {
		return d02;
	}

	public void setD02(Integer d02) {
		this.d02 = d02;
	}

	public Integer getD03() {
		return d03;
	}

	public void setD03(Integer d03) {
		this.d03 = d03;
	}

	public Integer getD04() {
		return d04;
	}

	public void setD04(Integer d04) {
		this.d04 = d04;
	}

	public Integer getD05() {
		return d05;
	}

	public void setD05(Integer d05) {
		this.d05 = d05;
	}

	public Integer getE01() {
		return e01;
	}

	public void setE01(Integer e01) {
		this.e01 = e01;
	}

	public Integer getE02() {
		return e02;
	}

	public void setE02(Integer e02) {
		this.e02 = e02;
	}

	public Integer getE03() {
		return e03;
	}

	public void setE03(Integer e03) {
		this.e03 = e03;
	}



	public Integer getF02() {
		return f02;
	}

	public void setF02(Integer f02) {
		this.f02 = f02;
	}

	public Integer getF03() {
		return f03;
	}

	public void setF03(Integer f03) {
		this.f03 = f03;
	}

	public Integer getF04() {
		return f04;
	}

	public void setF04(Integer f04) {
		this.f04 = f04;
	}

	public Integer getF05() {
		return f05;
	}

	public void setF05(Integer f05) {
		this.f05 = f05;
	}

	public TypeDetail getG01() {
		return g01;
	}

	public void setG01(TypeDetail g01) {
		this.g01 = g01;
	}

	public Integer getG02() {
		return g02;
	}

	public void setG02(Integer g02) {
		this.g02 = g02;
	}

	public Integer getG03() {
		return g03;
	}

	public void setG03(Integer g03) {
		this.g03 = g03;
	}

	public Integer getG04() {
		return g04;
	}

	public void setG04(Integer g04) {
		this.g04 = g04;
	}

	public TypeDetail getH01() {
		return h01;
	}

	public void setH01(TypeDetail h01) {
		this.h01 = h01;
	}

	public TypeDetail getH02() {
		return h02;
	}

	public void setH02(TypeDetail h02) {
		this.h02 = h02;
	}

	public Integer getH03() {
		return h03;
	}

	public void setH03(Integer h03) {
		this.h03 = h03;
	}

	public TypeDetail getH04() {
		return h04;
	}

	public void setH04(TypeDetail h04) {
		this.h04 = h04;
	}

	public Integer getH05() {
		return h05;
	}

	public void setH05(Integer h05) {
		this.h05 = h05;
	}

	public Integer getH06() {
		return h06;
	}

	public void setH06(Integer h06) {
		this.h06 = h06;
	}

	public Integer getH07() {
		return h07;
	}

	public void setH07(Integer h07) {
		this.h07 = h07;
	}

	public Integer getI01() {
		return i01;
	}

	public void setI01(Integer i01) {
		this.i01 = i01;
	}

	public Integer getI02() {
		return i02;
	}

	public void setI02(Integer i02) {
		this.i02 = i02;
	}

	public Integer getI03() {
		return i03;
	}

	public void setI03(Integer i03) {
		this.i03 = i03;
	}

	public Integer getJ01() {
		return j01;
	}

	public void setJ01(Integer j01) {
		this.j01 = j01;
	}

	public Integer getJ02() {
		return j02;
	}

	public void setJ02(Integer j02) {
		this.j02 = j02;
	}

	public Integer getJ03() {
		return j03;
	}

	public void setJ03(Integer j03) {
		this.j03 = j03;
	}

	public Integer getJ04() {
		return j04;
	}

	public void setJ04(Integer j04) {
		this.j04 = j04;
	}

	public Integer getJ05() {
		return j05;
	}

	public void setJ05(Integer j05) {
		this.j05 = j05;
	}

	public Integer getJ06() {
		return j06;
	}

	public void setJ06(Integer j06) {
		this.j06 = j06;
	}

	public Integer getJ07() {
		return j07;
	}

	public void setJ07(Integer j07) {
		this.j07 = j07;
	}

	public Integer getJ08() {
		return j08;
	}

	public void setJ08(Integer j08) {
		this.j08 = j08;
	}

	public Integer getJ09() {
		return j09;
	}

	public void setJ09(Integer j09) {
		this.j09 = j09;
	}

	public Integer getK01() {
		return k01;
	}

	public void setK01(Integer k01) {
		this.k01 = k01;
	}

	public Integer getK02() {
		return k02;
	}

	public void setK02(Integer k02) {
		this.k02 = k02;
	}

	public Integer getK03() {
		return k03;
	}

	public void setK03(Integer k03) {
		this.k03 = k03;
	}

	public Integer getK04() {
		return k04;
	}

	public void setK04(Integer k04) {
		this.k04 = k04;
	}

	public Integer getK05() {
		return k05;
	}

	public void setK05(Integer k05) {
		this.k05 = k05;
	}

	public Integer getK06() {
		return k06;
	}

	public void setK06(Integer k06) {
		this.k06 = k06;
	}

	public Integer getL01() {
		return l01;
	}

	public void setL01(Integer l01) {
		this.l01 = l01;
	}

	public Integer getL02() {
		return l02;
	}

	public void setL02(Integer l02) {
		this.l02 = l02;
	}

	public Integer getL03() {
		return l03;
	}

	public void setL03(Integer l03) {
		this.l03 = l03;
	}

	public Integer getM01() {
		return m01;
	}

	public void setM01(Integer m01) {
		this.m01 = m01;
	}

	public Integer getM02() {
		return m02;
	}

	public void setM02(Integer m02) {
		this.m02 = m02;
	}

	public Integer getN01() {
		return n01;
	}

	public void setN01(Integer n01) {
		this.n01 = n01;
	}

	public Integer getO01() {
		return o01;
	}

	public void setO01(Integer o01) {
		this.o01 = o01;
	}

	public Integer getO02() {
		return o02;
	}

	public void setO02(Integer o02) {
		this.o02 = o02;
	}

	public Integer getP01() {
		return p01;
	}

	public void setP01(Integer p01) {
		this.p01 = p01;
	}

	public Integer getP02() {
		return p02;
	}

	public void setP02(Integer p02) {
		this.p02 = p02;
	}

	public Integer getP03() {
		return p03;
	}

	public void setP03(Integer p03) {
		this.p03 = p03;
	}

	public List<FileModel> getQ01() {
		return q01;
	}

	public void setQ01(List<FileModel> q01) {
		this.q01 = q01;
	}

	public Integer getR01() {
		return r01;
	}

	public void setR01(Integer r01) {
		this.r01 = r01;
	}

	public Integer getS01() {
		return s01;
	}

	public void setS01(Integer s01) {
		this.s01 = s01;
	}

	public Integer getT01() {
		return t01;
	}

	public void setT01(Integer t01) {
		this.t01 = t01;
	}

	public Integer getU01() {
		return u01;
	}

	public void setU01(Integer u01) {
		this.u01 = u01;
	}

	public Integer getU02() {
		return u02;
	}

	public void setU02(Integer u02) {
		this.u02 = u02;
	}

	public Integer getU03() {
		return u03;
	}

	public void setU03(Integer u03) {
		this.u03 = u03;
	}

	public Integer getV01() {
		return v01;
	}

	public void setV01(Integer v01) {
		this.v01 = v01;
	}

	public Integer getV02() {
		return v02;
	}

	public void setV02(Integer v02) {
		this.v02 = v02;
	}

	public Integer getW01() {
		return w01;
	}

	public void setW01(Integer w01) {
		this.w01 = w01;
	}

	public Integer getW02() {
		return w02;
	}

	public void setW02(Integer w02) {
		this.w02 = w02;
	}

	public Integer getW03() {
		return w03;
	}

	public void setW03(Integer w03) {
		this.w03 = w03;
	}

	public Integer getW04() {
		return w04;
	}

	public void setW04(Integer w04) {
		this.w04 = w04;
	}

	public Integer getX01() {
		return x01;
	}

	public void setX01(Integer x01) {
		this.x01 = x01;
	}

	public Integer getX02() {
		return x02;
	}

	public void setX02(Integer x02) {
		this.x02 = x02;
	}

	public Integer getY01() {
		return y01;
	}

	public void setY01(Integer y01) {
		this.y01 = y01;
	}

	public Integer getY02() {
		return y02;
	}

	public void setY02(Integer y02) {
		this.y02 = y02;
	}

	public Integer getZ01() {
		return z01;
	}

	public void setZ01(Integer z01) {
		this.z01 = z01;
	}

	public Integer getZ02() {
		return z02;
	}

	public void setZ02(Integer z02) {
		this.z02 = z02;
	}

	public Integer getZ03() {
		return z03;
	}

	public void setZ03(Integer z03) {
		this.z03 = z03;
	}

	public Integer getZ04() {
		return z04;
	}

	public void setZ04(Integer z04) {
		this.z04 = z04;
	}

	public Integer getAa01() {
		return aa01;
	}

	public void setAa01(Integer aa01) {
		this.aa01 = aa01;
	}

	public Integer getAb01() {
		return ab01;
	}

	public void setAb01(Integer ab01) {
		this.ab01 = ab01;
	}

	public Integer getAb02() {
		return ab02;
	}

	public void setAb02(Integer ab02) {
		this.ab02 = ab02;
	}

	public Integer getAc01() {
		return ac01;
	}

	public void setAc01(Integer ac01) {
		this.ac01 = ac01;
	}
//
//	public Integer getAc02() {
//		return ac02;
//	}
//
//	public void setAc02(Integer ac02) {
//		this.ac02 = ac02;
//	}
//
//	public Integer getAc03() {
//		return ac03;
//	}
//
//	public void setAc03(Integer ac03) {
//		this.ac03 = ac03;
//	}
//
//	public Integer getAc04() {
//		return ac04;
//	}
//
//	public void setAc04(Integer ac04) {
//		this.ac04 = ac04;
//	}

	public Integer getAd01() {
		return ad01;
	}

	public void setAd01(Integer ad01) {
		this.ad01 = ad01;
	}

	public TypeDetail getAd02() {
		return ad02;
	}

	public void setAd02(TypeDetail ad02) {
		this.ad02 = ad02;
	}

	public List<Map<String, List<FileModel>>> getAd03() {
		return ad03;
	}

	public void setAd03(List<Map<String, List<FileModel>>> ad03) {
		this.ad03 = ad03;
	}

//	public Integer getAd04() {
//		return ad04;
//	}
//
//	public void setAd04(Integer ad04) {
//		this.ad04 = ad04;
//	}
//
//	public Integer getAd05() {
//		return ad05;
//	}
//
//	public void setAd05(Integer ad05) {
//		this.ad05 = ad05;
//	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PeriodReference getPeriodReference() {
		return periodReference;
	}

	public void setPeriodReference(PeriodReference periodReference) {
		this.periodReference = periodReference;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Boolean getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOthersValue() {
		return othersValue;
	}

	public void setOthersValue(String othersValue) {
		this.othersValue = othersValue;
	}

	public List<FileModel> getJ10() {
		return j10;
	}

	public void setJ10(List<FileModel> j10) {
		this.j10 = j10;
	}

	public Integer getF06() {
		return f06;
	}

	public void setF06(Integer f06) {
		this.f06 = f06;
	}
	
	

//	public Integer getZ05() {
//		return z05;
//	}
//
//	public void setZ05(Integer z05) {
//		this.z05 = z05;
//	}

//	public Integer getAb00() {
//		return ab00;
//	}
//
//	public void setAb00(Integer ab00) {
//		this.ab00 = ab00;
//	}
//	


	
	
	

}
