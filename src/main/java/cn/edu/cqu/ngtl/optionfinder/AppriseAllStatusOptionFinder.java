package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.taservice.impl.TAServiceimpl;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/15.
 */
public class AppriseAllStatusOptionFinder extends UifKeyValuesFinderBase {


    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        TaInfoForm taInfoForm = (TaInfoForm) model;
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        List<TaInfoViewObject> taList = taInfoForm.getAllTaInfo();
        List<TaInfoViewObject> checkedList = new ArrayList<>();
        for(TaInfoViewObject per : taList) {
            if(per.isCheckBox())
                checkedList.add(per);
        }
        List<TAMSWorkflowStatus> statuses = new ArrayList<>();
        for(TaInfoViewObject taInfoViewObject:checkedList) {
            if(statuses==null||statuses.size()==0)
                statuses.addAll(new TAServiceimpl().appriseStatusAvailable(taInfoForm.getUser().getCode(), taInfoViewObject.getId()));
            else
                statuses.retainAll(new TAServiceimpl().appriseStatusAvailable(taInfoForm.getUser().getCode(), taInfoViewObject.getId()));

        }
        for(TAMSWorkflowStatus status : statuses) {
            keyValues.add(new ConcreteKeyValue(status.getId(), status.getWorkflowStatus()));
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
