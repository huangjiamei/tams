package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSIssueTypeDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQU-CST-WuErli on 2016/11/3.
 */
public class TaTaskCategoryFinder extends KeyValuesBase{

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

        List<TAMSIssueType> issueTypes = new TAMSIssueTypeDaoJpa().selectAll();

        for (TAMSIssueType issueType : issueTypes) {
            keyValues.add(new ConcreteKeyValue(issueType.getId(), issueType.getTypeName());
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
