package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSCourseManagerDao;
import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTInstructorDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
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

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016-10-26.
 */
@Repository
@Component("TAMSCourseManagerDaoJpa")
public class TAMSCourseManagerDaoJpa implements TAMSCourseManagerDao {

    @Autowired
    private UTInstructorDao instructorDao;

    @Autowired
    private UTCourseDao courseDao;

    @Override
    public List<TAMSCourseManager> getAllCourseManager(){

        return KRADServiceLocator.getDataObjectService().findAll(TAMSCourseManager.class).getResults();

    }

    @Override
    public TAMSCourseManager getCourseManagerByInstructorId(String instructorId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("utInstructor.id" , instructorId));
        QueryResults<TAMSCourseManager> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSCourseManager.class,
                criteria.build()
        );
        return qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }

    @Override
    public void saveCourseManager(TAMSCourseManager tamsCourseManager){
        KRADServiceLocator.getDataObjectService().save(tamsCourseManager);
    }

    @Override
    public void deleteCourseManager(TAMSCourseManager tamsCourseManager){
        KRADServiceLocator.getDataObjectService().delete(tamsCourseManager);
    }

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();


    //课程负责人过滤器
    @Override
    public List<TAMSCourseManager> selectCourseManagerByCondition(Map<String, String> conditions) {
        int countNull = 0;
        List<TAMSCourseManager> list = new ArrayList<>();
        //加通配符
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (entry.getValue() == null) {
                conditions.put(entry.getKey(), "%");
                countNull++;
            } else
                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
        }
        if (countNull != 4) {
            Query qr = em.createNativeQuery("SELECT uc.UNIQUEID, ui.UNIQUEID FROM UNITIME_COURSE uc JOIN TAMS_COURSE_MANAGER t ON uc.UNIQUEID = t.COURSE_ID AND ((uc.NAME LIKE '"+conditions.get("CourseName")+"') AND (uc.CODE LIKE '"+conditions.get("CourseNumber")+"')) JOIN UNITIME_INSTRUCTOR ui ON t.COURSE_MANAGER_ID = ui.UNIQUEID AND ((ui.NAME LIKE '"+conditions.get("InstructorName")+"') AND (ui.CODE LIKE '"+conditions.get("InstructorCode")+"'))");
            //Query qr = em.createNativeQuery("SELECT uc.Name, uc.CODE, ui.NAME, ui.CODE FROM UNITIME_COURSE uc JOIN UNITIME_INSTRUCTOR ui JOIN TAMS_COURSE_MANAGERT t ON uc.UNICQUEID = t.COURSE_ID AND t.MANAGER_ID = ui.UNICQUEID AND ((uc.NAME LIKE '" + conditions.get("searchCourseName") + "') OR (uc.CODE LIKE '" + conditions.get("searchCourseNumber") + "') OR (ui.NAME LIKE '" + conditions.get("searchInstructorName") + "') OR (ui.CODE LIKE '" + conditions.get("searchInstructorCode") + "'))");
            List<Object> column = qr.getResultList();
            for(Object columns : column){
                TAMSCourseManager tamsCourseManager = new TAMSCourseManager();
                Object[] managers = (Object[]) columns;

                UTCourse utCourse = courseDao.selectOneById(Integer.parseInt(managers[0].toString()));
                UTInstructor utInstructor = instructorDao.getInstructorByIdWithoutCache(managers[1].toString());

/*
                utCourse.setName(managers[2].toString());
                utCourse.setCodeR(managers[3].toString());
                utInstructor.setName(managers[4].toString());
                utInstructor.setCode(managers[5].toString());*/

                tamsCourseManager.setCourse(utCourse);
                tamsCourseManager.setUtInstructor(utInstructor);
                tamsCourseManager.setCourseId(utCourse.getId());

                list.add(tamsCourseManager);
            }
        }
        //若返回为空，则显示全部课程负责人
        else{
            return KRADServiceLocator.getDataObjectService().findAll(TAMSCourseManager.class).getResults();
        }
        return list;
    }
        /*
        if(countNull != 4) {
            Query qr = em.createNativeQuery("SELECT uc.NAME, uc.SUBJECT, uc.COURSE_NBR, ui.NAME, ui.CODE FROM UNITIME_COURSE uc JOIN UNITIME_INSTRUCTOR ui JOIN TAMS_COURSE_MANAGER t ON uc.UNIQUEID = t.COURSE_ID AND t.MANAGER_ID = ui.UNIQUEID AND ((uc.NAME LIKE '" + conditions.get("")+"') OR (uc.SUBJECT '"+"' uc.COURSE_NBR LIKE '"+conditions.get("")+"') OR (ui.NAME LIKE '"+conditions.get("")+"') OR (ui.CODE LIKE '"+conditions.get("")+"'))");
            List<Object> column = qr.getResultList();
            for(Object columns : column){

                UTCourse utCourse = new UTCourse();
                UTInstructor utInstructor = new UTInstructor();
                TAMSCourseManager tamsCourseManager = new TAMSCourseManager();

                Object[] managers = (Object[]) columns;
                utCourse.setName(managers[0].toString());
                utCourse.setSubject(managers[1].toString());
                utCourse.setNumber(Integer.parseInt(managers[2].toString()));

                utInstructor.setName(managers[3].toString());
                utInstructor.setCode(managers[4].toString());

                tamsCourseManager.setCourseManagerId(utInstructor.getId());
                utCourse.setId(tamsCourseManager.getCourseId());

                list.add(utCourse);
            }
        }
        return list;
        */

}
