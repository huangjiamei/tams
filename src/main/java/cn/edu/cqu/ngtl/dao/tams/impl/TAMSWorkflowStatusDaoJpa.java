package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
@Repository
@Component("TAMSWorkflowStatusDaoJpa")
public class TAMSWorkflowStatusDaoJpa implements TAMSWorkflowStatusDao {

    @Override
    public List<TAMSWorkflowStatus> selectAll() {
        return KradDataServiceLocator.getDataObjectService().findAll(TAMSWorkflowStatus.class).getResults();
    }
}
