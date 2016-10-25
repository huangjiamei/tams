package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by awake on 2016-10-19.
 */
@Repository
@Component("UTClassInfoDaoJpa")
public class UTClassInfoDaoJpa implements UTClassInfoDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Override
    public List<UTClassInformation> getAllCurrentClassInformation(){
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INFORMATION t WHERE t.SESSION_ID='"+curSession.getId()+"'",UTClassInformation.class);
        List<UTClassInformation> result = query.getResultList();
        return result;
    }

    @Override
    public UTClassInformation getOneById(Integer id) {
        return KRADServiceLocator.getDataObjectService().find(UTClassInformation.class, id);
    }

}
