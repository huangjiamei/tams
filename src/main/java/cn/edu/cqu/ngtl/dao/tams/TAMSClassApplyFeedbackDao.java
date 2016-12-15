package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassApplyFeedback;

import java.util.List;

/**
 * Created by awake on 2016/12/15.
 */
public interface TAMSClassApplyFeedbackDao {

    List<TAMSClassApplyFeedback> getFbByClassId(String classId);

    boolean saveFbByEntity(TAMSClassApplyFeedback tamsClassApplyFeedback);

}
