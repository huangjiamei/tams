package cn.edu.cqu.ngtl.dataobject.tams;

import javax.persistence.*;

/**
 * Created by tangjing on 16-11-23.
 */
@Entity
@Table(name = "TAMS_TIMESETTING_TYPE")
public class TAMSTimeSettingType {
    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator = "tamsTimeSettingType")
    @SequenceGenerator(name = "tamsTimeSettingType", sequenceName = "TAMS_TIMESETTING_TYPE_S", allocationSize = 1)
    private String id;

    @Column(name = "TYPE_NAME")
    String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
