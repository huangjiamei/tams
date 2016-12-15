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
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

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

        Integer currentIndex = allStatus.indexOf(current.getWorkflowStatus());
        Set<Integer> status2IndexCanBe_NotSort = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFIdAndStatus1(RFId, current.getWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    int index = allStatus.indexOf(statusR.getStatus2());
                    status2IndexCanBe_NotSort.add(index);
                }
        }
        //这个变量表示此用户角色可以转变的状态
        List<Integer> status2IndexCanBe = new ArrayList<>(status2IndexCanBe_NotSort);
        Collections.sort(status2IndexCanBe);
        Integer nextIndex = currentIndex + 1;
        if(status2IndexCanBe == null || status2IndexCanBe.size() == 0)
            return false;
        //如果小于可选index最小值或者大于最大值,表示当前状态不属于此用户管辖范围
        int leftEdge = status2IndexCanBe.get(0), rightEdge = status2IndexCanBe.get(status2IndexCanBe.size()-1);
        if(nextIndex > rightEdge)
            return false;
        else {
            while (nextIndex <= rightEdge) {
                if(status2IndexCanBe.contains(nextIndex)) {
                    current.setWorkflowStatusId(allStatus.get(nextIndex).getId());
                    KradDataServiceLocator.getDataObjectService().save(current);
                    return true;
                }
                nextIndex++;
            }
            //应该来说不会跳到这里才对,这里表示已经跳出了管辖范围右边界
            return false;
        }
    }

    @Override
    public boolean toPreviousStatus(String[] roleIds, String functionId, String classId) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

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

        Integer currentIndex = allStatus.indexOf(current.getWorkflowStatus());
        Set<Integer> status2IndexCanBe_NotSort = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = workflowStatusRDao.selectByRFIdAndStatus1(RFId, current.getWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    int index = allStatus.indexOf(statusR.getStatus2());
                    status2IndexCanBe_NotSort.add(index);
                }
        }
        //这个变量表示此用户角色可以转变的状态
        List<Integer> status2IndexCanBe = new ArrayList<>(status2IndexCanBe_NotSort);
        Collections.sort(status2IndexCanBe);
        Integer previousIndex = currentIndex > 0 ? currentIndex - 1 : 0;
        if(status2IndexCanBe == null || status2IndexCanBe.size() == 0)
            return false;
        //如果小于可选index最小值或者大于最大值,表示当前状态不属于此用户管辖范围
        int leftEdge = status2IndexCanBe.get(0), rightEdge = status2IndexCanBe.get(status2IndexCanBe.size()-1);
        if(previousIndex < leftEdge)
            return false;
        else {
            while (previousIndex >= leftEdge) {
                if(status2IndexCanBe.contains(previousIndex)) {
                    current.setWorkflowStatusId(allStatus.get(previousIndex).getId());
                    KradDataServiceLocator.getDataObjectService().save(current);
                    return true;
                }
                previousIndex--;
            }
            //应该来说不会跳到这里才对,这里表示已经跳出了管辖范围右边界
            return false;
        }
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

    @Override
    public void saveApplyStatueByList(List<TAMSClassApplyStatus> tamsClassApplyStatuses){
        for(TAMSClassApplyStatus tamsClassApplyStatus:tamsClassApplyStatuses){
            KradDataServiceLocator.getDataObjectService().save(tamsClassApplyStatus);
        }
    }

    @Override
    public List<TAMSWorkflowStatus> getAvailableStatus(String[] roleIds, String functionId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("classId", classId)
        );
        QueryResults<TAMSClassApplyStatus> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassApplyStatus.class,
                criteria.build()
        );
        if(qr.getResults() == null || qr.getResults().size() == 0)
            return null;

        TAMSClassApplyStatus current = qr.getResults().get(0);

        Set<TAMSWorkflowStatus> availableStatus = new HashSet<>();
        for(String roleId : roleIds) {
            String RFId = new TAMSWorkflowRoleFunctionDaoJpa().selectIdByRoleIdAndFunctionId(roleId, functionId);
            List<TAMSWorkflowStatusR> statusRs = new TAMSWorkflowStatusRDaoJpa().selectByRFIdAndStatus1(RFId, current.getWorkflowStatusId());
            if(statusRs != null)
                for(TAMSWorkflowStatusR statusR : statusRs) {
                    availableStatus.add(statusR.getStatus2());
                }
        }
        return new ArrayList<>(availableStatus);
    }

    @Override
    public boolean changeStatusToCertainStatus(String classId, String workflowStatusId) {
        if(classId == null || workflowStatusId == null)
            return false;
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

        current.setWorkflowStatusId(workflowStatusId);

        KradDataServiceLocator.getDataObjectService().save(current);
        return true;
    }
}
