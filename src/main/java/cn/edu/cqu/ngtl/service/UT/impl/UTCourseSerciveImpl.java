package cn.edu.cqu.ngtl.service.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;
import cn.edu.cqu.ngtl.service.UT.UTCourseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tangjing on 16-10-13.
 */
public class UTCourseSerciveImpl implements UTCourseService {

    @Autowired
    UTCourseDao courseDao;

    @Override
    public UTCourse getCourseById(Integer id) {

        return courseDao.selectOneById(id);

    }

}
