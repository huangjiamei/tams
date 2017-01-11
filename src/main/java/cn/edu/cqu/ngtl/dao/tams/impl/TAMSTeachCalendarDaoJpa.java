package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTeachCalendarDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import org.apache.commons.collections.bag.PredicatedBag;
import org.apache.commons.collections.bag.PredicatedSortedBag;
import org.kuali.rice.core.api.criteria.*;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.UifConstants;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    @Override
    public TAMSTeachCalendar insertByEntity(TAMSTeachCalendar teachCalendar) {
        return KRADServiceLocator.getDataObjectService().save(teachCalendar);
    }

    @Override
    public TAMSTeachCalendar selectById(String id) {
        return KradDataServiceLocator.getDataObjectService().find(TAMSTeachCalendar.class, id);
    }

    @Override
    public boolean deleteByEntity(TAMSTeachCalendar teachCalendar) {
        KradDataServiceLocator.getDataObjectService().delete(teachCalendar);
        return true;
    }

    @Override
    public String countWorkTimeByClassId(String classId) {
        EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query query = em.createNativeQuery("SELECT SUM(t.ELAPSED_TIME) FROM TAMS_TEACH_CALENDAR t WHERE t.CLASS_ID = " + classId);
        Object sum = query.getSingleResult();

        return sum == null ? null : sum.toString();
    }
}
