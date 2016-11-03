package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaApplicationDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-23.
 */
@Repository
@Component("TAMSTaApplicationDaoJpa")
public class TAMSTaApplicationDaoJpa implements TAMSTaApplicationDao {

    @Override
    public boolean insertOne(TAMSTaApplication taApplication) {

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();

        taApplication.setSessinId(curSession.getId());

        KRADServiceLocator.getDataObjectService().save(taApplication);

        return true;
    }

    @Override
    public List<TAMSTaApplication> selectByClassId(List<Object> classIds) {
        List<TAMSTaApplication> tas = new ArrayList<>();

        for(Object classId : classIds) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("applicationClassId", classId));
            QueryResults<TAMSTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTaApplication.class,
                    criteria.build()
            );
            tas.addAll(qr.getResults());
        }

        return tas.isEmpty()?null:tas;
    }
}
