package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;

import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
public interface TAMSWorkflowStatusDao {
    List<TAMSWorkflowStatus> selectAll();
}
