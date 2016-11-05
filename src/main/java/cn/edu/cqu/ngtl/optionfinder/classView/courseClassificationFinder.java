package cn.edu.cqu.ngtl.optionfinder.classView;

import cn.edu.cqu.ngtl.dao.cm.impl.CMCourseClassificationDaoJpa;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-5.
 */
public class courseClassificationFinder extends KeyValuesBase {
    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        List<CMCourseClassification> classifications = new CMCourseClassificationDaoJpa().selectAll();

        for(CMCourseClassification classification : classifications) {
            keyValues.add(new ConcreteKeyValue(classification.getId().toString(), classification.getName()));
        }

        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
