package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassEvaluation;

import java.util.List;

/**
 * Created by tangjing on 16-11-26.
 */
public interface TAMSClassEvaluationDao {
    void deleteAllByClassId(String classId);

    boolean insertOneByEntity(TAMSClassEvaluation classEvaluation);

    List<TAMSClassEvaluation> getAllByClassId(String classId);
}
