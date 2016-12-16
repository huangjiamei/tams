package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowRoleFunctionDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusRDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatusR;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DetailFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-1.
 */
@Repository
@Component("TAMSTaDaoJpa")
public class TAMSTaDaoJpa implements TAMSTaDao {

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private TAMSWorkflowStatusRDao workflowStatusRDao;

    @Autowired
    private TAMSWorkflowRoleFunctionDao workflowRoleFunctionDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Autowired
    private UTClassInstructorDao utClassInstructorDao;

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    //根据助教id查询
    @Override
    public List<TAMSTa> selectByTaId(String taId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("taId", taId)
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        if(qr.getResults() != null || qr.getResults().size() != 0)
            return qr.getResults();
        else
            return null;
    }

    @Override
    public TAMSTa selectById(String id) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("id", id)
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        if(qr.getResults() != null || qr.getResults().size() != 0)
            return qr.getResults().get(0);
        else
            return null;
    }

    //系统管理员和教务处管理员用户查询全校所有助教
    @Override
    public List<TAMSTa> selectAll() {
        //return KRADServiceLocator.getDataObjectService().findAll(TAMSTa.class).getResults();
        //查询当前学期的所有助教
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("sessionId",curSession.getId()));
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        return qr.getResults();
    }


    //先根据教师id查询所有的课程（classInstructorDao.selectClassIdsByInstructorId(uId)），再根据批量课程id查出所有的助教
    @Override
    public List<TAMSTa> selectByClassIds(List<Object> classIds) {
        List<TAMSTa> tas = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        for(Object classId : classIds) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("taClassId", classId),
                            equal("sessionId",curSession.getId())
                    )
            );
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            tas.addAll(qr.getResults());
        }

        return tas.isEmpty()?null:tas;
    }



/*
    //根据Instructor 统一认证号获取所在学院的id
    @Override
    public String selectCollegeStaffDeptId(String uId){
        Query qr = em.createNativeQuery("SELECT DEPARTMENT_ID FROM UNITIME_INSTRUCTOR WHERE ID_NUMBER = '"+ uId + "' ");
        return qr.getResultList().toString();
    }
    */


    //二级管理员用户直接根据学院id查询所有助教
    public List<TAMSTa> selectByDeptId(String deptId){
        /*
        //获取用户所在的学院id
        UserSession userSession = GlobalVariables.getUserSession();
        User user = (User)userSession.retrieveObject("user");
        */
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClass.courseOffering.course.departmentId", deptId),
                        equal("sessionId", curSession.getId())
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        return qr.getResults().isEmpty()? null : qr.getResults();
    }

    //根据student_id查询class_id
    @Override
    public List<Object> selectClassIdsByStudentId(String stuId) {
        List<Object> list = new ArrayList<>();
        em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query query = em.createNativeQuery("SELECT TA_CLASS FROM TAMS_TA t WHERE t.TA_ID='" + stuId + "'");
        list = query.getResultList();
        return list;
    }

    //根据批量的id查询
    @Override
    public List<TAMSTa> selectBatchByIds(List<String> ids) {
        List<TAMSTa> tas = new ArrayList<>();

        for(String id : ids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("id", id));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            if(qr.getResults().size() != 0)
                tas.add(qr.getResults().get(0));
        }

        return tas.isEmpty()?null:tas;
    }

    //根据批量的student_id查询
    @Override
    public List<TAMSTa> selectBatchByTaIds(List<String> ids) {
        List<TAMSTa> tas = new ArrayList<>();

        for (String id : ids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("taId", id));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            if(qr.getResults().size() != 0)
                tas.add(qr.getResults().get(0));
        }

        return tas.isEmpty() ? null : tas;
    }

    @Override
    public boolean updateByEntity(TAMSTa ta) {
        return KRADServiceLocator.getDataObjectService().save(ta) != null;
    }

    @Override
    public TAMSTa selectByStudentIdAndClassId(String stuId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClassId", classId),
                        equal("taId",stuId)
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );

        return qr.getResults().size() == 0 ? null : qr.getResults().get(0);
    }



    @Override
    public boolean insertByEntity(TAMSTa newTa) {

        return KRADServiceLocator.getDataObjectService().save(newTa) != null;

    }


    //在classinformation表及其他表里面找出所有课程相关信息
    @Override
    public List<WorkBenchViewObject> selectAllCourseInfoByIds(List<Object> ids) {
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<WorkBenchViewObject> list = new ArrayList<>(ids.size());
        em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        for(Object id : ids){
            Query qr = em.createNativeQuery("SELECT c.DEPT_NAME, c.COURSE_NAME, c.COURSE_CODE, c.CLASS_NBR, i.NAME, SUM(t.ELAPSED_TIME) AS ELAPSED_TIME, p.NAME, c.STATUS, c.UNIQUEID FROM UNITIME_CLASS_INFORMATION c JOIN UNITIME_CLASS_INSTRUCTOR uci ON c.UNIQUEID=uci.CLASS_ID AND c.UNIQUEID='"+ id +"' AND c.SESSION_ID = '"+curSession.getId()+"' JOIN UNITIME_INSTRUCTOR i ON uci.INSTRUCTOR_ID = i.UNIQUEID JOIN TAMS_TEACH_CALENDAR t ON t.CLASS_ID = c.UNIQUEID JOIN CM_PROGRAM_COURSE cp ON c.COURSE_ID = cp.COURSE_ID JOIN CM_PROGRAM p ON cp.PROGRAM_ID = p.UNIQUEID GROUP BY c.DEPT_NAME, c.COURSE_NAME, c.COURSE_CODE, c.CLASS_NBR, i.NAME, p.NAME, c.STATUS, c.UNIQUEID");
            List<Object> columns = qr.getResultList();
            for(Object column : columns) {
                Object[] informations = (Object[]) column;
                WorkBenchViewObject workbenchviewobject = new WorkBenchViewObject();
                workbenchviewobject.setDept(informations[0].toString());
                workbenchviewobject.setCourseName(informations[1].toString());
                workbenchviewobject.setCourseCode(informations[2].toString());
                workbenchviewobject.setClassNbr(informations[3].toString());
                workbenchviewobject.setTeacher(informations[4].toString());
                workbenchviewobject.setHours(informations[5].toString());
                workbenchviewobject.setMajor(informations[6].toString());
                workbenchviewobject.setStatus(informations[7].toString());
                workbenchviewobject.setClassId(informations[8].toString());
                list.add(workbenchviewobject);
            }
        }
        return list;
    }


    @Override
    public List<TAMSTa> selectByClassId(String classId){

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClassId", classId)
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );

        return qr.getResults().size() == 0 ? null : qr.getResults();


    }

    //根据条件查询助教列表  //TODO 效率改进,特别是教师姓名的查询部分
    public List<TaInfoViewObject> getTaInfoByConditions(Map<String, String> conditions){
        List<TaInfoViewObject> taInfoViewObjects = new ArrayList<>();
        UTSession curSession = utSessionDao.getCurrentSession();
        Query query =em.createNativeQuery("SELECT s.NAME,s.UNIQUEID,t.TA_TYPE,co.NAME,co.CODE,cl.CLASS_NBR,t.EVALUATION,t.STUDENT_EVALUATION,t.OUTSTANDING_TA,cl.UNIQUEID,ws.WORKFLOW_STATUS,t.OUTSTANDING_TA FROM TAMS_TA t JOIN UNITIME_STUDENT s ON T .TA_ID = s.UNIQUEID AND t .SESSION_ID = '"+curSession.getId()+
                "' AND s.UNIQUEID LIKE '"+conditions.get("taId")+
                "' AND s. NAME LIKE '"+conditions.get("taName")+
                "' AND t .TA_TYPE LIKE '"+conditions.get("taDegree")+
//                "' AND t .EVALUATION LIKE '"+conditions.get("taTeacherAppraise")+
//                "' AND t .STUDENT_EVALUATION LIKE '"+conditions.get("taStuAppraise")+
                "' AND t .OUTSTANDING_TA LIKE '"+conditions.get("taStatus") +
                "'JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID " +
                "JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID JOIN TAMS_WORKFLOW_STATUS ws ON T.STATUS = ws.\"ORDER\"" +
                "AND co.NAME LIKE '"+conditions.get("taCourseName")+
                "' AND co.CODE LIKE '"+conditions.get("taCourseCode")+"' AND ws.WORKFLOW_FUNCTION_ID ='2" +
                "' AND T.TA_CLASS IN (SELECT ci.CLASS_ID FROM UNITIME_CLASS_INSTRUCTOR ci WHERE ci.INSTRUCTOR_ID IN (SELECT i.UNIQUEID FROM UNITIME_INSTRUCTOR i WHERE i.NAME like '" + conditions.get("taTeacherName")+"'))");



        List<Object> columns = query.getResultList();
        if(columns==null||columns.size()==0){
            taInfoViewObjects.add(new TaInfoViewObject());
            return taInfoViewObjects;
        }
        for(Object column : columns) {
            Object[] informations = (Object[]) column;
            String classId = informations[9].toString();
            List<UTClassInstructor> classInstructors = utClassInstructorDao.selectByClassId(classId);
            String insName = "";
            for(UTClassInstructor utClassInstructor : classInstructors){
                insName+=utClassInstructor.getUtInstructor().getName()+" ";
            }
            TaInfoViewObject taInfoViewObject = new TaInfoViewObject();
            taInfoViewObject.setTaName(informations[0].toString());
            taInfoViewObject.setTaIDNumber(informations[1].toString());
            taInfoViewObject.setTaMasterMajorName(informations[2].toString());
            taInfoViewObject.setCourseName(informations[3].toString());
            taInfoViewObject.setCourseCode(informations[4].toString());
            taInfoViewObject.setClassNumber(informations[5].toString());
            taInfoViewObject.setTeacherAppraise(informations[6]==null?"未评价":informations[6].toString());
            taInfoViewObject.setStuAppraise(informations[7]==null?"未评价":informations[7].toString());
            taInfoViewObject.setStatus(informations[10].toString());
            taInfoViewObject.setStatusId(informations[11].toString());
            taInfoViewObject.setInstructorName(insName);
            taInfoViewObjects.add(taInfoViewObject);
        }
        return taInfoViewObjects;
    }

    //查询助教经费信息
    public List<TaFundingViewObject> selectTaFundByCondition(Map<String, String> conditions) {
        //加通配符，若为空，则加一个通配符；若输入框为资金，则不加；若输入框为除资金外的字段，则前后加通配符
        int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            }
            else if(entry.getKey() == "Name" )
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "Number")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "Type" )
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "CourseName")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "CourseCode" )
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<TaFundingViewObject> list = new ArrayList<>();
        em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        //if(countNull != 12) {
            Query qr = em.createNativeQuery(" SELECT d.NAME, s.UNIQUEID, s.NAME, co.NAME, co.CODE, t.TA_TYPE, t.ASSIGNED_FUNDING, t.PHD_FUNDING, t.TRAVEL_SUBSIDY, t.BONUS, t.TA_CLASS, i.NAME from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '"+curSession.getId()+"' AND s.UNIQUEID LIKE '"+conditions.get("Number")+"' AND s.NAME LIKE '"+conditions.get("Name")+"' AND t.TA_TYPE LIKE '"+conditions.get("Type")+"' AND t.ASSIGNED_FUNDING LIKE '"+conditions.get("AssignedFunding")+"' AND t.PHD_FUNDING LIKE '"+conditions.get("PhdFunding")+"' AND t.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' AND t.BONUS LIKE '"+conditions.get("Bonus")+"' JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '"+conditions.get("CourseName")+"' AND co.CODE LIKE '"+conditions.get("CourseCode")+"' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.UNIQUEID LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"' ");
            List<Object> columns = qr.getResultList();
            for(Object column : columns) {
                Object[] tafunding = (Object[]) column;
                TaFundingViewObject taFundingViewObject = new TaFundingViewObject();
                taFundingViewObject.setDepartmentName(tafunding[0].toString());
                taFundingViewObject.setStuId(tafunding[1].toString());
                taFundingViewObject.setTaName(tafunding[2].toString());
                taFundingViewObject.setCourseName(tafunding[3].toString());
                taFundingViewObject.setCourseCode(tafunding[4].toString());
                taFundingViewObject.setTaType("博士");
                taFundingViewObject.setAssignedFunding(tafunding[6].toString());
                taFundingViewObject.setPhdFunding(tafunding[7].toString());
                taFundingViewObject.setTravelSubsidy(tafunding[8].toString());
                taFundingViewObject.setBonus(tafunding[9].toString());
                taFundingViewObject.setClassNbr(tafunding[10].toString()); //converter中会用到
                taFundingViewObject.setInstrucotrName(tafunding[11].toString());
                list.add(taFundingViewObject);
            }
            return list.size() != 0 ? list : null;
       }
        /*
        //若输入为空，则返回所有助教经费
        else{
            Query qr = em.createNativeQuery(" SELECT d.NAME, s.UNIQUEID, s.NAME, co.NAME, co.CODE, t.TA_TYPE, t.ASSIGNED_FUNDING, t.PHD_FUNDING, t.TRAVEL_SUBSIDY, t.BONUS from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '"+curSession.getId()+"' AND s.UNIQUEID LIKE '"+conditions.get("Number")+"' AND s.NAME LIKE '"+conditions.get("Name")+"' AND t.TA_TYPE LIKE '"+conditions.get("Type")+"' AND t.ASSIGNED_FUNDING LIKE '"+conditions.get("AssignedFunding")+"' AND t.PHD_FUNDING LIKE '"+conditions.get("PhdFunding")+"' AND t.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' AND t.BONUS LIKE '"+conditions.get("Bonus")+"' JOIN UNITIME_DEPARTMENT d ON s.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '"+conditions.get("CourseName")+"' AND co.CODE LIKE '"+conditions.get("CourseCode")+"' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"' ");
            List<Object> columns = qr.getResultList();
            for(Object column : columns) {
                Object[] tafunding = (Object[]) column;
                TaFundingViewObject taFundingViewObject = new TaFundingViewObject();
                taFundingViewObject.setDepartmentName(tafunding[0].toString());
                taFundingViewObject.setStuId(tafunding[1].toString());
                taFundingViewObject.setTaName(tafunding[2].toString());
                taFundingViewObject.setCourseName(tafunding[3].toString());
                taFundingViewObject.setCourseCode(tafunding[4].toString());
                taFundingViewObject.setTaType(tafunding[5].toString());
                taFundingViewObject.setAssignedFunding(tafunding[6].toString());
                taFundingViewObject.setPhdFunding(tafunding[7].toString());
                taFundingViewObject.setTravelSubsidy(tafunding[8].toString());
                taFundingViewObject.setBonus(tafunding[9].toString());
                list.add(taFundingViewObject);
            }
            return list;
        }
    }*/

    //根据条件查询经费明细
    @Override
    public List<DetailFundingViewObject> selectDetailFundByCondition(Map<String, String> conditions) {
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<DetailFundingViewObject> list = new ArrayList<>();
        //此处除资金外，其他都是前后加通配符
        //若输入框为空，则加通配符；若输入框为资金，则不加；若输入框为除资金外的字段，则前后加通配符
        int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            }
            else if(entry.getKey() == "Name" )
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "Number")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "Bank")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "BankNbr")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "IdCard")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "CourseName")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "CourseCode")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        //若输入不为空，查询
        List<Object> columns = new ArrayList<>();
        //if(countNull != 21) {
            if (curSession.getTerm().equals("春")) {
                Query qr = em.createNativeQuery("SELECT s.UNIQUEID, s.NAME, s.ID_NUMBER, co.NAME, co.CODE, t.MONTH_3, t.MONTH_4, t.MONTH_5, t.MONTH_6, t.MONTH_7, t.MONTH_8, t.TA_CLASS, i.NAME from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '" + curSession.getId() + "' AND s.UNIQUEID LIKE '" + conditions.get("Number") + "' AND s.NAME LIKE '" + conditions.get("Name") + "' AND s.ID_NUMBER LIKE '" + conditions.get("IdCard") + "' AND t.MONTH_3 LIKE '" + conditions.get("month3") + "' AND t.MONTH_4 LIKE '" + conditions.get("month4") + "' AND t.MONTH_5 LIKE '" + conditions.get("month5") + "' AND t.MONTH_6 LIKE '" + conditions.get("month6") + "' AND t.MONTH_7 LIKE '" + conditions.get("month7") + "' AND t.MONTH_8 LIKE '" + conditions.get("month8") + "'  JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID  JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '" + conditions.get("CourseName") + "' AND co.CODE LIKE '" + conditions.get("CourseCode") + "' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"' ");
                columns = qr.getResultList();
                for(Object column : columns) {
                    Object[] detailFunding = (Object[]) column;
                    DetailFundingViewObject detailFundingViewObject = new DetailFundingViewObject();
                    detailFundingViewObject.setStuId(detailFunding[0].toString());
                    detailFundingViewObject.setTaName(detailFunding[1].toString());
                    detailFundingViewObject.setBankName("缺失");
                    detailFundingViewObject.setBankId("缺失");
                    detailFundingViewObject.setIdentity(detailFunding[2].toString());
                    detailFundingViewObject.setCourseName(detailFunding[3].toString());
                    detailFundingViewObject.setCourseCode(detailFunding[4].toString());
                    detailFundingViewObject.setMonthlySalary3(detailFunding[5].toString());
                    detailFundingViewObject.setMonthlySalary4(detailFunding[6].toString());
                    detailFundingViewObject.setMonthlySalary5(detailFunding[7].toString());
                    detailFundingViewObject.setMonthlySalary6(detailFunding[8].toString());
                    detailFundingViewObject.setMonthlySalary7(detailFunding[9].toString());
                    detailFundingViewObject.setMonthlySalary8(detailFunding[10].toString());
                    detailFundingViewObject.setClassNbr(detailFunding[11].toString()); //converter中使用
                    detailFundingViewObject.setInstructorName(detailFunding[12].toString());
                    int total = (
                            Integer.parseInt(detailFunding[5].toString()) + Integer.parseInt(detailFunding[6].toString()) +
                                    Integer.parseInt(detailFunding[7].toString()) + Integer.parseInt(detailFunding[8].toString()) +
                                    Integer.parseInt(detailFunding[9].toString()) + Integer.parseInt(detailFunding[10].toString())
                    );
                    detailFundingViewObject.setTotal(String.valueOf(total));
                    list.add(detailFundingViewObject);
                }
            }
            else if (curSession.getTerm().equals("秋")) {
                Query qr = em.createNativeQuery("SELECT s.UNIQUEID, s.NAME, s.ID_NUMBER, co.NAME, co.CODE, t.MONTH_9, t.MONTH_10, t.MONTH_11, t.MONTH_12, t.MONTH_1, t.MONTH_2, t.TA_CLASS, i.NAME from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '" + curSession.getId() + "' AND s.UNIQUEID LIKE '" + conditions.get("Number") + "' AND s.NAME LIKE '" + conditions.get("Name") + "' AND s.ID_NUMBER LIKE '" + conditions.get("IdCard") + "' AND t.MONTH_9 LIKE '" + conditions.get("month9") + "' AND t.MONTH_10 LIKE '" + conditions.get("month10") + "' AND t.MONTH_11 LIKE '" + conditions.get("month11") + "' AND t.MONTH_12 LIKE '" + conditions.get("month12") + "' AND t.MONTH_1 LIKE '" + conditions.get("month1") + "' AND t.MONTH_2 LIKE '" + conditions.get("month2") + "' JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '" + conditions.get("CourseName") + "' AND co.CODE LIKE '" + conditions.get("CourseCode") + "' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"'");
                columns = qr.getResultList();
                for(Object column : columns) {
                    Object[] detailFunding = (Object[]) column;
                    DetailFundingViewObject detailFundingViewObject = new DetailFundingViewObject();
                    detailFundingViewObject.setStuId(detailFunding[0].toString());
                    detailFundingViewObject.setTaName(detailFunding[1].toString());
                    detailFundingViewObject.setBankName("缺失");
                    detailFundingViewObject.setBankId("缺失");
                    detailFundingViewObject.setIdentity(detailFunding[2].toString());
                    detailFundingViewObject.setCourseName(detailFunding[3].toString());
                    detailFundingViewObject.setCourseCode(detailFunding[4].toString());
                    detailFundingViewObject.setMonthlySalary9(detailFunding[5].toString());
                    detailFundingViewObject.setMonthlySalary10(detailFunding[6].toString());
                    detailFundingViewObject.setMonthlySalary11(detailFunding[7].toString());
                    detailFundingViewObject.setMonthlySalary12(detailFunding[8].toString());
                    detailFundingViewObject.setMonthlySalary1(detailFunding[9].toString());
                    detailFundingViewObject.setMonthlySalary2(detailFunding[10].toString());
                    detailFundingViewObject.setClassNbr(detailFunding[11].toString()); //converter中使用
                    detailFundingViewObject.setInstructorName(detailFunding[12].toString());
                    int total = (
                            Integer.parseInt(detailFunding[5].toString()) + Integer.parseInt(detailFunding[6].toString()) +
                                    Integer.parseInt(detailFunding[7].toString()) + Integer.parseInt(detailFunding[8].toString()) +
                                    Integer.parseInt(detailFunding[9].toString()) + Integer.parseInt(detailFunding[10].toString())
                    );
                    detailFundingViewObject.setTotal(String.valueOf(total));
                    list.add(detailFundingViewObject);
                }
            }
            return list.size() != 0 ? list : null;
        }
        /*
        //若输入为空，则返回全部经费明细
        else {
            if (curSession.getTerm().equals("春")) {
                Query qr = em.createNativeQuery("SELECT s.UNIQUEID, s.NAME, s.ID_NUMBER, co.NAME, co.CODE, t.MONTH_3, t.MONTH_4, t.MONTH_5, t.MONTH_6, t.MONTH_7, t.MONTH_8 from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '" + curSession.getId() + "' AND s.UNIQUEID LIKE '" + conditions.get("Number") + "' AND s.NAME LIKE '" + conditions.get("Name") + "' AND s.ID_NUMBER LIKE '" + conditions.get("IdCard") + "' AND t.MONTH_3 LIKE '" + conditions.get("month3") + "' AND t.MONTH_4 LIKE '" + conditions.get("month4") + "' AND t.MONTH_5 LIKE '" + conditions.get("month5") + "' AND t.MONTH_6 LIKE '" + conditions.get("month6") + "' AND t.MONTH_7 LIKE '" + conditions.get("month7") + "' AND t.MONTH_8 LIKE '" + conditions.get("month8") + "'  JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID  JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '" + conditions.get("CourseName") + "' AND co.CODE LIKE '" + conditions.get("CourseCode") + "' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"' ");
                columns = qr.getResultList();
                for(Object column : columns) {
                    Object[] detailFunding = (Object[]) column;
                    DetailFundingViewObject detailFundingViewObject = new DetailFundingViewObject();
                    detailFundingViewObject.setStuId(detailFunding[0].toString());
                    detailFundingViewObject.setTaName(detailFunding[1].toString());
                    detailFundingViewObject.setBankName("缺失");
                    detailFundingViewObject.setBankId("缺失");
                    detailFundingViewObject.setIdentity(detailFunding[2].toString());
                    detailFundingViewObject.setCourseName(detailFunding[3].toString());
                    detailFundingViewObject.setCourseCode(detailFunding[4].toString());
                    detailFundingViewObject.setMonthlySalary3(detailFunding[5].toString());
                    detailFundingViewObject.setMonthlySalary4(detailFunding[6].toString());
                    detailFundingViewObject.setMonthlySalary5(detailFunding[7].toString());
                    detailFundingViewObject.setMonthlySalary6(detailFunding[8].toString());
                    detailFundingViewObject.setMonthlySalary7(detailFunding[9].toString());
                    detailFundingViewObject.setMonthlySalary8(detailFunding[10].toString());
                    int total = (
                            Integer.parseInt(detailFunding[5].toString()) + Integer.parseInt(detailFunding[6].toString()) +
                                    Integer.parseInt(detailFunding[7].toString()) + Integer.parseInt(detailFunding[8].toString()) +
                                    Integer.parseInt(detailFunding[9].toString()) + Integer.parseInt(detailFunding[10].toString())
                    );
                    detailFundingViewObject.setTotal(String.valueOf(total));
                    list.add(detailFundingViewObject);
                }
            }
            if (curSession.getTerm().equals("秋")) {
                Query qr = em.createNativeQuery("SELECT s.UNIQUEID, s.NAME, s.ID_NUMBER, co.NAME, co.CODE, t.MONTH_9, t.MONTH_10, t.MONTH_11, t.MONTH_12, t.MONTH_1, t.MONTH_2 from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '" + curSession.getId() + "' AND s.UNIQUEID LIKE '" + conditions.get("Number") + "' AND s.NAME LIKE '" + conditions.get("Name") + "' AND s.ID_NUMBER LIKE '" + conditions.get("IdCard") + "' AND t.MONTH_9 LIKE '" + conditions.get("month9") + "' AND t.MONTH_10 LIKE '" + conditions.get("month10") + "' AND t.MONTH_11 LIKE '" + conditions.get("month11") + "' AND t.MONTH_12 LIKE '" + conditions.get("month12") + "' AND t.MONTH_1 LIKE '" + conditions.get("month1") + "' AND t.MONTH_2 LIKE '" + conditions.get("month2") + "' JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '" + conditions.get("CourseName") + "' AND co.CODE LIKE '" + conditions.get("CourseCode") + "' JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS_INSTRUCTOR ci ON t.TA_CLASS = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.UNIQUEID LIKE '"+conditions.get("user")+"'");
                columns = qr.getResultList();
                for(Object column : columns) {
                    Object[] detailFunding = (Object[]) column;
                    DetailFundingViewObject detailFundingViewObject = new DetailFundingViewObject();
                    detailFundingViewObject.setStuId(detailFunding[0].toString());
                    detailFundingViewObject.setTaName(detailFunding[1].toString());
                    detailFundingViewObject.setBankName("缺失");
                    detailFundingViewObject.setBankId("缺失");
                    detailFundingViewObject.setIdentity(detailFunding[2].toString());
                    detailFundingViewObject.setCourseName(detailFunding[3].toString());
                    detailFundingViewObject.setCourseCode(detailFunding[4].toString());
                    detailFundingViewObject.setMonthlySalary9(detailFunding[5].toString());
                    detailFundingViewObject.setMonthlySalary10(detailFunding[6].toString());
                    detailFundingViewObject.setMonthlySalary11(detailFunding[7].toString());
                    detailFundingViewObject.setMonthlySalary12(detailFunding[8].toString());
                    detailFundingViewObject.setMonthlySalary1(detailFunding[9].toString());
                    detailFundingViewObject.setMonthlySalary2(detailFunding[10].toString());
                    int total = (
                            Integer.parseInt(detailFunding[5].toString()) + Integer.parseInt(detailFunding[6].toString()) +
                                    Integer.parseInt(detailFunding[7].toString()) + Integer.parseInt(detailFunding[8].toString()) +
                                    Integer.parseInt(detailFunding[9].toString()) + Integer.parseInt(detailFunding[10].toString())
                    );
                    detailFundingViewObject.setTotal(String.valueOf(total));
                    list.add(detailFundingViewObject);
                }
            }
            return list;
        }
        */
    //}

    @Override
    public boolean changeStatusAvailableForUser(String[] roleIds, String functionId, String taId) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

        TAMSTa current = this.selectById(taId);
        if(current == null)
            return false;

        Integer currentIndex = allStatus.indexOf(current.getOutStandingTaWorkflowStatus());
        Set<Integer> status2IndexCanBe_NotSort = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFIdAndStatus1(RFId, current.getOutStandingTaWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    int index = allStatus.indexOf(statusR.getStatus2());
                    status2IndexCanBe_NotSort.add(index);
                }
        }
        //这个变量表示此用户角色可以转变的状态
        List<Integer> status2IndexCanBe = new ArrayList<>(status2IndexCanBe_NotSort);
        Collections.sort(status2IndexCanBe);
        Integer nextIndex = currentIndex + 1;
        Integer previousIndex = currentIndex > 0 ? currentIndex - 1 : 0;

        if(status2IndexCanBe == null || status2IndexCanBe.size() == 0)
            return false;
        //如果小于可选index最小值或者大于最大值,表示当前状态不属于此用户管辖范围
        int leftEdge = status2IndexCanBe.get(0), rightEdge = status2IndexCanBe.get(status2IndexCanBe.size()-1);
        if(previousIndex < leftEdge && nextIndex > rightEdge)
            return false;
        else {
            return true;
        }
    }

    @Override
    public boolean toNextStatus(String[] roleIds, String functionId, String taId) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

        TAMSTa current = this.selectById(taId);
        if(current == null)
            return false;

        Integer currentIndex = allStatus.indexOf(current.getOutStandingTaWorkflowStatus());
        Set<Integer> status2IndexCanBe_NotSort = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFIdAndStatus1(RFId, current.getOutStandingTaWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    int index = allStatus.indexOf(statusR.getStatus2());
                    status2IndexCanBe_NotSort.add(index);
                }
        }
        //这个变量表示此用户角色可以转变的状态
        List<Integer> status2IndexCanBe = new ArrayList<>(status2IndexCanBe_NotSort);
        Collections.sort(status2IndexCanBe);
        Integer nextIndex = currentIndex + 1;

        if(status2IndexCanBe == null || status2IndexCanBe.size() == 0)
            return false;
        //如果小于可选index最小值或者大于最大值,表示当前状态不属于此用户管辖范围
        int leftEdge = status2IndexCanBe.get(0), rightEdge = status2IndexCanBe.get(status2IndexCanBe.size()-1);
        if(nextIndex > rightEdge)
            return false;
        else {
            while (nextIndex <= rightEdge) {
                if(status2IndexCanBe.contains(nextIndex)) {
                    current.setOutStandingTaWorkflowStatusId(allStatus.get(nextIndex).getId());
                    KradDataServiceLocator.getDataObjectService().save(current);
                    return true;
                }
                nextIndex++;
            }
            //应该来说不会跳到这里才对,这里表示已经跳出了管辖范围右边界
            return false;
        }
    }

    @Override
    public boolean toPreviousStatus(String[] roleIds, String functionId, String taId) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

        TAMSTa current = this.selectById(taId);
        if(current == null)
            return false;

        Integer currentIndex = allStatus.indexOf(current.getOutStandingTaWorkflowStatus());
        Set<Integer> status2IndexCanBe_NotSort = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFIdAndStatus1(RFId, current.getOutStandingTaWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    int index = allStatus.indexOf(statusR.getStatus2());
                    status2IndexCanBe_NotSort.add(index);
                }
        }
        //这个变量表示此用户角色可以转变的状态
        List<Integer> status2IndexCanBe = new ArrayList<>(status2IndexCanBe_NotSort);
        Collections.sort(status2IndexCanBe);
        Integer previousIndex = currentIndex > 0 ? currentIndex - 1 : 0;

        if(status2IndexCanBe == null || status2IndexCanBe.size() == 0)
            return false;
        //如果小于可选index最小值或者大于最大值,表示当前状态不属于此用户管辖范围
        int leftEdge = status2IndexCanBe.get(0), rightEdge = status2IndexCanBe.get(status2IndexCanBe.size()-1);
        if(previousIndex < leftEdge)
            return false;
        else {
            while (previousIndex >= leftEdge) {
                if(status2IndexCanBe.contains(previousIndex)) {
                    current.setOutStandingTaWorkflowStatusId(allStatus.get(previousIndex).getId());
                    KradDataServiceLocator.getDataObjectService().save(current);
                    return true;
                }
                previousIndex--;
            }
            //应该来说不会跳到这里才对,这里表示已经跳出了管辖范围右边界
            return false;
        }
    }


    @Override
    public List<TAMSWorkflowStatus> getAvailableStatus(String[] roleIds ,String functionId,String taId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("id", taId)
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        if(qr.getResults() == null || qr.getResults().size() == 0)
            return null;

        TAMSTa current = qr.getResults().get(0);

        Set<TAMSWorkflowStatus> availableStatus = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = new TAMSWorkflowRoleFunctionDaoJpa().selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = new TAMSWorkflowStatusRDaoJpa().selectByRFIdAndStatus1(RFId, current.getOutStandingTaWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    availableStatus.add(statusR.getStatus2());
                }
        }
        return new ArrayList<>(availableStatus);

    }


    @Override
    public boolean changeStatusToSpecifiedStatus(String taId, String workflowStatusId){
        if(taId == null || workflowStatusId == null)
            return false;
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("id", taId)
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        if(qr.getResults() == null || qr.getResults().size() == 0)
            return false;

        TAMSTa current = qr.getResults().get(0);

        current.setOutStandingTaWorkflowStatusId(workflowStatusId);

        KradDataServiceLocator.getDataObjectService().save(current);

        return false;
    }

}
