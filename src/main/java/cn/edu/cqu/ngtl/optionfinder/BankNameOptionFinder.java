package cn.edu.cqu.ngtl.optionfinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/30.
 */
public class BankNameOptionFinder extends KeyValuesBase {

    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        keyValues.add(new ConcreteKeyValue("中国银行", "中国银行"));
        keyValues.add(new ConcreteKeyValue("重庆银行", "重庆银行"));

        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
