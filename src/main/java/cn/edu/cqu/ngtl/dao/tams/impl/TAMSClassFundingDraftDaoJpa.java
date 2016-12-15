package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassFundingDraftDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFundingDraft;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
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

/**
 * Created by awake on 2016/11/25.
 */


@Repository
@Component("TAMSClassFundingDraftDaoJpa")
public class TAMSClassFundingDraftDaoJpa implements TAMSClassFundingDraftDao {

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private UTClassInstructorDao utClassInstructorDao;


    @Override
    public List<TAMSClassFunding> selectAll() {

        List<TAMSClassFundingDraft> list = KradDataServiceLocator.getDataObjectService().findAll(TAMSClassFundingDraft.class).getResults();
        List<TAMSClassFunding> result = new ArrayList<>(list.size());

        for (TAMSClassFundingDraft per : list) {
            TAMSClassFunding tamsClassFunding = new TAMSClassFunding();
            tamsClassFunding.setApplyFunding(per.getApplyFunding());
            tamsClassFunding.setAssignedFunding(per.getAssignedFunding());
            tamsClassFunding.setBonus(per.getBonus());
            tamsClassFunding.setClassInformation(
                    classInfoDao.getOneById(
                            Integer.parseInt(
                                    per.getClassId()
                            )));
            tamsClassFunding.setSession(
                    sessionDao.getUTSessionById(
                            tamsClassFunding.getClassInformation().getSessionId()
                    )
            );
            tamsClassFunding.setClassId(per.getClassId());
            tamsClassFunding.setPhdFunding(per.getPhdFunding());
            tamsClassFunding.setTravelSubsidy(per.getTravelSubsidy());
            result.add(tamsClassFunding);
        }


        return result;
    }

    //过滤classdraft经费
    //根据条件查询课程经费
    @Override
    public List<ClassFundingViewObject> selectClassFundDraftByCondition(Map<String, String> conditions) {
        //加通配符，如果为空，则加一个通配符；若为资金，则不加；除资金以外的前后加
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
            } else if (entry.getKey() == "className")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if (entry.getKey() == "classCode")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if (entry.getKey() == "classNbr")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
            else if (entry.getKey() == "teacher")
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        List<ClassFundingViewObject> list = new ArrayList<>();
        //若不根据教师查询，则
        if(conditions.get("teacher").equals("%%") || conditions.get("teacher").equals("%")) {
            Query qr = em.createNativeQuery("SELECT f.APPLY_FUNDING, f.ASSIGNED_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY, d.NAME, co.NAME, co.CODE, cl.CLASS_NBR, f.CLASS_ID FROM TAMS_CLASS_FUNDING_DRAFT f JOIN UNITIME_CLASS cl ON cl.UNIQUEID = f.CLASS_ID AND f.SESSION_ID = '" + curSession.getId() + "' AND f.APPLY_FUNDING LIKE '" + conditions.get("applyFunding") + "' AND f.ASSIGNED_FUNDING LIKE '" + conditions.get("actualFunding") + "' AND f.PHD_FUNDING LIKE '" + conditions.get("phdFunding") + "' AND f.BONUS LIKE '" + conditions.get("bonus") + "' AND f.TRAVEL_SUBSIDY LIKE '" + conditions.get("travelFunding") + "' AND f.CLASS_ID IN (SELECT cl.UNIQUEID FROM UNITIME_CLASS cl JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  AND co.NAME LIKE '" + conditions.get("className") + "' AND co.CODE LIKE '" + conditions.get("classCode") + "' AND cl.CLASS_NBR LIKE '" + conditions.get("classNbr") + "' AND co.DEPARTMENT_ID IN (SELECT d.UNIQUEID FROM UNITIME_DEPARTMENT d WHERE d.UNIQUEID LIKE '" + conditions.get("dept") + "' ) ) JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID");
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
        }else {
            Query qr = em.createNativeQuery("SELECT f.APPLY_FUNDING, f.ASSIGNED_FUNDING, f.PHD_FUNDING, f.BONUS, f.TRAVEL_SUBSIDY, d.NAME, co.NAME, co.CODE, cl.CLASS_NBR, f.CLASS_ID FROM TAMS_CLASS_FUNDING_DRAFT f JOIN UNITIME_CLASS cl ON cl.UNIQUEID = f.CLASS_ID AND f.SESSION_ID = '" + curSession.getId() + "' AND f.APPLY_FUNDING LIKE '" + conditions.get("applyFunding") + "' AND f.ASSIGNED_FUNDING LIKE '" + conditions.get("actualFunding") + "' AND f.PHD_FUNDING LIKE '" + conditions.get("phdFunding") + "' AND f.BONUS LIKE '" + conditions.get("bonus") + "' AND f.TRAVEL_SUBSIDY LIKE '" + conditions.get("travelFunding") + "' AND f.CLASS_ID IN (SELECT cl.UNIQUEID FROM UNITIME_CLASS cl JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID  AND co.NAME LIKE '" + conditions.get("className") + "' AND co.CODE LIKE '" + conditions.get("classCode") + "' AND cl.CLASS_NBR LIKE '" + conditions.get("classNbr") + "' AND co.DEPARTMENT_ID IN (SELECT d.UNIQUEID FROM UNITIME_DEPARTMENT d WHERE d.UNIQUEID LIKE '" + conditions.get("dept") + "' ) ) JOIN UNITIME_COURSE_OFFERING cf ON cl.COURSEOFFERING_ID = cf.UNIQUEID JOIN UNITIME_COURSE co ON cf.COURSE_ID = co.UNIQUEID JOIN UNITIME_DEPARTMENT d ON co.DEPARTMENT_ID = d.UNIQUEID  JOIN UNITIME_CLASS_INSTRUCTOR ci ON cl.UNIQUEID = ci.CLASS_ID "
                    + "  JOIN UNITIME_INSTRUCTOR i ON ci.INSTRUCTOR_ID = i.UNIQUEID AND i.NAME LIKE '" + conditions.get("teacher") + "' ");
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
                classFundingViewObject.setClassId(informations[9].toString());
                //classFundingViewObject.setInstructorName(informations[10].toString());
                list.add(classFundingViewObject);
            }
        }

        return list.size() != 0 ? list : null;
    }
}
