package cn.edu.cqu.ngtl.optionfinder.classview;

import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.KRADServiceLocator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by damei on 16/11/20.
 */
public class sessionFinder extends KeyValuesBase{

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    private boolean blankOption;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList();
        if(blankOption){
            keyValues.add(new ConcreteKeyValue("", ""));

        }

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();

        Query qr = em.createNativeQuery("SELECT s.UNIQUEID, s.YEAR, s.TERM  FROM UNITIME_SESSION s WHERE UNIQUEID != '"+curSession.getId()+"' ORDER BY s.YEAR DESC, s.TERM DESC");

        //List<UTSession> sessions = new UTSessionDaoJpa().selectAll();
        List<Object> sessions = qr.getResultList();

        for(Object session : sessions) {
            Object[] column = (Object[]) session;
            keyValues.add(new ConcreteKeyValue(column[0].toString(), column[1].toString()+"年"+column[2].toString()+"季"));
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
