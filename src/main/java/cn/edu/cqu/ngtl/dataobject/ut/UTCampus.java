package cn.edu.cqu.ngtl.dataobject.ut;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "UNITIME_CAMPUS")
public class UTCampus extends DataObjectBase implements Serializable {
private static final long serialVersionUID = 2815620281514105119L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "SHORTNAME")
	private String shortName;
	
	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy="utCampus")
	@JsonIgnore
	private List<UTBuilding> buildings;
	
	@Column(name = "UNIVERSITY_ID")
	private String universityId;
	
	@ManyToOne
	@JoinColumn(name="UNIVERSITY_ID", insertable = false, updatable = false)
	private UTUniversity utUniversity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UTBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<UTBuilding> buildings) {
		this.buildings = buildings;
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
