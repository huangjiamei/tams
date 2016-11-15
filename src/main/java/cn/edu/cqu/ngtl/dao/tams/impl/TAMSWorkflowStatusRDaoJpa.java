package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusRDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatusR;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-9.
 */
@Repository
@Component("TAMSWorkflowStatusRDaoJpa")
public class TAMSWorkflowStatusRDaoJpa implements TAMSWorkflowStatusRDao {

    @Override
    public List<TAMSWorkflowStatusR> selectByRFId(String RFId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("roleFunctionId", RFId));
        QueryResults<TAMSWorkflowStatusR> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSWorkflowStatusR.class,
                criteria.build()
        );
        return qr.getResults().size() != 0 ? qr.getResults() : null;
    }

    @Override
    public boolean saveTAMSWorkflowStatusR(TAMSWorkflowStatusR dataTAMSWorkflowStatusR) {
        return (KRADServiceLocator.getDataObjectService().save(dataTAMSWorkflowStatusR) != null);
    }

    @Override
    public void deleteTAMSWorkflowStatusRByRFId(String RFId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("roleFunctionId", RFId));
        KRADServiceLocator.getDataObjectService().deleteMatching( TAMSWorkflowStatusR.class, criteria.build());
    }

}
