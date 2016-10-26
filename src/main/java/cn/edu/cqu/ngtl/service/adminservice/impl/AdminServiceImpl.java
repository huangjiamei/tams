package cn.edu.cqu.ngtl.service.adminservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaCategoryDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
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

    @Autowired
    private TAMSTaCategoryDao tamsTaCategoryDao;

    @Override
    public List<CMCourseClassification> getAllClassification() {
        List<CMCourseClassification> courseClassifications = courseClassificationDao.selectAll();
        return courseClassifications.size() != 0 ? courseClassifications : null;
    }

    @Override
    public boolean addCourseClassificationOnlyWithName(String name) {

        if(courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
            return false;

        CMCourseClassification courseClassification = new CMCourseClassification();
        courseClassification.setName(name);

        return courseClassificationDao.insertOneByEntity(courseClassification);
    }

    @Override
    public boolean changeCourseClassificationNameById(Integer id, String name) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if(courseClassification == null)
            return false;
        if(courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
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

    @Override
    public List<TAMSTaCategory> getAllTaCategories() {
        List<TAMSTaCategory> tamsTaCategories = tamsTaCategoryDao.selectAll();
        return tamsTaCategories.size() != 0 ? tamsTaCategories : null;
    }

    @Override
    public boolean addTaCategory(TAMSTaCategory newTaCategory) {
        if(tamsTaCategoryDao.selectOneByName(newTaCategory.getName()) != null)
            //存在同名数据
            return false;

        return tamsTaCategoryDao.insertOneByEntity(newTaCategory);
    }

    @Override
    public boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory) {
        if(tamsTaCategoryDao.selectOneByName(tamsTaCategory.getName()) != null)
            //存在同名数据
            return false;

        return tamsTaCategoryDao.updateOneByEntity(tamsTaCategory);
    }
}
