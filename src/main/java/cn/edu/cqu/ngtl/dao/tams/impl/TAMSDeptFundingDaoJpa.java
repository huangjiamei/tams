package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
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
/**
 * Created by tangjing on 16-11-6.
 */
@Repository
@Component("TAMSDeptFundingDaoJpa")
public class TAMSDeptFundingDaoJpa implements TAMSDeptFundingDao {

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private UTDepartmentDao departmentDao;

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private IUserInfoService userInfoService;
    /*

    @Override
    public List<TAMSDeptFunding> selectCurrBySession() {
        List<TAMSDeptFunding> list = new ArrayList<>();

        //先添加当前学期的内容
        TAMSDeptFunding current = new TAMSDeptFunding();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT SUM(PLAN_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='" + curSession.getId() + "'");
        String planFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(ACTUAL_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='" + curSession.getId() + "'");
        String actualFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(PHD_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='" + curSession.getId() + "'");
        String phdFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(APPLY_FUNDING) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='" + curSession.getId() + "'");
        String applyFunding = String.valueOf(query.getResultList() != null ? query.getResultList().get(0) : null);
        query = em.createNativeQuery("SELECT SUM(BONUS) FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID='" + curSession.getId() + "'");
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
        Query query = em.createNativeQuery("SELECT  t.SESSION_ID,SUM(PLAN_FUNDING) AS PLAN_FUNDING,SUM(ACTUAL_FUNDING) AS ACTUAL_FUNDING,SUM(PHD_FUNDING) AS PHD_FUNDING,SUM(APPLY_FUNDING) AS APPLY_FUNDING,SUM(BONUS) AS BONUS FROM TAMS_DEPT_FUNDING t, UNITIME_SESSION s WHERE t.SESSION_ID = s.UNIQUEID AND t.SESSION_ID!='" + curSession.getId() +"' GROUP BY t.SESSION_ID,s.YEAR,s.TERM ORDER BY s.YEAR DESC, s.TERM DESC");
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

**/

    @Override
    public List<TAMSDeptFunding> selectDepartmentCurrBySession(){
        List<TAMSDeptFunding> list = new ArrayList<>();

        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        String departmentId = user.getDepartmentId().toString();

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query query = em.createNativeQuery("SELECT t.SESSION_ID,t.DEPARTMENT_ID,t.PLAN_FUNDING,t.APPLY_FUNDING,t.ACTUAL_FUNDING,t.PHD_FUNDING,t.BONUS,t.TRAVEL_SUBSIDY,s.YEAR,s.TERM FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID ='"+curSession.getId()+"' AND t.DEPARTMENT_ID = '"+departmentId+"' ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
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
        return list.size() !=0 ? list : null;
    }
    @Override
    public List<TAMSDeptFunding> selectDepartmentPreBySession() {
        String departmentId;
        User user = (User)GlobalVariables.getUserSession().retrieveObject("user");
        if(userInfoService.isAcademicAffairsStaff(user.getCode()) || userInfoService.isSysAdmin(user.getCode()))
            departmentId = "%";
        else
            departmentId = user.getDepartmentId().toString();

        List<TAMSDeptFunding> list = new ArrayList<>();

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query query = em.createNativeQuery("SELECT t.SESSION_ID,t.DEPARTMENT_ID,t.PLAN_FUNDING,t.APPLY_FUNDING,t.ACTUAL_FUNDING,t.PHD_FUNDING,t.BONUS,t.TRAVEL_SUBSIDY,s.YEAR,s.TERM FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION s ON t.SESSION_ID=s.UNIQUEID AND t.SESSION_ID !='"+curSession.getId()+"' AND t.DEPARTMENT_ID LIKE '"+departmentId+"' ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
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
        return list.size() != 0 ? list : null;
    }

    //批次经费：学校历史经费过滤器
    /*
    @Override
    public List<TAMSDeptFunding> getDeptFundPreByCondition(TAMSDeptFunding tamsDeptFunding){
        List<TAMSDeptFunding> list = new ArrayList<>();

        //获取历史学期的记录（除去当前学期）
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        Query query = em.createNativeQuery("SELECT u.YEAR, u.TERM, SUM(t.PLAN_FUNDING) AS PLAN_FUNDING,SUM(t.ACTUAL_FUNDING) AS ACTUAL_FUNDING,SUM(t.PHD_FUNDING) AS PHD_FUNDING,SUM(t.APPLY_FUNDING) AS APPLY_FUNDING,SUM(t.BONUS) AS BONUS , t.SESSION_ID FROM UNITIME_SESSION u JOIN TAMS_DEPT_FUNDINGS t ON u.UNIQUEID=t.SESSION_ID AND t.SESSION_ID !='"+curSession.getId()+"'GROUP BY t.SESSION_ID");
        List<Object> column = query.getResultList();

        //前台数据
        String planFunding = tamsDeptFunding.getPlanFunding();
        String applyFunding = tamsDeptFunding.getApplyFunding();
        String actualFunding = tamsDeptFunding.getActualFunding();
        String phdFunding = tamsDeptFunding.getPhdFunding();
        String bonus = tamsDeptFunding.getBonus();
        Query qr = em.createNativeQuery("SELECT u.YEAR, u.TERM FROM UNITIME_SESSION u WHERE u.UNIQUEID='"+tamsDeptFunding.getId()+"'");
        List<Object> qrr = qr.getResultList();

        TAMSDeptFunding deptPreFunding = new TAMSDeptFunding();

        for (Object qrrs : qrr) {
            Object[] t = (Object[]) qrrs;
            String year = t[0].toString();
            String term = t[1].toString();


            //遍历所获取的记录并与前台比较

            for (Object columns : column) {
                Object funding[] = (Object[]) columns;

                if (funding[0] ==year || funding[1]==term || funding[2] == planFunding || funding[3] == actualFunding || funding[4] == phdFunding || funding[5] == applyFunding || funding[6] == bonus) {
                    deptPreFunding.setSessionId(funding[7].toString());
                    deptPreFunding.setPlanFunding(funding[2].toString());
                    deptPreFunding.setActualFunding(funding[3].toString());
                    deptPreFunding.setPhdFunding(funding[4].toString());
                    deptPreFunding.setApplyFunding(funding[5].toString());
                    deptPreFunding.setBonus(funding[6].toString());
                }
            }

        }

        list.add(deptPreFunding);
        return list;
    }

*/

/*
    //批次经费：学校历史经费过滤器
    @Override
    public List<TAMSDeptFunding> getDeptFundPreByCondition(Map<String, String> conditions) {
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<TAMSDeptFunding> list = new ArrayList<>();
        int countNull = 0;
        //加通配符
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            } else
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        if (countNull != 7) {
            Query qr = em.createNativeQuery("SELECT u.YEAR, u.TERM, SUM(t.PLAN_FUNDING) AS PLAN_FUNDING, SUM(t.ACTUAL_FUNDING) AS ACTUAL_FUNDING, SUM(t.PHD_FUNDING) AS PHD_FUNDING, SUM(t.APPLY_FUNDING) AS APPLY_FUNDING, SUM(t.BONUS) AS BONUS FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION u ON (t.SESSION_ID = u.UNIQUEID) AND (t.SESSION_ID != '" + curSession.getId() + "') AND ((u.YEAR LIKE '" + conditions.get("") + "') OR (u.TERM LIKE '" + conditions.get("") + "') OR (SUM(t.PLAN_FUNDING) LIKE '" + conditions.get("") + "') OR (SUM(t.ACTUAL_FUNDING) LIKE '" + conditions.get("") + "') OR (SUM(t.APPLY_FUNDING) LIKE '" + conditions.get("") + "') OR (SUM(t.PHD_FUNDING) LIKE '" + conditions.get("") + "') OR (SUM(t.BONUS) LIKE '" + conditions.get("") + "')) GROUP BY t.SESSION_ID");
            List<Object> column = qr.getResultList();
            for(Object columns : column ){
                TAMSDeptFunding deptFunding = new TAMSDeptFunding();

                UTSession utSession = new UTSession();

                Object[] fundings = (Object[]) columns;

                utSession.setYear(fundings[0].toString());
                utSession.setTerm(fundings[1].toString());

                deptFunding.setSession(utSession);

                deptFunding.setPlanFunding(fundings[2].toString());
                deptFunding.setActualFunding(fundings[3].toString());
                deptFunding.setPhdFunding(fundings[4].toString());
                deptFunding.setApplyFunding(fundings[5].toString());
                deptFunding.setBonus(fundings[6].toString());

                list.add(deptFunding);
            }
        }
        return list;
    }

**/

    //学院当前经费过滤器
    @Override
    public List<TAMSDeptFunding> selectDeptFundCurrByCondition(Map<String, String> conditions) {
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        //此处不是模糊查询
        //若输入框为空，则加一个通配符使其等于任意值，若输入框不为空，则不加
        //int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
        //        countNull++;
            }
        }
        //若输入框不为空
        //if(countNull != 7) {
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID = '" + curSession.getId() + "' AND t.DEPARTMENT_ID LIKE '" + conditions.get("Dept") + "' AND t.PLAN_FUNDING LIKE '" + conditions.get("PlanFunding") + "' AND t.APPLY_FUNDING LIKE '" + conditions.get("ApplyFunding") + "' AND t.ACTUAL_FUNDING LIKE '" + conditions.get("ApprovalFunding") + "' AND t.PHD_FUNDING LIKE '" + conditions.get("PhdFunding") + "' AND t.BONUS LIKE '" + conditions.get("Bonus") + "' AND t.TRAVEL_SUBSIDY LIKE '" + conditions.get("TravelFunding") + "'", TAMSDeptFunding.class);
            List<TAMSDeptFunding> list = qr.getResultList();
            return list.size() !=0 ? list : null;
        }
        /*
        //若输入框都为空，则返回全部学院
        else {
            Query qr = em.createNativeQuery("SELECT * FROM TAMS_DEPT_FUNDING t WHERE t.SESSION_ID = '" + curSession.getId() + "'", TAMSDeptFunding.class);
            List<TAMSDeptFunding> list = qr.getResultList();
            return list;
        }
    }*/

    //学院历史经费过滤器
    @Override
    public List<TAMSDeptFunding> selectDeptFundPreByCondition(Map<String, String> conditions) {

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<TAMSDeptFunding> list = new ArrayList<>();
        //加通配符,若输入为空，则赋值%; 若不为空，则不变
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
            }
        }
        Query query = em.createNativeQuery("SELECT t.SESSION_ID,t.DEPARTMENT_ID,t.PLAN_FUNDING,t.APPLY_FUNDING,t.ACTUAL_FUNDING,t.PHD_FUNDING,t.BONUS,t.TRAVEL_SUBSIDY,s.YEAR,s.TERM FROM TAMS_DEPT_FUNDING t JOIN UNITIME_SESSION s ON t.SESSION_ID = s.UNIQUEID AND t.SESSION_ID != '"+curSession.getId()+"' AND s.UNIQUEID LIKE '"+conditions.get("dTimes")+"' AND t.DEPARTMENT_ID LIKE '"+conditions.get("deptId")+"' AND t.PLAN_FUNDING LIKE '"+conditions.get("dPreFunds")+"' AND t.APPLY_FUNDING LIKE '"+conditions.get("dApplyFunds")+"' AND t.ACTUAL_FUNDING LIKE '"+conditions.get("dApprovalFunds")+"' AND t.PHD_FUNDING LIKE '"+conditions.get("dAddingFunds")+"' AND t.BONUS LIKE '"+conditions.get("dRewardFunds")+"' AND t.TRAVEL_SUBSIDY LIKE '"+conditions.get("dTrafficFunds")+"' ORDER BY s.YEAR DESC ,s.TERM DESC ,t.DEPARTMENT_ID ASC");
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
        return list.size() != 0 ? list : null ;
    }


    public TAMSDeptFunding selectDeptFundsByDeptIdAndSession(Integer deptId,Integer sessionId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("departmentId", deptId),
                        equal("sessionId", sessionId)
                )
        );
        QueryResults<TAMSDeptFunding> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSDeptFunding.class,criteria.build());
        if(qr.getResults() != null && !qr.getResults().isEmpty())
            return qr.getResults().get(0);
        else
            return null;
    }

    public TAMSDeptFunding selectDeptFundsByDeptId(Integer deptId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("departmentId", deptId)
                )
        );
        QueryResults<TAMSDeptFunding> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSDeptFunding.class,criteria.build());
        if(qr.getResults() != null && !qr.getResults().isEmpty())
            return qr.getResults().get(0);
        else
            return null;
    }


    public boolean saveOneByEntity(TAMSDeptFunding tamsDeptFunding){

        return KradDataServiceLocator.getDataObjectService().save(tamsDeptFunding)!=null;
    }


    public List<TAMSDeptFunding> selectByDeptAndSessionId (List<Integer> depts, Integer sessionId) {
        List<TAMSDeptFunding> tamsDeptFundings = new ArrayList<>();
        for(Integer deptId : depts) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("departmentId", deptId),
                            equal("sessionId", sessionId)
                    )
            );
            QueryResults<TAMSDeptFunding> qr = KradDataServiceLocator.getDataObjectService().findMatching(TAMSDeptFunding.class, criteria.build());
            if (qr.getResults() != null && !qr.getResults().isEmpty())
                tamsDeptFundings.add(qr.getResults().get(0));
            else
                tamsDeptFundings.add(null);
        }
        return tamsDeptFundings;
    }



}
