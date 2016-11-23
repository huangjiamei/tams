package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSUniversityFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSUniversityFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

    //显示当前学期学校经费
    @Override
    public List<TAMSUniversityFunding> selectCurrBySession() {
        List<TAMSUniversityFunding> list = new ArrayList<>();
        //先添加当前学期的内容
        TAMSUniversityFunding current = new TAMSUniversityFunding();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();

        Query qr = em.createNativeQuery("SELECT t.PLAN_FUNDING FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String PlanFunding = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        qr = em.createNativeQuery("SELECT t.APPLY_FUNDING FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String ApplyFunding = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        qr = em.createNativeQuery("SELECT t.ACTUAL_FUNDING FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String ActualFunding = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        qr = em.createNativeQuery("SELECT t.PHD_FUNDING FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String PhdFunding = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        qr = em.createNativeQuery("SELECT t.BONUS FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String Bonus = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        qr = em.createNativeQuery("SELECT t.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING t WHERE t.SESSION_ID = '"+curSession.getId()+"'");
        String TravelFunding = String.valueOf(qr.getResultList() != null ? qr.getResultList().get(0) : null);

        current.setPlanFunding(PlanFunding);
        current.setApplyFunding(ApplyFunding);
        current.setActualFunding(ActualFunding);
        current.setPhdFunding(PhdFunding);
        current.setBonus(Bonus);
        current.setTravelSubsidy(TravelFunding);

        current.setUtSession(curSession);

        list.add(current);
        return list;

    }

    //显示历史学校经费
    @Override
    public List<TAMSUniversityFunding> selectPreBySession(){
        List<TAMSUniversityFunding> list = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query qr = em.createNativeQuery("SELECT t.SESSION_ID, t.PLAN_FUNDING, t.APPLY_FUNDING, t.ACTUAL_FUNDING, t.PHD_FUNDING, t.BONUS, t.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING t JOIN UNITIME_SESSION s ON s.UNIQUEID = t.SESSION_ID AND t.SESSION_ID != '"+curSession.getId()+"' ORDER BY s.YEAR DESC, s.TERM DESC ");
        List<Object> column = qr.getResultList();
        for(Object columns : column){
            TAMSUniversityFunding tamsUniversityFunding = new TAMSUniversityFunding();
            Object[] fundings = (Object[]) columns;

            tamsUniversityFunding.setPlanFunding(String.valueOf(fundings[1]));
            tamsUniversityFunding.setApplyFunding(String.valueOf(fundings[2]));
            tamsUniversityFunding.setActualFunding(String.valueOf(fundings[3]));
            tamsUniversityFunding.setPhdFunding(String.valueOf(fundings[4]));
            tamsUniversityFunding.setBonus(String.valueOf(fundings[5]));
            tamsUniversityFunding.setTravelSubsidy(String.valueOf(fundings[6]));

            tamsUniversityFunding.setUtSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));

            list.add(tamsUniversityFunding);
        }
        return list;
    }

    //批次经费查询
    @Override
    public List<TAMSUniversityFunding> selectUniFundPreByCondition(Map<String, String> conditions){
        List<TAMSUniversityFunding> list = new ArrayList<>();
        //当前学期
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        int countNull = 0;
        //加通配符
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            } else
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        if ( countNull != 7) {
            //Query qr = em.createNativeQuery("SELECT f.PLAN_FUNDING, f.APPLY_FUNDING, f.ACTUAL_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND ( (f.SESSION_ID = conditions.get("Session"))  OR (f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"') OR (f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"') OR (f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"') OR (f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"') OR (f.BONUS LIKE '"+conditions.get("Bonus")+"') OR (f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"') ) ");
            Query qr = em.createNativeQuery("SELECT f.SESSION_ID, f.PLAN_FUNDING, f.APPLY_FUNDING, f.ACTUAL_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND (  f.SESSION_ID LIKE '"+conditions.get("Session")+"'  AND  f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"'  AND  f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"'  AND  f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"'  AND  f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"'  AND  f.BONUS LIKE '"+conditions.get("Bonus")+"'  AND  f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' ) ");
            //System.out.print("SELECT f.SESSION_ID, f.PLAN_FUNDING, f.APPLY_FUNDING, f.ACTUAL_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING f JOIN UNITIME_SESSION s ON f.SESSION_ID != '"+curSession.getId()+"' AND f.SESSION_ID = s.UNIQUEID  AND (  f.SESSION_ID LIKE '"+conditions.get("Session")+"'  OR  f.PLAN_FUNDING LIKE '"+conditions.get("PlanFunding")+"'  OR  f.APPLY_FUNDING LIKE '"+conditions.get("ApplyFunding")+"'  OR  f.ACTUAL_FUNDING LIKE '"+conditions.get("ApprovalFunding")+"'  OR  f.PHD_FUNDING LIKE '"+conditions.get("AddFunding")+"'  OR  f.BONUS LIKE '"+conditions.get("Bonus")+"'  OR  f.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' ) ");
            List<Object> column = qr.getResultList();
            for(Object columns : column){
                TAMSUniversityFunding universityFunding = new TAMSUniversityFunding();
                Object[] fundings = (Object[]) columns;
                universityFunding.setPlanFunding(fundings[1].toString());
                universityFunding.setApplyFunding(fundings[2].toString());
                universityFunding.setActualFunding(fundings[3].toString());
                universityFunding.setPhdFunding(fundings[4].toString());
                universityFunding.setBonus(fundings[5].toString());
                universityFunding.setTravelSubsidy(fundings[6].toString());

                universityFunding.setUtSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));

                list.add(universityFunding);
            }
        }
        //若输入为空，则返回全部历史学期经费
        else {
            Query qr = em.createNativeQuery("SELECT t.SESSION_ID, t.PLAN_FUNDING, t.APPLY_FUNDING, t.ACTUAL_FUNDING, t.PHD_FUNDING, t.BONUS, t.TRAVEL_SUBSIDY FROM TAMS_UNIVERSITY_FUNDING t JOIN UNITIME_SESSION s ON s.UNIQUEID = t.SESSION_ID AND t.SESSION_ID != '"+curSession.getId()+"' ORDER BY s.YEAR DESC, s.TERM DESC ");
            List<Object> column = qr.getResultList();
            for(Object columns : column){
                TAMSUniversityFunding tamsUniversityFunding = new TAMSUniversityFunding();
                Object[] fundings = (Object[]) columns;

                tamsUniversityFunding.setPlanFunding(String.valueOf(fundings[1]));
                tamsUniversityFunding.setApplyFunding(String.valueOf(fundings[2]));
                tamsUniversityFunding.setActualFunding(String.valueOf(fundings[3]));
                tamsUniversityFunding.setPhdFunding(String.valueOf(fundings[4]));
                tamsUniversityFunding.setBonus(String.valueOf(fundings[5]));
                tamsUniversityFunding.setTravelSubsidy(String.valueOf(fundings[6]));

                tamsUniversityFunding.setUtSession(sessionDao.getUTSessionById(Integer.valueOf(fundings[0].toString())));

                list.add(tamsUniversityFunding);
            }
        }
        return list;
    }
}
