package cn.edu.cqu.ngtl.optionfinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-30.
 */
public class SessionTermFinder extends KeyValuesBase {
    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("秋季", "秋季"));
        keyValues.add(new ConcreteKeyValue("春季", "春季"));
        return keyValues;
    }
}
