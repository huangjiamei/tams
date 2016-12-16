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
import javax.persistence.Query;
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

    //默认显示审核类别的工作流
    @Override
    public List<TAMSWorkflowStatus> selectAll() {
        //List<TAMSWorkflowStatus> result = KradDataServiceLocator.getDataObjectService().findAll(TAMSWorkflowStatus.class).getResults();
        //return result.size() !=0 ? result : null;
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query qr = em.createNativeQuery("SELECT * FROM TAMS_WORKFLOW_STATUS s WHERE s.WORKFLOW_FUNCTION_ID IN (SELECT UNIQUEID FROM TAMS_WORKFLOW_FUNCTIONS f WHERE f.NAME = '审核' )", TAMSWorkflowStatus.class);
        List<TAMSWorkflowStatus> list = qr.getResultList();
        return list.size()!=0 ? list: null;
    }

    //根据workflowfunctionid查询
    @Override
    public List<TAMSWorkflowStatus> selectWorkFlowByCondition(String workflowfunction){
        em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();
        Query qr = em.createNativeQuery("SELECT * FROM TAMS_WORKFLOW_STATUS s WHERE s.WORKFLOW_FUNCTION_ID  = '"+workflowfunction+"' ", TAMSWorkflowStatus.class);
        List<TAMSWorkflowStatus> list = qr.getResultList();
        /*
        List<Object> columns = qr.getResultList();
        for(Object column : columns) {
            Object[] workflowStatuses = (Object[]) column;
            TAMSWorkflowStatus tamsWorkflowStatus = new TAMSWorkflowStatus();
            tamsWorkflowStatus.setWorkflowStatus(workflowStatuses[1].toString());
            tamsWorkflowStatus.setOrder(Integer.parseInt(workflowStatuses[4].toString()));
            list.add(tamsWorkflowStatus);
        }
        */
        return list.size()!=0 ? list: null;
    }

    /*
    //添加工作流类型
    @Override
    public boolean insertOne(Map<String, String> conditions) {
        TAMSWorkflowStatus tamsWorkflowStatus = new TAMSWorkflowStatus();
        tamsWorkflowStatus.setWorkflowFunctionId(conditions.get("workflowfunction"));
        tamsWorkflowStatus.setWorkflowStatus(conditions.get("workflowstatus"));
        tamsWorkflowStatus.setOrder(Integer.parseInt(conditions.get("workfloworder")));
        KRADServiceLocator.getDataObjectService().save(tamsWorkflowStatus);
        return true;
    }

    //修改工作流类型
    @Override
    public boolean modifyOne(Map<String, String> conditions, String status, String order) {
        TAMSWorkflowStatus tamsWorkflowStatus = new TAMSWorkflowStatus();
        tamsWorkflowStatus.setWorkflowFunctionId(conditions.get("workflowfunction"));
        if(conditions.get("workflowstatus").toString().equals(""))
            tamsWorkflowStatus.setWorkflowStatus(status);
        else
            tamsWorkflowStatus.setWorkflowStatus(conditions.get("workflowstatus"));
        if(conditions.get("workfloworder").toString().equals(""))
            tamsWorkflowStatus.setOrder(Integer.parseInt(order));
        else
            tamsWorkflowStatus.setOrder(Integer.parseInt(conditions.get("workfloworder")));
        KRADServiceLocator.getDataObjectService().save(tamsWorkflowStatus);
        return true;
    }
    */
    //保存工作流类型
    @Override
    public boolean saveOne(TAMSWorkflowStatus tamsWorkflowStatus) {
        KRADServiceLocator.getDataObjectService().save(tamsWorkflowStatus);
        return true;
    }

    //删除工作流类型
    @Override
    public boolean deleteOne(TAMSWorkflowStatus tamsWorkflowStatus){
        KRADServiceLocator.getDataObjectService().delete(tamsWorkflowStatus);
        return true;
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

    @Override
    public TAMSWorkflowStatus getOneById(String id){
        return KradDataServiceLocator.getDataObjectService().find(TAMSWorkflowStatus.class, id);

    }

}
