package cn.edu.cqu.ngtl.optionfinder.classview;

import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
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
/*
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
        }*/

        keyValues.add(new ConcreteKeyValue("第-2周(开学前2周)", "第-2周(开学前1周)"));
        keyValues.add(new ConcreteKeyValue("第-1周(开学前1周)", "第-1周(开学前1周)"));
        keyValues.add(new ConcreteKeyValue("第1周", "第1周)"));
        keyValues.add(new ConcreteKeyValue("第2周", "第2周)"));
        keyValues.add(new ConcreteKeyValue("第3周", "第3周)"));
        keyValues.add(new ConcreteKeyValue("第4周", "第4周)"));
        keyValues.add(new ConcreteKeyValue("第5周", "第5周)"));
        keyValues.add(new ConcreteKeyValue("第6周", "第6周)"));
        keyValues.add(new ConcreteKeyValue("第7周", "第7周)"));
        keyValues.add(new ConcreteKeyValue("第8周", "第8周)"));
        keyValues.add(new ConcreteKeyValue("第9周", "第9周)"));
        keyValues.add(new ConcreteKeyValue("第10周", "第10周)"));
        keyValues.add(new ConcreteKeyValue("第11周", "第11周)"));
        keyValues.add(new ConcreteKeyValue("第12周", "第12周)"));
        keyValues.add(new ConcreteKeyValue("第13周", "第13周)"));
        keyValues.add(new ConcreteKeyValue("第14周", "第14周)"));
        keyValues.add(new ConcreteKeyValue("第15周", "第15周)"));
        keyValues.add(new ConcreteKeyValue("第16周", "第16周)"));
        keyValues.add(new ConcreteKeyValue("第17周", "第17周)"));
        keyValues.add(new ConcreteKeyValue("第18周", "第18周)"));
        keyValues.add(new ConcreteKeyValue("第19周", "第19周)"));
        keyValues.add(new ConcreteKeyValue("第20周", "第20周)"));
        keyValues.add(new ConcreteKeyValue("第21周", "第21周)"));
        keyValues.add(new ConcreteKeyValue("第22周", "第22周)"));
        keyValues.add(new ConcreteKeyValue("第23周", "第23周)"));
        keyValues.add(new ConcreteKeyValue("第24周", "第24周)"));
        keyValues.add(new ConcreteKeyValue("第25周", "第25周)"));




        return keyValues;
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }

}
