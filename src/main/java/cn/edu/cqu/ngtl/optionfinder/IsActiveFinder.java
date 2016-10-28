package cn.edu.cqu.ngtl.optionfinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-28.
 */
public class IsActiveFinder extends KeyValuesBase {

    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }
        keyValues.add(new ConcreteKeyValue("Y", "可用"));
        keyValues.add(new ConcreteKeyValue("N", "不可用"));
        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
