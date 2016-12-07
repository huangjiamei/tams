package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;

import java.util.List;
import java.util.Map;

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
}
