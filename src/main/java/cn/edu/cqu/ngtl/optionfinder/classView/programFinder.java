package cn.edu.cqu.ngtl.optionfinder.classView;

import cn.edu.cqu.ngtl.dao.cm.impl.CMProgramDaoJpa;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-4.
 */
public class programFinder extends UifKeyValuesFinderBase {
    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        ClassInfoForm classInfoForm = (ClassInfoForm) model;
        List<KeyValue> keyValues = new ArrayList();

        List<CMProgram> programs= new CMProgramDaoJpa().selectByDepartmentId(classInfoForm.getCondDepartmentName());

        if(programs == null)
            return keyValues;
        for(CMProgram program : programs) {
            keyValues.add(new ConcreteKeyValue(program.getId().toString(), program.getName()));
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
