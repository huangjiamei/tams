package cn.edu.cqu.ngtl.dao.CM.impl;

import cn.edu.cqu.ngtl.dao.CM.CMProgramCourseDao;
import cn.edu.cqu.ngtl.dataobject.CM.CMProgramCourse;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-15.
 */
@Repository
@Component("CMProgramCourseDaoJpa")
public class CMProgramCourseDaoJpa implements CMProgramCourseDao {

    @Override
    public CMProgramCourse selectByCourseId(Integer courseId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("courseId" , courseId));
        QueryResults<CMProgramCourse> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                CMProgramCourse.class,
                criteria.build()
        );

        return qr.getResults().get(0);

    }

}
