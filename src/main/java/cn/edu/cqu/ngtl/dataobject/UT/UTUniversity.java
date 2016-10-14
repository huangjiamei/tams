/**
 * 
 */
/**
 * @author Bill
 *
 */
package cn.edu.cqu.ngtl.dataobject.UT;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.Label;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViewType;
import org.kuali.rice.krad.data.provider.annotation.UifAutoCreateViews;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UNITIME_UNIVERSITY")
@UifAutoCreateViews({ UifAutoCreateViewType.INQUIRY, UifAutoCreateViewType.LOOKUP })
public class UTUniversity extends DataObjectBase implements Serializable {

	@Id
	@Column(name = "UNIQUEID")
    @GeneratedValue(generator="utUniversitySeq")
    @SequenceGenerator(name="utUniversitySeq",sequenceName="UNITIME_UNIVERSITY_ID_S",allocationSize=1)
	private Integer id;

	@Column(name = "NAME")
	@Label("NAME")
	private String name;
	
	@Column(name = "CODE")
	@Label("CODE")
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
