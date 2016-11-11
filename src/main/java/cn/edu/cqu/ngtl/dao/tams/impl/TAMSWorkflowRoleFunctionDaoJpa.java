package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowRoleFunctionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowRoleFunction;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-11.
 */
@Repository
@Component("TAMSWorkflowRoleFunctionDaoJpa")
public class TAMSWorkflowRoleFunctionDaoJpa implements TAMSWorkflowRoleFunctionDao {
    @Override
    public String selectIdByRoleIdAndFunctionId(String roleId, String functionId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("roleId", roleId),
                        equal("workflowFunctionId", functionId)
                )
        );
        QueryResults<TAMSWorkflowRoleFunction> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSWorkflowRoleFunction.class,
                criteria.build()
        );
        TAMSWorkflowRoleFunction roleFunction = qr.getResults() != null && qr.getResults().size() != 0 ? qr.getResults().get(0) : null;
        return roleFunction != null ? roleFunction.getId() : null;
    }
}
