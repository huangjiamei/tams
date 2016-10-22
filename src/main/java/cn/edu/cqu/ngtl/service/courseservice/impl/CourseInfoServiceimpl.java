package cn.edu.cqu.ngtl.service.courseservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMProgramCourseDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class CourseInfoServiceimpl implements ICourseInfoService {

    @Autowired
    private CMProgramCourseDao programCourseDao;

    @Override
    public CMProgramCourse getProgramCourseByCourseId(Integer courseId) {

        return programCourseDao.selectByCourseId(courseId);

    }
}
