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
@Table(name = "UNITIME_CLASS_RELATION")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTClassRelation extends DataObjectBase implements Serializable {
	
	private static final long serialVersionUID = 1918731649037685178L;
	
	@Id
	@Column(name = "UNIQUEID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="CLASS_ID_1",insertable=false, updatable=false)
	private UTClass klass1;
	
	@ManyToOne
	@JoinColumn(name="CLASS_ID_2",insertable=false, updatable=false)
	private UTClass klass2;
	
	@Column(name="CLASS_ID_1")
	private Integer klassId1;
	
	@Column(name="CLASS_ID_2")
	private Integer klassId2;
	
	@Column(name = "RELATIONSHIP")
	@Label("relationship")
	private String relationship;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UTClass getKlass1() {
		return klass1;
	}

	public void setKlass1(UTClass klass1) {
		this.klass1 = klass1;
	}

	public UTClass getKlass2() {
		return klass2;
	}

	public void setKlass2(UTClass klass2) {
		this.klass2 = klass2;
	}

	public Integer getKlassId1() {
		return klassId1;
	}

	public void setKlassId1(Integer klassId1) {
		this.klassId1 = klassId1;
	}

	public Integer getKlassId2() {
		return klassId2;
	}

	public void setKlassId2(Integer klassId2) {
		this.klassId2 = klassId2;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
}
