package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
import org.apache.poi.ss.formula.functions.T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
 * Created by awake on 2016/11/25.
 */
@Repository
@Component("TAMSClassFundingDao")
public class TAMSClassFundingDaoJpa implements TAMSClassFundingDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    //对于classfunding表
    //根据条件查询课程经费
    @Override
    public List<ClassFundingViewObject> selectClassFundByCondition(Map<String, String> conditions) {
        //加通配符，如果为空，则加一个通配符；若为资金，则不加；除资金以外的前后加
        //int countNull = 0;
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
        //        countNull++;
            }
            else if(entry.getKey() == "className")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "classCode")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "classNbr")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if(entry.getKey() == "teacher")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<ClassFundingViewObject> list = new ArrayList<>();
        //if(countNull != 10){
        //若不根据教师查询，则
        if(conditions.get("teacher").equals("%%") || conditions.get("teacher").equals("%")) {
            Query qr = em.createNativeQuery("SELECT f.APPLY_FUNDING, f.ASSIGNED_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY, d.NAME, co.NAME, co.CODE, cl.CLASS_NBR, f.CLASS_ID FROM TAMS_CLASS_FUNDING f JOIN UNITIME_CLASS cl ON cl.UNIQUEID = f.CLASS_ID AND f.SESSION_ID = '" + curSession.getId() + "' AND f.APPLY_FUNDING LIKE '" + conditions.get("applyFunding") + "' AND f.ASSIGNED_FUNDING LIKE '" + conditions.get("actualFunding") + "' AND f.PHD_FUNDING LIKE '" + conditions.get("phdFunding") + "' AND f.BONUS LIKE '" + conditions.get("bonus") + "' AND f.TRAVEL_SUBSIDY LIKE '" + conditions.get("travelFunding") + "' AND f.CLASS_ID IN (SELECT cl.UNIQUEID FROM UNITIME_CLASS cl JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  AND co.NAME LIKE '" + conditions.get("className") + "' AND co.CODE LIKE '" + conditions.get("classCode") + "' AND cl.CLASS_NBR LIKE '" + conditions.get("classNbr") + "' AND co.DEPARTMENT_ID IN (SELECT d.UNIQUEID FROM UNITIME_DEPARTMENT d WHERE d.UNIQUEID LIKE '" + conditions.get("dept") + "' ) ) JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID");
            List<Object> columns = qr.getResultList();
            for (Object column : columns) {
                Object[] informations = (Object[]) column;
                ClassFundingViewObject classFundingViewObject = new ClassFundingViewObject();
                classFundingViewObject.setApplyFunding(informations[0].toString());
                classFundingViewObject.setAssignedFunding(informations[1].toString());
                classFundingViewObject.setPhdFunding(informations[2].toString());
                classFundingViewObject.setBonus(informations[3].toString());
                classFundingViewObject.setTravelSubsidy(informations[4].toString());
                classFundingViewObject.setDepartment(informations[5].toString());
                classFundingViewObject.setCourseName(informations[6].toString());
                classFundingViewObject.setCourseCode(informations[7].toString());
                classFundingViewObject.setClassNumber(informations[8].toString());
                //classFundingViewObject.setInstructorName(informations[9].toString());
                classFundingViewObject.setClassId(informations[9].toString());
                list.add(classFundingViewObject);
            }
        }
        else{
            Query qr = em.createNativeQuery("SELECT f.APPLY_FUNDING, f.ASSIGNED_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY, d.NAME, co.NAME, co.CODE, cl.CLASS_NBR, f.CLASS_ID FROM TAMS_CLASS_FUNDING f JOIN UNITIME_CLASS cl ON cl.UNIQUEID = f.CLASS_ID AND f.SESSION_ID = '" + curSession.getId() + "' AND f.APPLY_FUNDING LIKE '" + conditions.get("applyFunding") + "' AND f.ASSIGNED_FUNDING LIKE '" + conditions.get("actualFunding") + "' AND f.PHD_FUNDING LIKE '" + conditions.get("phdFunding") + "' AND f.BONUS LIKE '" + conditions.get("bonus") + "' AND f.TRAVEL_SUBSIDY LIKE '" + conditions.get("travelFunding") + "' AND f.CLASS_ID IN (SELECT cl.UNIQUEID FROM UNITIME_CLASS cl JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  AND co.NAME LIKE '" + conditions.get("className") + "' AND co.CODE LIKE '" + conditions.get("classCode") + "' AND cl.CLASS_NBR LIKE '" + conditions.get("classNbr") + "' AND co.DEPARTMENT_ID IN (SELECT d.UNIQUEID FROM UNITIME_DEPARTMENT d WHERE d.UNIQUEID LIKE '" + conditions.get("dept") + "' ) ) JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID  JOIN UNITIME_CLASS_INSTRUCTOR ci ON cl.UNIQUEID = ci.CLASS_ID JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.NAME LIKE '" + conditions.get("teacher") + "'");
            List<Object> columns = qr.getResultList();
            for (Object column : columns) {
                Object[] informations = (Object[]) column;
                ClassFundingViewObject classFundingViewObject = new ClassFundingViewObject();
                classFundingViewObject.setApplyFunding(informations[0].toString());
                classFundingViewObject.setAssignedFunding(informations[1].toString());
                classFundingViewObject.setPhdFunding(informations[2].toString());
                classFundingViewObject.setBonus(informations[3].toString());
                classFundingViewObject.setTravelSubsidy(informations[4].toString());
                classFundingViewObject.setDepartment(informations[5].toString());
                classFundingViewObject.setCourseName(informations[6].toString());
                classFundingViewObject.setCourseCode(informations[7].toString());
                classFundingViewObject.setClassNumber(informations[8].toString());
                //classFundingViewObject.setInstructorName(informations[9].toString());
                classFundingViewObject.setClassId(informations[9].toString());
                list.add(classFundingViewObject);
            }
        }
            return list.size() !=0 ? list : null;
        }
        /*
        //若输入为空，则返回全部课程经费
        else{
            return taConverter.classFundingToViewObject(
                    adminService.getFundingByClass()
            );
        }
        */


    @Override
    public List<TAMSClassFunding> selectAll(User user) {

        UTSession currentSession = sessionDao.getCurrentSession();

        /**
         * 如果是二级单位管理员
         */
/*
        if(iUserInfoService.isCollegeStaff(user.getCode())) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("sessionId", currentSession.getId())
                    )
            );
            List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findMatching(TAMSClassFunding.class, criteria.build()).getResults();
            List<TAMSClassFunding> copyList = new ArrayList<>();
            for (TAMSClassFunding per : list) {
                per.setClassInformation(
                        classInfoDao.getOneById(
                                Integer.parseInt(
                                        per.getClassId()
                                )
                        )
                );
                copyList.add(per);
            }

            //删除不是当前用户学院的课程
            Iterator iterator = copyList.iterator();
            while (iterator.hasNext()){
                TAMSClassFunding tamsClassFunding = (TAMSClassFunding)iterator.next();
                if(!tamsClassFunding.getClassInformation().getDepartmentId().equals(user.getDepartmentId())){
                    iterator.remove();
                }
            }
            return copyList.size() != 0 ? copyList : null;
        }
*/


        /**
         * 如果是教师  暂定教师没有权限查看此页面   根据教师取课程可以参照此方法
         */
         if(iUserInfoService.isInstructor(user.getCode())){
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(user.getCode());
            String conditions = "";
            for(Object classid : classIds){
                conditions+="'" + classid.toString()+"',";
            }

            String sql = conditions.substring(0,conditions.length()-1);
            Query query = em.createNativeQuery("SELECT * FROM TAMS_CLASS_FUNDING t WHERE t.SESSION_ID = '"+currentSession.getId()+"' AND t.CLASS_ID in ("+sql+")",TAMSClassFunding.class);
            List<TAMSClassFunding> list = query.getResultList();
            for (TAMSClassFunding per : list) {
                per.setClassInformation(
                        classInfoDao.getOneById(
                                //Integer.parseInt(
                                        per.getClassId()
                                //)
                        )
                );
            }
            return list;
        }
        return null;
    }

    @Override
    public TAMSClassFunding getOneByClassIdAndSessionId(String classId, String sessionId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("classId", classId),
                        equal("sessionId",sessionId)
                )
        );
        List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findMatching(TAMSClassFunding.class, criteria.build()).getResults();
        if(list.size() == 0 || list == null)
            return null;
        else
            return list.get(0);
    }


    @Override
    public boolean saveOneByEntity(TAMSClassFunding tamsClassFunding){
        return KRADServiceLocator.getDataObjectService().save(tamsClassFunding)==null;
    }

    @Override
    public TAMSClassFunding getOneByClassId(String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("classId", classId)
                )
        );
        List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findMatching(TAMSClassFunding.class, criteria.build()).getResults();
        if(list.size() == 0 || list == null)
            return null;
        else
            return list.get(0);
    }

    public List<TAMSClassFunding> selectByClassIds(List<String> classids) {
        List<TAMSClassFunding> tamsClassFunding = new ArrayList<>();
        for(String classid : classids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("classId", classid)
                    )
            );
            List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findMatching(TAMSClassFunding.class, criteria.build()).getResults();
            if(list.size() == 0 || list == null)
                tamsClassFunding.add(null);
            else
                tamsClassFunding.add(list.get(0));
        }
        return tamsClassFunding;
    }
}
