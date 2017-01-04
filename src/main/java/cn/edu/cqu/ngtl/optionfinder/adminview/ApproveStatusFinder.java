package cn.edu.cqu.ngtl.optionfinder.adminview;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowFunctionsDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowStatusDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-12-11.
 */
public class ApproveStatusFinder extends KeyValuesBase {
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

        String approveFunctionId = new TAMSWorkflowFunctionsDaoJpa().selectOneByName("审核").getId();
        List<TAMSWorkflowStatus> statuses = new TAMSWorkflowStatusDaoJpa().selectByFunctionId(approveFunctionId);


        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        Boolean userInfo=new UserInfoServiceImpl().isStudent(user.getCode());//检测是否为学生，如果是，则optionfinder只显示工作状态

        if(userInfo){
            keyValues.add(new ConcreteKeyValue("5", "选聘中"));
        }else{
            for(TAMSWorkflowStatus status : statuses) {
                keyValues.add(new ConcreteKeyValue(status.getId(), status.getWorkflowStatus()));
            }
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
