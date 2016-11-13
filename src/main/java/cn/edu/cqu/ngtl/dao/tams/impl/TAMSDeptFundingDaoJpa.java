package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDao;
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
 * Created by tangjing on 16-11-6.
 */
@Repository
@Component("TAMSDeptFundingDaoJpa")
public class TAMSDeptFundingDaoJpa implements TAMSDeptFundingDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private UTDepartmentDao departmentDao;

    @Override
    public List<TAMSDeptFunding> selectCurrBySession() {
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

        return list;
    }

    @Override
    public List<TAMSDeptFunding> selectPreBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT SESSION_ID,SUM(PLAN_FUNDING) AS PLAN_FUNDING,SUM(ACTUAL_FUNDING) AS ACTUAL_FUNDING,SUM(PHD_FUNDING) AS PHD_FUNDING,SUM(APPLY_FUNDING) AS APPLY_FUNDING,SUM(BONUS) AS BONUS FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID!='"+curSession.getId()+"' GROUP BY t.SESSION_ID");
        List<Object> columns = query.getResultList();

        for(Object column : columns) {
            TAMSDeptFunding deptFunding = new TAMSDeptFunding();

            Object[] fundings = (Object[]) column;
            deptFunding.setPlanFunding(String.valueOf(fundings[1]));
            deptFunding.setActualFunding(String.valueOf(fundings[2]));
            deptFunding.setPhdFunding(String.valueOf(fundings[3]));
            deptFunding.setApplyFunding(String.valueOf(fundings[4]));
            deptFunding.setBonus(String.valueOf(fundings[5]));

            deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));

            list.add(deptFunding);
        }

        return list;
    }
    @Override
    public List<TAMSDeptFunding> selectDepartmentCurrBySession(){
        List<TAMSDeptFunding> list = new ArrayList<>();

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT s.YEAR,s.TERM,t.*FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID ='"+curSession.getId()+"'ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
        List<Object> columns = query.getResultList();

        for (Object column : columns) {
            TAMSDeptFunding deptFunding = new TAMSDeptFunding();

            Object[] fundings = (Object[]) column;
            deptFunding.setPlanFunding(String.valueOf(fundings[3]));
            deptFunding.setActualFunding(String.valueOf(fundings[4]));
            deptFunding.setPhdFunding(String.valueOf(fundings[10]));
            deptFunding.setApplyFunding(String.valueOf(fundings[9]));
            deptFunding.setBonus(String.valueOf(fundings[11]));
            deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[5].toString())));
            deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[8].toString())));


            list.add(deptFunding);
        }
        return list;
    }
    @Override
    public List<TAMSDeptFunding> selectDepartmentPreBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT s.YEAR,s.TERM,t.*FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID !='"+curSession.getId()+"'ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
        List<Object> columns = query.getResultList();

        for (Object column : columns) {
            TAMSDeptFunding deptFunding = new TAMSDeptFunding();

            Object[] fundings = (Object[]) column;
            deptFunding.setPlanFunding(String.valueOf(fundings[3]));
            deptFunding.setActualFunding(String.valueOf(fundings[4]));
            deptFunding.setPhdFunding(String.valueOf(fundings[10]));
            deptFunding.setApplyFunding(String.valueOf(fundings[9]));
            deptFunding.setBonus(String.valueOf(fundings[11]));
            deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[5].toString())));
            deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[8].toString())));


            list.add(deptFunding);
        }
        return list;
    }
}
