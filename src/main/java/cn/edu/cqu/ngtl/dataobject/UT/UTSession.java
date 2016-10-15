package cn.edu.cqu.ngtl.dataobject.UT;

import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tangjing on 16-10-15.
 */
@Entity
@Table(name = "UNITIME_SESSION")
public class UTSession extends DataObjectBase implements Serializable {

    @Id
    @Column(name = "UNIQUEID")
    private Integer id;

    @Column(name="YEAR")
    private String year;
    
    @Column(name="TERM")
    private String term;

    @Column(name="SESSION_BEGIN")
    @Convert(converter = StringDateConverter.class)
    private String beginDate;

    @Column(name="SESSION_END")
    @Convert(converter = StringDateConverter.class)
    private String endDate;

}
