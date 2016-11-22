package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSActivityDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSActivity;
import org.kuali.rice.core.api.criteria.OrderByField;
import org.kuali.rice.core.api.criteria.OrderDirection;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by damei on 16/11/15.
 */
@Repository
@Component("TAMSActivityDaoJpa")
public class TAMSActivityDaoJpa implements TAMSActivityDao{

    private EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    public List<TAMSActivity> selectAllByCalendarId(String CalendarId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("teachCalendarId", CalendarId)
                )
        );
        QueryResults<TAMSActivity> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSActivity.class,
                criteria.build()
        );
        return qr.getResults().size() != 0 ? qr.getResults() : null ;
    }

    @Override
    public List<TAMSActivity> selectAllByClassId(String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("teachCalendar.classId", classId)
                )
        );
        OrderByField orderByField = OrderByField.Builder.create("teachCalendar.id", OrderDirection.ASCENDING).build();
        criteria.setOrderByFields(orderByField);
        QueryResults<TAMSActivity> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSActivity.class,
                criteria.build()
        );
        return qr.getResults().size() != 0 ? qr.getResults() : null ;
    }
}
