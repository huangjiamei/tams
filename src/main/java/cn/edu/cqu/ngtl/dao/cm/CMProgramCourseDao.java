package cn.edu.cqu.ngtl.dao.cm;

import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;

/**
 * Created by tangjing on 16-10-15.
 */
public interface CMProgramCourseDao {

    CMProgramCourse selectByCourseId(Integer courseId);

}
