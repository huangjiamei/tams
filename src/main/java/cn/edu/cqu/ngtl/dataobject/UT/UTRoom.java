/**
 * 教室的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject.UT;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_ROOM")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTRoom extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = -7810921207047858809L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "NAME")
	@Label("name")
	private String name;

	@Column(name = "CAPACITY")
	private Integer capacity;

	@Column(name = "BUILDING_ID")
	private Integer buildingId;

	@ManyToOne
	@JoinColumn(name="BUILDING_ID",insertable = false, updatable = false)
	private UTBuilding building;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public UTBuilding getBuilding() {
		return building;
	}

	public void setBuilding(UTBuilding building) {
		this.building = building;
	}
}
