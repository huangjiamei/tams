package cn.edu.cqu.ngtl.dataobject.ut;

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
    @GeneratedValue(generator="utSession")
    @SequenceGenerator(name="utSession",sequenceName="UNITIME_SESSION_S",allocationSize=1)
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

    @Column(name="ACTIVE")
    private String active;


    @Transient
    private String timeInfoName;
    public String getTimeInfoName(){
        return this.getYear()+"年"+this.getTerm()+"季";
    }

    public void setTimeInfoName(String timeInfoName) {
        this.timeInfoName = timeInfoName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
