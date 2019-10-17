package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.sdrc.bbbp.dashboard.models.GroupIndicatorModel;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Data;

@Entity
@Data
@TypeDefs({
  @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Table( name = "Indicator_group")
public class IndicatorGroup implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer indicatorGroupId;
	
	@Type(type = "json")
    @Column(columnDefinition = "json")
	private List<GroupIndicatorModel> groupIndicatorsJson;

}
