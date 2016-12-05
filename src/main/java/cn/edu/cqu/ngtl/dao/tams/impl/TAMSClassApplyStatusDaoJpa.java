package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassApplyStatusDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowRoleFunctionDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusRDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassApplyStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatusR;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-26.
 */
@Repository
@Component("TAMSClassApplyStatusDaoJpa")
public class TAMSClassApplyStatusDaoJpa implements TAMSClassApplyStatusDao {

    @Autowired
    private TAMSWorkflowStatusRDao workflowStatusRDao;

    @Autowired
    private TAMSWorkflowRoleFunctionDao workflowRoleFunctionDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Override
    public boolean toNextStatus(String[] roleIds, String functionId, String classId) {
        Set<Integer> whichColumn = new HashSet<>();
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);

            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFId(RFId);

            for(TAMSWorkflowStatusR statusR : statusRs) {
                int j = allStatus.indexOf(statusR.getStatus2());
                whichColumn.add(j);
            }
        }
        List<Integer> parsedWhichColumn = new ArrayList<>(whichColumn);
        Collections.sort(parsedWhichColumn);

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("classId", classId)
        );
        QueryResults<TAMSClassApplyStatus> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassApplyStatus.class,
                criteria.build()
        );
        if(qr.getResults() == null || qr.getResults().size() == 0)
            return false;

        TAMSClassApplyStatus current = qr.getResults().get(0);

        Integer position = allStatus.indexOf(current.getWorkflowStatus());

        Integer nextPosition = parsedWhichColumn.indexOf(position) + 1;

        if(nextPosition >= allStatus.size())  //已到头，不可以继续next
            return false;

        current.setWorkflowStatusId(allStatus.get(nextPosition).getId());

        KradDataServiceLocator.getDataObjectService().save(current);
        return true;
    }

    @Override
    public boolean isInitializedStatus(String functionId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("classId", classId)
        );
        QueryResults<TAMSClassApplyStatus> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassApplyStatus.class,
                criteria.build()
        );
        if(qr.getResults() == null || qr.getResults().size() == 0)
            return false;

        TAMSClassApplyStatus current = qr.getResults().get(0);

        boolean result = workflowStatusDao.isFirstStatus(current.getWorkflowStatusId());
        return result;
    }
}
