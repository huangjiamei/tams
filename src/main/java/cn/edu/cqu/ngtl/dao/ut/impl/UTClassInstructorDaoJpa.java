package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by 金祖增 on 2016/10/20.
 */

@Repository
@Component("UTClassInstructorDaoJpa")
public class UTClassInstructorDaoJpa implements UTClassInstructorDao {

    @Override
    public UTClassInstructor getClassInstructorByCode(String code)
    {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("instructorId" , code));
        QueryResults<UTClassInstructor> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTClassInstructor.class, criteria.build());

        return qr.getResults().isEmpty()?null:qr.getResults().get(0);
    }

}
