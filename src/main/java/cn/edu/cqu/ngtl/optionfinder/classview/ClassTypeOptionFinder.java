package cn.edu.cqu.ngtl.optionfinder.classview;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/30.
 */
public class ClassTypeOptionFinder extends KeyValuesBase {

    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        keyValues.add(new ConcreteKeyValue("理论", "理论"));
        keyValues.add(new ConcreteKeyValue("上机", "上机"));
        keyValues.add(new ConcreteKeyValue("实验", "实验"));
        keyValues.add(new ConcreteKeyValue("实践", "实践"));

        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
