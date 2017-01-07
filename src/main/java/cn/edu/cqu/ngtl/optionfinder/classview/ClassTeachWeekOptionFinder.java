package cn.edu.cqu.ngtl.optionfinder.classview;

import cn.edu.cqu.ngtl.dao.ut.impl.UTClassDaoJpa;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Awake on 2017/1/7.
 */
public class ClassTeachWeekOptionFinder extends UifKeyValuesFinderBase {
    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        ClassInfoForm infoForm = (ClassInfoForm) model;
        List<KeyValue> keyValues = new ArrayList();

        String classId = infoForm.getCurrClassId();
        if(classId!=null) {

            UTClass utClass = new UTClassDaoJpa().selectByClassId(classId);
            String teachWeek = utClass.getTeachWeek();
            if(teachWeek!=null){
                String[] teachWeekList = teachWeek.split("\\|");
                List<String> result = Arrays.asList(teachWeekList);
                Collections.sort(result);
                for(String s: result){
                    if(s.indexOf("0")==0){
                        s.replace("0","");
                    }
                    keyValues.add(new ConcreteKeyValue("第"+s+"周", "第"+s+"周"));
                }
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
