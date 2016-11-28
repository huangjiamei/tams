package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDraftDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/11/25.
 */

@Repository
@Component("TAMSDeptFundingDraftDaoJpa")
public class TAMSDeptFundingDraftDaoJpa implements TAMSDeptFundingDraftDao {

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private UTDepartmentDao departmentDao;

    @Override
    public List<TAMSDeptFunding> selectDepartmentCurrDraftBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT t.SESSION_ID,t.DEPARTMENT_ID,t.PLAN_FUNDING,t.APPLY_FUNDING,t.ACTUAL_FUNDING,t.PHD_FUNDING,t.BONUS,t.TRAVEL_SUBSIDY,s.YEAR,s.TERM FROM TAMS_DEPT_FUNDING_DRAFT t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID ='"+curSession.getId()+"'ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
        List<Object> columns = query.getResultList();
        for (Object column : columns) {
            TAMSDeptFunding deptFunding = new TAMSDeptFunding();
            Object[] fundings = (Object[]) column;
            deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));
            deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[1].toString())));
            deptFunding.setPlanFunding(String.valueOf(fundings[2]));
            deptFunding.setApplyFunding(String.valueOf(fundings[3]));
            deptFunding.setActualFunding(String.valueOf(fundings[4]));
            deptFunding.setPhdFunding(String.valueOf(fundings[5]));
            deptFunding.setBonus(String.valueOf(fundings[6]));
            deptFunding.setTravelSubsidy(String.valueOf(fundings[7]));
            list.add(deptFunding);
        }
        return list;
    }
}
