package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowFunctionsDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowFunctions;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-10.
 */
@Repository
@Component("TAMSWorkflowFunctionsDaoJpa")
public class TAMSWorkflowFunctionsDaoJpa implements TAMSWorkflowFunctionsDao {
    @Override
    public List<TAMSWorkflowFunctions> selectAll() {
        return KRADServiceLocator.getDataObjectService().findAll(TAMSWorkflowFunctions.class).getResults();
    }

    @Override
    public TAMSWorkflowFunctions selectOneByName(String Name) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("name", Name)
        );
        QueryResults<TAMSWorkflowFunctions> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSWorkflowFunctions.class,
                criteria.build()
        );
        return  qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }
}
