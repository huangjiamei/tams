package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-1.
 */
@Repository
@Component("TAMSTaDaoJpa")
public class TAMSTaDaoJpa implements TAMSTaDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Override
    public List<TAMSTa> selectAll() {

        return KRADServiceLocator.getDataObjectService().findAll(TAMSTa.class).getResults();

    }

    @Override
    public List<TAMSTa> selectByClassId(List<Object> classIds) {
        List<TAMSTa> tas = new ArrayList<>();

        for(Object classId : classIds) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("taClassId", classId));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            tas.addAll(qr.getResults());
        }

        return tas.isEmpty()?null:tas;
    }

    @Override
    public List<Object> selectClassIdsByStudentId(String uId) {
        Query query = em.createNativeQuery("SELECT TA_CLASS FROM TAMS_TA t WHERE t.TA_ID='" + uId + "'");
        return query.getResultList();
    }

    @Override
    public List<TAMSTa> selectBatchByIds(List<String> ids) {
        List<TAMSTa> tas = new ArrayList<>();

        for(String id : ids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("id", id));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            if(qr.getResults().size() != 0)
                tas.add(qr.getResults().get(0));
        }

        return tas.isEmpty()?null:tas;
    }

    @Override
    public boolean updateByEntity(TAMSTa ta) {
        return KRADServiceLocator.getDataObjectService().save(ta) != null;
    }
}
