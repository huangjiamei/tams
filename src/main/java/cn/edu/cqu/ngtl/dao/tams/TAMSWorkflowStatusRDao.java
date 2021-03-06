package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatusR;

import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
public interface TAMSWorkflowStatusRDao {

    List<TAMSWorkflowStatusR> selectByRFId(String RFId);

    boolean saveTAMSWorkflowStatusR(TAMSWorkflowStatusR dataTAMSWorkflowStatusR);

    void deleteTAMSWorkflowStatusRByRFId(String RFId);

    List<TAMSWorkflowStatusR> selectByRFIdAndStatus1(String rfId, String statusId1);
}
