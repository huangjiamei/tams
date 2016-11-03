package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTaDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTClassInstructorDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTStudentDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQU-CST-WuErli on 2016/11/3.
 */
public class TaAssignFinder extends KeyValuesBase {

    /**
     * 开启blankOption后默认option为空，否则为option1
     */
    private boolean blankOption;


    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));
        }

        UserSession userSession = GlobalVariables.getUserSession();
        String uId = userSession.getLoggedInUserPrincipalId();

        // test
        List<Object> classIds = new UTClassInstructorDaoJpa().selectClassIdsByInstructorId(uId);
        List<TAMSTa> tas = new TAMSTaDaoJpa().selectByClassId(classIds);

        UTStudentDaoJpa utStudentDaoJpa = new UTStudentDaoJpa();

        for (TAMSTa ta : tas) {
            UTStudent utStudent = utStudentDaoJpa.getUTStudentById(ta.getId());
            keyValues.add(new ConcreteKeyValue(utStudent.getId(), utStudent.getName()));
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
