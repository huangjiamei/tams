package cn.edu.cqu.ngtl.optionfinder.adminview;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowFunctionsDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowStatusDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljz on 2017/1/13.
 */
public class ApproveTravelSubsidyOptionFinder  extends KeyValuesBase{
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(){
        List<KeyValue> keyValues=new ArrayList<>();
        if (blankOption){
            keyValues.add(new ConcreteKeyValue("",""));
        }
        String approveTravelSubsidyFunctionId= new TAMSWorkflowFunctionsDaoJpa().selectOneByName("审核交通补贴").getId();
        List<TAMSWorkflowStatus> statuses=new TAMSWorkflowStatusDaoJpa().selectByFunctionId(approveTravelSubsidyFunctionId);

        for (TAMSWorkflowStatus status : statuses){
            keyValues.add(new ConcreteKeyValue(status.getId(),status.getWorkflowStatus()));
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
