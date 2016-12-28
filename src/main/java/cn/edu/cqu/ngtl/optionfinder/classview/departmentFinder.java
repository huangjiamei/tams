package cn.edu.cqu.ngtl.optionfinder.classview;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.ut.impl.UTDepartmentDaoJpa;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import cn.edu.cqu.ngtl.service.userservice.impl.UserInfoServiceImpl;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;

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

        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        Boolean userInfo=new UserInfoServiceImpl().isCollegeStaff(user.getCode());//检测是否为而建单位管理员，如果是，则optionfinder只显示本学院

        List<UTDepartment> departments= new UTDepartmentDaoJpa().getAllHasCourseDepartment();
        if (!userInfo) {

            for (UTDepartment department : departments) {
                keyValues.add(new ConcreteKeyValue(department.getId().toString(), department.getName()));
            }
        }else{
            keyValues.add(new ConcreteKeyValue(user.getDepartmentId().toString(), user.getDepartment()));
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
