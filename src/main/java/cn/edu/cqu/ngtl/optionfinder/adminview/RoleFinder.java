package cn.edu.cqu.ngtl.optionfinder.adminview;

import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_T_DaoJpa;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-10.
 */
public class RoleFinder extends KeyValuesBase {
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

        List<KRIM_ROLE_T> roles= new KRIM_ROLE_T_DaoJpa().getAllKrimRoleTs();

        for(KRIM_ROLE_T role : roles) {
            keyValues.add(new ConcreteKeyValue(role.getId(), role.getName()));
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
