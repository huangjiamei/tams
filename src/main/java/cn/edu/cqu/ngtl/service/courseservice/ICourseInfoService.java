package cn.edu.cqu.ngtl.service.courseservice;

import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ICourseInfoService {

    CMProgramCourse getProgramCourseByCourseId(Integer courseId);

}
