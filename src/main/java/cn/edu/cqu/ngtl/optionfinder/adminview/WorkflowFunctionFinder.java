package cn.edu.cqu.ngtl.optionfinder.adminview;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowFunctionsDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowFunctions;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-10.
 */
public class WorkflowFunctionFinder extends KeyValuesBase {
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

        List<TAMSWorkflowFunctions> functions = new TAMSWorkflowFunctionsDaoJpa().selectAll();

        for(TAMSWorkflowFunctions function : functions) {
            keyValues.add(new ConcreteKeyValue(function.getId(), function.getName()));
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
