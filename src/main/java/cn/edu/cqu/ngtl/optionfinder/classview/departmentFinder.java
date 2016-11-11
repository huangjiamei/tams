package cn.edu.cqu.ngtl.optionfinder.classView;

import cn.edu.cqu.ngtl.dao.ut.impl.UTDepartmentDaoJpa;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-4.
 */
public class departmentFinder extends KeyValuesBase {
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

        List<UTDepartment> departments= new UTDepartmentDaoJpa().getAllUTDepartments();

        for(UTDepartment department : departments) {
            keyValues.add(new ConcreteKeyValue(department.getId().toString(), department.getName()));
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
