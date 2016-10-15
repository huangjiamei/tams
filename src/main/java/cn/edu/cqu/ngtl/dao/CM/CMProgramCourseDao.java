package cn.edu.cqu.ngtl.dao.CM;

import cn.edu.cqu.ngtl.dataobject.CM.CMProgramCourse;

/**
 * Created by tangjing on 16-10-15.
 */
public interface CMProgramCourseDao {

    CMProgramCourse selectByCourseId(Integer courseId);

}
