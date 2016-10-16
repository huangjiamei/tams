package cn.edu.cqu.ngtl.optionfinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/10/16.
 */
public class TestFinder extends KeyValuesBase {

    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }
        keyValues.add(new ConcreteKeyValue("1", "Option 1"));
        keyValues.add(new ConcreteKeyValue("2", "Option 2"));
        keyValues.add(new ConcreteKeyValue("3", "Option 3"));
        keyValues.add(new ConcreteKeyValue("4", "Option 4"));
        keyValues.add(new ConcreteKeyValue("5", "Option 5"));
        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
