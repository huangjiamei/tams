package cn.edu.cqu.ngtl.dataobject.ut;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by awake on 2016/12/7.
 */

@Entity
@Table(name = "UNITIME_CONFIG_DETAIL")
public class UTConfigDetail extends DataObjectBase implements Serializable {

    private static final long serialVersionUID = -4324465195144360011L;

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "utConfigDetailSeq")
    @SequenceGenerator(name = "utConfigDetailSeq", sequenceName = "UNITIME_CONFIG_DETAIL_S", allocationSize = 1)
    private Integer id;

    @Column(name = "CONFIG_ID")
    private Integer configId;

    @ManyToOne
    @JoinColumn(name = "CONFIG_ID", updatable = false, insertable = false)
    private UTCourseOfferingConfig offeringConfig;

    @Column(name = "CLASS_ID")
    private Integer klassId;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID", updatable = false, insertable = false)
    private UTClass klass;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return this.configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public UTCourseOfferingConfig getOfferingConfig() {
        return this.offeringConfig;
    }

    public void setOfferingConfig(UTCourseOfferingConfig offeringConfig) {
        this.offeringConfig = offeringConfig;
    }

    public Integer getKlassId() {
        return this.klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public UTClass getKlass() {
        return this.klass;
    }


}
