package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-6.
 */
@Repository
@Component("TAMSDeptFundingDaoJpa")
public class TAMSDeptFundingDaoJpa implements TAMSDeptFundingDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Override
    public List<TAMSDeptFunding> selectAllBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();

        //先添加当前学期的内容
        TAMSDeptFunding current = new TAMSDeptFunding();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT SUM(PLAN_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='"+curSession.getId()+"'");
        String planFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(ACTUAL_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='"+curSession.getId()+"'");
        String actualFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(PHD_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='"+curSession.getId()+"'");
        String phdFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(APPLY_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='"+curSession.getId()+"'");
        String applyFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(BONUS) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='"+curSession.getId()+"'");
        String bonus = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);

        current.setPlanFunding(planFunding);
        current.setActualFunding(actualFunding);
        current.setPhdFunding(phdFunding);
        current.setApplyFunding(applyFunding);
        current.setBonus(bonus);
        current.setSession(curSession);
        list.add(current);

        //TODO 把其余学期的经费计算合放进list，需要把session通过sessionId实体化装载到经费实体中

        return list;
    }
}
