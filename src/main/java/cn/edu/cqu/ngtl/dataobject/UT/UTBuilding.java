/**
 * 教室的dataobject，通过JPA与数据库关联
 *
 * @author 洪明坚
 */
package cn.edu.cqu.ngtl.dataobject.UT;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UNITIME_BUILDING")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTBuilding extends DataObjectBase implements Serializable {

	private static final long serialVersionUID = 2815620281514105119L;

	@Id
	@Column(name = "UNIQUEID")
	private Integer id;

	@Column(name = "NAME")
	@Label("name")
	private String name;

	@Column(name = "COORDINATE_X")
	private String gpsX;

	@Column(name = "COORDINATE_Y")
	private String gpsY;

	@Column(name="CAMPUS_ID")
	private Integer campusId;

	@ManyToOne
	@JoinColumn(name="CAMPUS_ID", insertable = false, updatable = false)
	private UTCampus utCampus;

	@OneToMany(mappedBy="building")
	@JsonIgnore
	private List<UTRoom> rooms;

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

	public String getGpsX() {
		return gpsX;
	}

	public void setGpsX(String gpsX) {
		this.gpsX = gpsX;
	}

	public String getGpsY() {
		return gpsY;
	}

	public void setGpsY(String gpsY) {
		this.gpsY = gpsY;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public UTCampus getUtCampus() {
		return utCampus;
	}

	public void setUtCampus(UTCampus utCampus) {
		this.utCampus = utCampus;
	}

	public List<UTRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<UTRoom> rooms) {
		this.rooms = rooms;
	}
}
