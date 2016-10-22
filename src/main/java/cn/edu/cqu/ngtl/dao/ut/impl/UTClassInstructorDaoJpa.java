package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public class UTClassInstructorDaoJpa implements UTClassInstructorDao {

    @Override
    public UTClassInstructor selectOneByClassId(Integer classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("CLASS_ID", classId)
        );
        QueryResults<UTClassInstructor> queryResults = KradDataServiceLocator.getDataObjectService().findMatching(UTClassInstructor.class, criteria.build());
        return queryResults.getResults().isEmpty()? null : queryResults.getResults().get(0);
    }
}
