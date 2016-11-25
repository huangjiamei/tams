package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by awake on 2016-11-11.
 */
@Entity
@Table(name = "TAMS_CLASS_EVALUATION")
public class TAMSClassEvaluation extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsevaluation")
    @SequenceGenerator(name="tamsevaluation",sequenceName="TAMS_CLASS_EVALUATION_S",allocationSize=1)
    private String id;

    @Column(name = "EVALUATION_TYPE")
    private String evaluationType;


    @Column(name = "EVALUATION_PERCENT")
    private String evaluationPercent;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID",updatable=false, insertable=false)
    private UTClass utClass;

    @Column(name = "CLASS_ID")
    private Integer classId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationPercent() {
        return evaluationPercent;
    }

    public void setEvaluationPercent(String evaluationPercent) {
        this.evaluationPercent = evaluationPercent;
    }

    public UTClass getUtClass() {
        return utClass;
    }

    public void setUtClass(UTClass utClass) {
        this.utClass = utClass;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
