package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSActivityDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSActivity;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by damei on 16/11/15.
 */
public class TAMSActivityDaoJpa implements TAMSActivityDao{
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
}
