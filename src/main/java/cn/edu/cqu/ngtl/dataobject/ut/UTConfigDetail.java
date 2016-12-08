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
//    @GeneratedValue(generator = "utConfigDetailSeq")
//    @SequenceGenerator(name = "utConfigDetailSeq", sequenceName = "UNITIME_CONFIG_DETAIL_S", allocationSize = 1)
    private String id;

    @Column(name = "CONFIG_ID")
    private String configId;

    @ManyToOne
    @JoinColumn(name = "CONFIG_ID", updatable = false, insertable = false)
    private UTCourseOfferingConfig offeringConfig;

    @Column(name = "CLASS_ID")
    private String klassId;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID", updatable = false, insertable = false)
    private UTClass klass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getKlassId() {
        return klassId;
    }

    public void setKlassId(String klassId) {
        this.klassId = klassId;
    }

    public UTCourseOfferingConfig getOfferingConfig() {
        return this.offeringConfig;
    }

    public void setOfferingConfig(UTCourseOfferingConfig offeringConfig) {
        this.offeringConfig = offeringConfig;
    }



    public UTClass getKlass() {
        return this.klass;
    }


}
