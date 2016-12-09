package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Repository
@Component("UTClassInstructorDaoJpa")
public class UTClassInstructorDaoJpa implements UTClassInstructorDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

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


}
