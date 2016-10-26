package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSCourseManagerDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016-10-26.
 */

@Component("TAMSCourseManagerDaoJpa")
public class TAMSCourseManagerDaoJpa implements TAMSCourseManagerDao {


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
        return  qr.getResults().get(0);
    }


    @Override
    public void saveCourseManager(TAMSCourseManager tamsCourseManager){
        KRADServiceLocator.getDataObjectService().save(tamsCourseManager);
    }



}
