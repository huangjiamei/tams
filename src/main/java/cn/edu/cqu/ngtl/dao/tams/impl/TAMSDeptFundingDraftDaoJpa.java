package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDraftDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFundingDraft;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNotNull;

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

    //获取当前学期学院的草稿经费
    @Override
    public List<TAMSDeptFunding> selectDepartmentCurrDraftBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query query = em.createNativeQuery("SELECT t.SESSION_ID,t.DEPARTMENT_ID,t.PLAN_FUNDING,t.APPLY_FUNDING,t.ACTUAL_FUNDING,t.PHD_FUNDING,t.BONUS,t.TRAVEL_SUBSIDY,s.YEAR,s.TERM FROM TAMS_DEPT_FUNDING_DRAFT t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID ='"+curSession.getId()+"'ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
        List<Object> columns = query.getResultList();
        for (Object column : columns) {
            TAMSDeptFunding deptFunding = new TAMSDeptFunding();
            Object[] fundings = (Object[]) column;
            deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));
            deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[1].toString())));
            deptFunding.setDepartmentId(((BigDecimal)fundings[1]).intValue());
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

    @Override
    public List<TAMSDeptFunding> selectDeptFundDraftCurrByCondition(Map<String, String > conditions) {
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        List<TAMSDeptFunding> list = new ArrayList<>();
        //若输入框为空，则加通配符，若输入框不为空，则不加
        //int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
        //        countNull++;
            }
        }
        //若输入框不为空
        //if(countNull != 7) {
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_DEPT_FUNDING_DRAFT t WHERE t.SESSION_ID = '" + curSession.getId() + "' AND t.DEPARTMENT_ID LIKE '" + conditions.get("Dept") + "' AND t.PLAN_FUNDING LIKE '" + conditions.get("PlanFunding") + "' AND t.APPLY_FUNDING LIKE '" + conditions.get("ApplyFunding") + "' AND t.ACTUAL_FUNDING LIKE '" + conditions.get("ApprovalFunding") + "' AND t.PHD_FUNDING LIKE '" + conditions.get("PhdFunding") + "' AND t.BONUS LIKE '" + conditions.get("Bonus") + "' AND t.TRAVEL_SUBSIDY LIKE '" + conditions.get("TravelFunding") + "'");
            List<Object> columns = qr.getResultList();
            for (Object column : columns) {
                TAMSDeptFunding deptFunding = new TAMSDeptFunding();
                Object[] fundings = (Object[]) column;
                deptFunding.setSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[6].toString())));
                deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[3].toString())));
                deptFunding.setDepartmentId(((BigDecimal)fundings[3]).intValue());
                deptFunding.setPlanFunding(String.valueOf(fundings[1]));
                deptFunding.setApplyFunding(String.valueOf(fundings[7]));
                deptFunding.setActualFunding(String.valueOf(fundings[2]));
                deptFunding.setPhdFunding(String.valueOf(fundings[8]));
                deptFunding.setBonus(String.valueOf(fundings[9]));
                deptFunding.setTravelSubsidy(String.valueOf(fundings[10]));
                list.add(deptFunding);
            }
            return list.size() !=0 ? list : null;
        }
        /*
        //若输入框都为空，则返回全部学院
        else {
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_DEPT_FUNDING_DRAFT t WHERE t.SESSION_ID = '" + curSession.getId() + "'");
            List<TAMSDeptFunding> columns = qr.getResultList();
            for (Object column : columns) {
                TAMSDeptFunding deptFunding = new TAMSDeptFunding();
                Object[] fundings = (Object[]) column;
                deptFunding.setDepartment(departmentDao.getUTDepartmentById(Integer.valueOf(fundings[3].toString())));
                deptFunding.setDepartmentId(((BigDecimal)fundings[3]).intValue());
                deptFunding.setPlanFunding(String.valueOf(fundings[1]));
                deptFunding.setApplyFunding(String.valueOf(fundings[7]));
                deptFunding.setActualFunding(String.valueOf(fundings[2]));
                deptFunding.setPhdFunding(String.valueOf(fundings[8]));
                deptFunding.setBonus(String.valueOf(fundings[9]));
                deptFunding.setTravelSubsidy(String.valueOf(fundings[10]));
                list.add(deptFunding);
            }
            return list;
        }

    }*/

    @Override
    public TAMSDeptFundingDraft selectDeptDraftFundsByDeptIdAndSession(Integer deptId, Integer sessionId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("departmentId", deptId),
                        equal("sessionId", sessionId)
                )
        );
        QueryResults<TAMSDeptFundingDraft> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSDeptFundingDraft.class,criteria.build());
        return qr.getResults().get(0);
    }


    @Override
    public boolean saveOneByEntity(TAMSDeptFundingDraft tamsDeptDraftFunding){

        return KradDataServiceLocator.getDataObjectService().save(tamsDeptDraftFunding)!=null;
    }

    //批量添加
    @Override
    public boolean saveBatchByEntities(List<TAMSDeptFundingDraft> tamsDeptFundingDrafts){
        for(TAMSDeptFundingDraft per : tamsDeptFundingDrafts){
            KradDataServiceLocator.getDataObjectService().save(per);
        }
        return true;
    }


}
