package org.sdrc.bbbp.dashboard.models;


public class ValueObject {

	private String key;
	private Object value;
	private String description;
	private String groupName;
	private String shortNmae;
	private Boolean isSelected;
	private Integer id;
	private String keyValue;
	private Integer count;
	
	
	
	public String getKeyValue() {
		return keyValue;
	}


	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}


	public ValueObject(String key, Object value) {
		this.key = key;
		this.value = value;
	}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public ValueObject() {
		super();
	}
	public ValueObject(String value, String keyValue) {
		super();
		this.value = value;
		this.keyValue = keyValue;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getShortNmae() {
		return shortNmae;
	}
	public void setShortNmae(String shortNmae) {
		this.shortNmae = shortNmae;
	}
	
	public ValueObject(int key, String value) {
		this(new Integer(key).toString(),value);
	}
	
	public ValueObject(int key, String value, int description) {
		super();
		this.key = new Integer(key).toString();
		this.value = value;
		this.description = new Integer(description).toString();
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public ValueObject(String key, String value, String description,int count) {
		super();
		this.key = key;
		this.value = value;
		this.description = description;
		this.count=count;
	}
	@Override
	public String toString() {
		return "ValueObject [key=" + key + ", value=" + value
				+ ", description=" + description + ", groupName=" + groupName
				+ ", shortNmae=" + shortNmae + ", isSelected=" + isSelected
				+ "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
