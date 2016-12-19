package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTaCategoryDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/19.
 */
public class TaCategoryFinder extends KeyValuesBase {

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

        List<TAMSTaCategory> tamsTaCategories = new TAMSTaCategoryDaoJpa().selectAll();

        for (TAMSTaCategory tamsTaCategory : tamsTaCategories) {
            keyValues.add(new ConcreteKeyValue(tamsTaCategory.getId(), tamsTaCategory.getName()));
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