package cn.edu.cqu.ngtl.dao.tams;

/**
 * Created by tangjing on 16-11-11.
 */
public interface TAMSWorkflowRoleFunctionDao {

    String selectIdByRoleIdAndFunctionId(String roleId, String functionId);

}
