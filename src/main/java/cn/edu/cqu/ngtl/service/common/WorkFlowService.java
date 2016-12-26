package cn.edu.cqu.ngtl.service.common;

/**
 * Created by awake on 2016/12/25.
 */
public interface WorkFlowService {

    /**
     * 是否是某个工作流状态里的最终状态
     * @param statusId
     * @param functionId
     * @return
     */
    boolean isMaxOrderOfThisStatue(String statusId,String functionId);

    String getWorkFlowStatusName(String statusId);
}
