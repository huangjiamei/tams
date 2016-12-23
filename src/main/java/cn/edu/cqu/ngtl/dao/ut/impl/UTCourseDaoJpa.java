package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;

/**
 * Created by tangjing on 16-10-13.
 */
@Repository
@Component("UTCourseDaoJpa")
public class UTCourseDaoJpa implements UTCourseDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();


    @Override
    public UTCourse selectOneById(Integer id) {

        return KradDataServiceLocator.getDataObjectService().find(UTCourse.class, id);

    }

    @Override
    public List<UTCourse> selectAllMappedByDepartment() {

        return KradDataServiceLocator.getDataObjectService().findAll(UTCourse.class).getResults();

    }

    @Override
    public void InsertOneByEntity(UTCourse utCourse){
        KradDataServiceLocator.getDataObjectService().save(utCourse);
    }

    @Override
    public List<UTCourse> getAllNeedManagerCourse(){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(isNull("subject"));
        QueryResults<UTCourse> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTCourse.class,
                criteria.build()
        );
        return qr.getResults()==null?null:qr.getResults();
    }


    @Override
    public List<Map> getCourseNameIdMap(){
        List<Map> result = new ArrayList<>();
        Map courseNameIdMap = new HashMap();
        Map courseNbrIdMap = new HashMap();
        Query query = em.createNativeQuery("SELECT t.NAME,t.UNIQUEID,t.CODE FROM UNITIME_COURSE t");
        List<Object> columns = query.getResultList();
        for (Object column : columns) {
            Object[] instructors = (Object[]) column;
            courseNameIdMap.put(instructors[1].toString(),instructors[0].toString());
            courseNbrIdMap.put(instructors[1].toString(),instructors[2].toString());
        }
        result.add(courseNameIdMap);
        result.add(courseNbrIdMap);
        return result;
    }
}
