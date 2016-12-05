package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
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

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-9.
 */
@Repository
@Component("TAMSWorkflowStatusDaoJpa")
public class TAMSWorkflowStatusDaoJpa implements TAMSWorkflowStatusDao {

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    private static final int FirstIndex = 1;

    @Override
    public List<TAMSWorkflowStatus> selectAll() {
        List<TAMSWorkflowStatus> result = KradDataServiceLocator.getDataObjectService().findAll(TAMSWorkflowStatus.class).getResults();
        return result;
    }

    @Override
    public List<TAMSWorkflowStatus> selectByFunctionId(String functionId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("workflowFunctionId", functionId)
        );
        OrderByField orderByField = OrderByField.Builder.create("order", OrderDirection.ASCENDING).build();
        criteria.setOrderByFields(orderByField);
        QueryResults<TAMSWorkflowStatus> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSWorkflowStatus.class,
                criteria.build()
        );
        return qr.getResults().size() != 0 ? qr.getResults() : null ;
    }

    @Override
    public boolean isFirstStatus(String workflowStatusId) {
        TAMSWorkflowStatus qr = KradDataServiceLocator.getDataObjectService().find(TAMSWorkflowStatus.class, workflowStatusId);
        if(qr == null)
            return false;
        else if(qr.getOrder() == FirstIndex)
            return true;
        else
            return false;
    }
}
