package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassTaApplication;

import java.util.Map;

/**
 * Created by tangjing on 16-11-26.
 */
public interface TAMSClassTaApplicationDao {
    TAMSClassTaApplication selectByInstructorIdAndClassId(String instructorId, String classId);

    TAMSClassTaApplication insertOneByEntity(TAMSClassTaApplication classTaApplication);

    TAMSClassTaApplication selectByClassId(String classId);

    Map getAllClassAndHourAndFundsAndTaCounts();
}
