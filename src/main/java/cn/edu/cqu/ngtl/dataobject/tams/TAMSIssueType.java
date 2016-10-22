package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ISSUE_TYPE")
public class TAMSIssueType extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    private String id;

    @Column(name = "TYPE_NAME")
    private String typeName;

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getTypeName(){return typeName;}
    public void setTypeName(String typeName){this.typeName = typeName;}

}
