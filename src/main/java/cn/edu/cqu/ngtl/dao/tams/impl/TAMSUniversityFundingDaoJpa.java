package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSUniversityFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSUniversityFunding;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by damei on 16/11/20.
 */

//添加注解
@Repository
@Component("TAMSUniversityFundingDaoJpa")
public class TAMSUniversityFundingDaoJpa implements TAMSUniversityFundingDao{
    //数据库查询变量
    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTSessionDao sessionDao;

    @Override
    public TAMSUniversityFunding getOneBySessionId(Integer sessionId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("sessionId", sessionId)
                )
        );
        QueryResults<TAMSUniversityFunding> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSUniversityFunding.class,criteria.build());
        return qr.getResults().get(0);
    }

    //显示当前学期学校经费
    @Override
    public List<TAMSUniversityFunding> selectCurrBySession() {
        List<TAMSUniversityFunding> list = new ArrayList<>();
        //先添加当前学期的内容
        TAMSUniversityFunding current = new TAMSUniversityFunding();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
//        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
//        Query qr = em.createNativeQuery("SELECT * FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'",TAMSUniversityFunding.class);
//        list = qr.getResultList();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("sessionId", curSession.getId())
                )
        );
        QueryResults<TAMSUniversityFunding> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSUniversityFunding.class,criteria.build());
        return qr.getResults()==null?null:(qr.getResults().size()==0?null:qr.getResults());
//        return list.size() != 0 ? list : null;
    }

    //显示历史学校经费
    @Override
    public List<TAMSUniversityFunding> selectPreBySession(){
        List<TAMSUniversityFunding> list = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query qr = em.createNativeQuery("SELECT * FROM TAMS_UNIVERSITY_FUNDING t JOIN UNITIME_SESSION s ON s.UNIQUEID = t.SESSION_ID AND t.SESSION_ID != '"+curSession.getId()+"' ORDER BY s.YEAR DESC, s.TERM DESC ",TAMSUniversityFunding.class);
        list = qr.getResultList();
        return list.size() != 0 ? list : null;
    }

    //批次经费查询
    @Override
    public List<TAMSUniversityFunding> selectUniFundPreByCondition(Map<String, String> conditions){
        List<TAMSUniversityFunding> list = new ArrayList<>();
        //当前学期
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        //int countNull = 0;
        //加通配符,若输入为空，则加通配符，否则不加
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
         //       countNull++;
            }
        }
        //if ( countNull != 7) {
            //Query qr = em.createNativeQuery("SELECT f.PLAN_FUNDING, f.APPLY_FUNDING, f.ACTUAL_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND ( (f.SESSION_ID = conditions.get("Session"))  OR (f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"') OR (f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"') OR (f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"') OR (f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"') OR (f.BONUS LIKE '"+conditions.get("Bonus")+"') OR (f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"') ) ");
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND   f.SESSION_ID LIKE '"+conditions.get("Session")+"'  AND  f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"'  AND  f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"'  AND  f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"'  AND  f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"'  AND  f.BONUS LIKE '"+conditions.get("Bonus")+"'  AND  f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"'  ",TAMSUniversityFunding.class);
            //System.out.print("SELECT f.SESSION_ID, f.PLAN_FUNDING, f.APPLY_FUNDING, f.ACTUAL_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND (  f.SESSION_ID LIKE '"+conditions.get("Session")+"'  OR  f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"'  OR  f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"'  OR  f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"'  OR  f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"'  OR  f.BONUS LIKE '"+conditions.get("Bonus")+"'  OR  f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' ) ");
            list = qr.getResultList();
        //}
        /*
        //若输入为空，则返回全部历史学期经费
        else {
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_UNIVERSITY_FUNDING t JOIN UNITIME_SESSION s ON s.UNIQUEID = t.SESSION_ID AND t.SESSION_ID != '"+curSession.getId()+"' ORDER BY s.YEAR DESC, s.TERM DESC ",TAMSUniversityFunding.class);
            list = qr.getResultList();
        }
        */
        return list.size() !=0 ? list : null;
    }

    @Override
    public boolean insertOneByEntity(TAMSUniversityFunding tamsUniversityFunding){
       // return KRADServiceLocator.getDataObjectService().save(tamsUniversityFunding) !=null;
        KRADServiceLocator.getDataObjectService().save(tamsUniversityFunding);
        return true;
    }

}
