package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.service.common.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by awake on 2016/12/25.
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private TAMSWorkflowStatusDao tamsWorkflowStatusDao;


    @Override
    public boolean isMaxOrderOfThisStatue(String statusId,String functionId){
        TAMSWorkflowStatus newStatus = tamsWorkflowStatusDao.getOneById(statusId);
        Integer newStatusOrder = newStatus.getOrder();
        Integer maxOrder = tamsWorkflowStatusDao.getMaxOrderByFunctionId(functionId);
        if(newStatusOrder==maxOrder)
            return true;
        return false;
    }


    @Override
    public String getWorkFlowStatusName(String statusId){
        TAMSWorkflowStatus status = tamsWorkflowStatusDao.getOneById(statusId);
        if(status!=null){
            return status.getWorkflowStatus();
        }
        return "";
    }
}
