package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.impl.ClassInfoServiceImpl;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/15.
 */
public class ApproveAllStatusOptionFinder extends UifKeyValuesFinderBase {


    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        ClassInfoForm infoForm = (ClassInfoForm) model;
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        List<ClassTeacherViewObject> checkedList = new ArrayList<>();
        for(ClassTeacherViewObject per : classList) {
            if(per.isChecked())
                checkedList.add(per);
        }

        List<TAMSWorkflowStatus> statuses = new ArrayList<>();
        for(ClassTeacherViewObject classTeacherViewObject:checkedList) {
            if(statuses==null||statuses.size()==0)
                statuses.addAll(new ClassInfoServiceImpl().classStatusAvailable(infoForm.getUser().getCode(), classTeacherViewObject.getId()));
            else
                statuses.retainAll(new ClassInfoServiceImpl().classStatusAvailable(infoForm.getUser().getCode(), classTeacherViewObject.getId()));

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