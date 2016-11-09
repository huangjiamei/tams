package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tangjing on 16-11-6.
 */
@Entity
@Table(name = "TAMS_DEPT_FUNDING")
public class TAMSDeptFunding extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsDeptFunding")
    @SequenceGenerator(name="tamsDeptFunding",sequenceName="TAMS_DEPT_FUNDING_S",allocationSize=1)
    private String id;

    @Column(name = "PLAN_FUNDING")
    private String planFunding;

    @Column(name = "ACTUAL_FUNDING")
    private String actualFunding;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @OneToOne
    @JoinColumn(name = "DEPARTMENT_ID",updatable=false, insertable=false)
    private UTDepartment department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanFunding() {
        return planFunding;
    }

    public void setPlanFunding(String planFunding) {
        this.planFunding = planFunding;
    }

    public String getActualFunding() {
        return actualFunding;
    }

    public void setActualFunding(String actualFunding) {
        this.actualFunding = actualFunding;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public UTDepartment getDepartment() {
        return department;
    }

    public void setDepartment(UTDepartment department) {
        this.department = department;
    }
}
