package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSCourseManagerDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Repository
@Component("UTClassInstructorDaoJpa")
public class UTClassInstructorDaoJpa implements UTClassInstructorDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private TAMSCourseManagerDao tamsCourseManagerDao;

    @Override
    public List<UTClassInstructor> selectByClassId(String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("classId", classId)
        );
        QueryResults<UTClassInstructor> queryResults = KradDataServiceLocator.getDataObjectService().findMatching(UTClassInstructor.class, criteria.build());
        return queryResults.getResults().isEmpty()? null : queryResults.getResults();
    }

    //根据教师的id查询批量class_id
    @Override
    public List<Object> selectClassIdsByInstructorId(String uId) {
        Query query = em.createNativeQuery("SELECT CLASS_ID FROM UNITIME_CLASS_INSTRUCTOR t WHERE t.INSTRUCTOR_ID='" + uId + "'");
        return query.getResultList();
    }


    @Override
    public List<UTClassInstructor> getAllClassInstructor(){
        Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INSTRUCTOR t",UTClassInstructor.class);
//        KRADServiceLocator.getDataObjectService().findAll(UTClassInstructor.class).getResults();

        return query.getResultList();
    }


    @Override
    public void saveClassInstructorByList(List<UTClassInstructor> utClassInstructors){
        for(UTClassInstructor utClassInstructor : utClassInstructors){
            KradDataServiceLocator.getDataObjectService().save(utClassInstructor);
        }
    }

    @Override
    public Map getAllClassIdAndInstructorId(Map InstructorMap){
        Map classInstructorMap = new HashMap();
        Query query = em.createNativeQuery("SELECT t.CLASS_ID,t.INSTRUCTOR_ID FROM UNITIME_CLASS_INSTRUCTOR t");
        List<Object> columns = query.getResultList();
        for (Object column : columns) {
            Object[] insIdAndClassId = (Object[]) column;
            if (classInstructorMap.get(insIdAndClassId[0].toString()) != null) //如果一门课有多个教师，则将教师名字进行组合
                classInstructorMap.put(insIdAndClassId[0].toString(), InstructorMap.get(insIdAndClassId[1]) + " " + classInstructorMap.get(insIdAndClassId[0].toString()));
            else
                classInstructorMap.put(insIdAndClassId[0].toString(), InstructorMap.get(insIdAndClassId[1]));
        }
        return classInstructorMap;
    }

    public List<Object> selectCourseManagerClassIdsByInstructorId(String uId){
        Query query = em.createNativeQuery("SELECT t.COURSE_ID FROM TAMS_COURSE_MANAGER t WHERE t.COURSE_MANAGER_ID ='"+uId+"'");
        List<Object> courseIdList = query.getResultList();
        List<Object> result  = new ArrayList();
        for (Object courseId : courseIdList) {
            query = em.createNativeQuery("SELECT t.UNIQUEID FROM UNITIME_CLASS_INFORMATION t WHERE t.COURSE_ID = '" + courseId.toString()+"'");
            result.addAll(query.getResultList());
        }
        return result;
    }
}
