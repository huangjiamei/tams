package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTeachCalendarDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-15.
 */
@Repository
@Component("TAMSTeachCalendarDaoJpa")
public class TAMSTeachCalendarDaoJpa implements TAMSTeachCalendarDao {
    @Override
    public List<TAMSTeachCalendar> selectAllByClassId(String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("classId", classId)
                )
        );
        QueryResults<TAMSTeachCalendar> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTeachCalendar.class,
                criteria.build()
        );
        return qr.getResults().size() != 0 ? qr.getResults() : null;
    }
}
