package cn.edu.cqu.ngtl.dataobject;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "UNITIME_CAMPUS")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTCampus extends DataObjectBase implements Serializable {
private static final long serialVersionUID = 2815620281514105119L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "SHORTNAME")
	@Label("SHORTNAME")
	private String shortName;
	
	@Column(name = "NAME")
	@Label("name")
	private String name;

	@OneToMany(mappedBy="utCampus")
	@JsonIgnore
	private List<UTBuilding> buildings;
	
	@Column(name = "UNIVERSITY_ID")
	@Label("UNIVERSITY_ID")
	private String universityId;
	
	@ManyToOne
	@JoinColumn(name="UNIVERSITY_ID", insertable = false, updatable = false)
	private UTUniversity utUniversity;
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<UTBuilding> getBuildings() {
		return this.buildings;
	}
	public void setBuildings(List<UTBuilding> buildings) {
		this.buildings = buildings;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public UTUniversity getUtUniversity() {
		return utUniversity;
	}
	public void setUtUniversity(UTUniversity utUniversity) {
		this.utUniversity = utUniversity;
	}
}
