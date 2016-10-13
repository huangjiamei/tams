/**
 * 教室的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_ROOM")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTRoom extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = -7810921207047858809L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME")
	@Label("name")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CAPACITY")
	private Integer capacity;
	public Integer getCapacity(){
		return this.capacity;
	}

	public void setCapacity(Integer capacity){
		this.capacity = capacity;
	}

	@Column(name = "BUILDING_ID")
	private Integer buildingId;
	public Integer getBuildingId() {
		return this.buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	@ManyToOne
	@JoinColumn(name="BUILDING_ID",insertable = false, updatable = false)
	private UTBuilding building;
	public UTBuilding getBuilding() {
		return this.building;
	}
	public void setBuilding(UTBuilding building) {
		this.building = building;
	}

	/**
	 * 获取教室基本信息
	 * @author zhouxiaowen
	 */
	@Override
	@Transient
	public String toString()
	{
		String room ="教学楼:" + this.building.getName()+", 教室:" + this.name + ", 容量:" + this.capacity;
		return room;
	}
}
