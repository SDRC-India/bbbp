package org.sdrc.bbbp.models;

public class SubmissionDataModel {/*
	
	
	private Long id; 
	private Integer a01	; //typedetails
	private Integer a02;
	private Integer a03;
	private Integer a04;
	private Integer a05;
	private List<ValueObject> a06; //filetype
	private Integer b01;
	private Integer b02;
	private Integer b03; //typedetails
	private Integer c01;//typedetails
	private Integer c02;
	private Integer c03;
	private Integer c04;
	private Integer c05;
	private Integer c06;
	private Integer c07;
	private Integer c08;
	private Integer d01;
	private Integer d02;
	private Integer d03;
	private Integer d04;
	private Integer d05;
	private Integer e01;
	private Integer e02;
	private Integer e03;
	private Integer f01;
	private Integer f02;
	private Integer f03;
	private TypeDetail g01; //typedetails
	private Integer g02;
	private Integer g03;
	private TypeDetail h01; //typedetails
	private TypeDetail h02; //typedetails
	private Integer h03;
	private TypeDetail h04;//typedetails
	private Integer h05;
	private Integer h06;
	private Integer h07;
	private Integer h08;
	private Integer i01;
	private Integer i02;
	private Integer j01;
	private Integer j02;
	private Integer j03;
	private Integer j04;
	private Integer j05;
	private Integer j06;
	private Integer j07;
	private Integer j08;
	private Integer j09;
	private Integer k01;
	private Integer k02;
	private Integer l01;
	private Integer l02;
	private Integer l03;
	private Integer m01;
	private Integer m02;
	private Integer n01;
	private Integer o01;
	private Integer o02;
	private Integer p01;
	private Integer p02;
	private Integer p03;
	private List<ValueObject> q01; //filetype
	private Integer r01;
	private Integer s01;
	private Integer t01;
	private Integer u01;
	private Integer u02;
	private Integer u03;
	private Integer u04;
	private Integer v01;
	private Integer v02;
	private Integer w01;
	private Integer w02;
	private Integer w03;
	private Integer w04;
	private Integer x01;
	private Integer x02;
	private Integer y01;
	private Integer y02;
	private Integer y03;
	private Integer z01;
	private Integer z02;
	private Integer aa01;
	private Integer ab01;
	private Integer ab02;
	private Integer ac01;
	private Integer ac02;
	private Integer ac03;
	private Integer ac04;
	private Integer ad01;
	private TypeDetail ad02; //typedetails
	private List<Map<String,List<ValueObject>>> ad03; //filetype
	private Integer ad04;
	private Integer ad05;
	private Integer userId;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getA01() {
		return a01;
	}
	public void setA01(Integer a01) {
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
	
	public List<ValueObject> getA06() {
		return a06;
	}
	public void setA06(List<ValueObject> a06) {
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
	public Integer getB03() {
		return b03;
	}
	public void setB03(Integer b03) {
		this.b03 = b03;
	}
	public Integer getC01() {
		return c01;
	}
	public void setC01(Integer c01) {
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
	public Integer getF01() {
		return f01;
	}
	public void setF01(Integer f01) {
		this.f01 = f01;
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
	public Integer getH08() {
		return h08;
	}
	public void setH08(Integer h08) {
		this.h08 = h08;
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
	
	public List<ValueObject> getQ01() {
		return q01;
	}
	public void setQ01(List<ValueObject> q01) {
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
	public Integer getU04() {
		return u04;
	}
	public void setU04(Integer u04) {
		this.u04 = u04;
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
	public Integer getY03() {
		return y03;
	}
	public void setY03(Integer y03) {
		this.y03 = y03;
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
	public Integer getAc02() {
		return ac02;
	}
	public void setAc02(Integer ac02) {
		this.ac02 = ac02;
	}
	public Integer getAc03() {
		return ac03;
	}
	public void setAc03(Integer ac03) {
		this.ac03 = ac03;
	}
	public Integer getAc04() {
		return ac04;
	}
	public void setAc04(Integer ac04) {
		this.ac04 = ac04;
	}
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
	
	
	public List<Map<String, List<ValueObject>>> getAd03() {
		return ad03;
	}
	public void setAd03(List<Map<String, List<ValueObject>>> ad03) {
		this.ad03 = ad03;
	}
	public Integer getAd04() {
		return ad04;
	}
	public void setAd04(Integer ad04) {
		this.ad04 = ad04;
	}
	public Integer getAd05() {
		return ad05;
	}
	public void setAd05(Integer ad05) {
		this.ad05 = ad05;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	*/}
