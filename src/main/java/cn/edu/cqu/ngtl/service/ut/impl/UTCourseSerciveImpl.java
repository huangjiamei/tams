package cn.edu.cqu.ngtl.service.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.service.ut.IUTCourseService;
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
