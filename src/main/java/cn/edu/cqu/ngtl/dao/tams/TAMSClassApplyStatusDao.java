package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassApplyStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;

import java.util.List;

/**
 * Created by tangjing on 16-11-26.
 */
public interface TAMSClassApplyStatusDao {

    boolean toNextStatus(String[] roleIds, String functionId, String classId);

    boolean isInitializedStatus(String functionId, String classId);

    boolean toPreviousStatus(String[] roleIds, String functionId, String classId);

    void saveApplyStatueByList(List<TAMSClassApplyStatus> tamsClassApplyStatuses);

    List<TAMSWorkflowStatus> getAvailableStatus(String[] roleIds, String functionId, String classId);

    boolean changeStatusToCertainStatus(String classId, String workflowStatusId);
}
