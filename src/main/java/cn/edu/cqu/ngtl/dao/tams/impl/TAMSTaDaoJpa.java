package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowRoleFunctionDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusRDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatusR;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
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
    private TAMSWorkflowStatusRDao workflowStatusRDao;

    @Autowired
    private TAMSWorkflowRoleFunctionDao workflowRoleFunctionDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

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

    //查询助教经费信息
    public List<TaFundingViewObject> selectTaFundByCondition(Map<String, String> conditions) {
        //加通配符
        int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            } else
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        UTSession curSession = new UTSession();
        List<TaFundingViewObject> list = new ArrayList<>();
        em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        if(countNull != 10) {
            Query qr = em.createNativeQuery(" SELECT d.NAME, s.UNIQUEID, s.NAME, co.NAME, co.CODE, t.TA_TYPE, t.ASSIGNED_FUNDING, t.PHD_FUNDING, t.TRAVEL_SUBSIDY, t.BONUS from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '"+curSession.getId()+"' AND s.UNIQUEID LIKE '"+conditions.get("Number")+"' AND s.NAME LIKE '"+conditions.get("Name")+"' AND t.TA_TYPE LIKE '"+conditions.get("Type")+"' AND t.ASSIGNED_FUNDING LIKE '"+conditions.get("AssignedFunding")+"' AND t.PHD_FUNDING LIKE '"+conditions.get("PhdFunding")+"' AND t.TRAVEL_SUBSIDY LIKE '"+conditions.get("TravelFunding")+"' AND t.BONUS LIKE '"+conditions.get("Bonus")+"' JOIN UNITIME_DEPARTMENT d ON s.DEPARTMENT_ID = d.UNIQUEID AND d.NAME LIKE '"+conditions.get("dept")+"' JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID AND co.NAME LIKE '"+conditions.get("CourseName")+"' AND co.CODE LIKE '"+conditions.get("CourseCode")+"'");
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
        //若输入为空，则返回所有助教经费
        else{
            Query qr = em.createNativeQuery(" SELECT d.NAME, s.UNIQUEID, s.NAME, co.NAME, co.CODE, t.TA_TYPE, t.ASSIGNED_FUNDING, t.PHD_FUNDING, t.TRAVEL_SUBSIDY, t.BONUS from TAMS_TA t JOIN UNITIME_STUDENT s ON t.TA_ID = s.UNIQUEID AND t.SESSION_ID = '"+curSession.getId()+"'  JOIN UNITIME_DEPARTMENT d ON s.DEPARTMENT_ID = d.UNIQUEID  JOIN UNITIME_CLASS cl ON t.TA_CLASS = cl.UNIQUEID JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID ");
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
    }

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
        if(previousIndex < leftEdge || nextIndex > rightEdge)
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
}
