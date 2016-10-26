package cn.edu.cqu.ngtl.service.adminservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangjing on 16-10-25.
 */
@Service
public class AdminServiceImpl implements IAdminService{

    @Autowired
    private CMCourseClassificationDao courseClassificationDao;

    @Override
    public List<CMCourseClassification> getAllClassification() {
        List<CMCourseClassification> courseClassifications = courseClassificationDao.selectAll();
        return courseClassifications.size() != 0 ? courseClassifications : null;
    }

    @Override
    public boolean addCourseClassificationOnlyWithName(String name) {
        CMCourseClassification courseClassification = new CMCourseClassification();
        courseClassification.setName(name);

        return courseClassificationDao.insertOneByEntity(courseClassification);
    }

    @Override
    public boolean changeCourseClassificationNameById(Integer id, String name) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if(courseClassification == null)
            return false;

        courseClassification.setId(id);
        courseClassification.setName(name);

        return courseClassificationDao.updateOneByEntity(courseClassification);
    }

    @Override
    public boolean removeCourseClassificationById(Integer id) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if(courseClassification == null)
            return false;

        return courseClassificationDao.deleteOneByEntity(courseClassification);
    }
}
