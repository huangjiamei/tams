package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;

import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
public interface TAMSWorkflowStatusDao {

    List<TAMSWorkflowStatus> selectAll();

    List<TAMSWorkflowStatus> selectByFunctionId(String functionId);

    boolean isFirstStatus(String workflowStatusId);

    List<TAMSWorkflowStatus> selectWorkFlowByCondition(String workflowfunction);

    //boolean insertOne(Map<String, String> conditions);

    //boolean modifyOne(Map<String, String> conditions, String status, String order);

    boolean saveOne(TAMSWorkflowStatus tamsWorkflowStatus);

    boolean deleteOne(TAMSWorkflowStatus tamsWorkflowStatus);

    TAMSWorkflowStatus getOneById(String id);

    Integer getMaxOrderByFunctionId(String functionId);

    String  getMaxOrderStatusIdByFunctionId(String functionId);

    String getFirstStatusByFunctionId(String functionId);

    String getSecMaxOrderStatusIdByFunctionId(String functionId);

    TAMSWorkflowStatus getOneByOrderAndFunctionId(String order,String functionId)
}
