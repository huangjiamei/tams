package cn.edu.cqu.ngtl.service.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;
import cn.edu.cqu.ngtl.service.UT.IUTCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tangjing on 16-10-13.
 */
@Service
public class UTCourseSerciveImpl implements IUTCourseService {

    @Autowired
    UTCourseDao courseDao;

    @Override
    public UTCourse getCourseById(Integer id) {

        return courseDao.selectOneById(id);

    }

}
