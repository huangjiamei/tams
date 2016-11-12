package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowFunctionsDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowFunctions;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
