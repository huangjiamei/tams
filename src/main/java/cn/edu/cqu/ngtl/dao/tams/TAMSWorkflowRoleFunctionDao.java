package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowRoleFunction;

/**
 * Created by tangjing on 16-11-11.
 */
public interface TAMSWorkflowRoleFunctionDao {

    String selectIdByRoleIdAndFunctionId(String roleId, String functionId);

    boolean setRoleFunction(TAMSWorkflowRoleFunction dataTAMSWorkflowRoleFunction);

    TAMSWorkflowRoleFunction insertByEntity(TAMSWorkflowRoleFunction workflowRoleFunction);
}
