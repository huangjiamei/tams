package cn.edu.cqu.ngtl.service.classservice.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTClassDao classDao;

    @Override
    public List<UTClassInformation> getAllClassesMappedByDepartment() {

        /** Access DataBase */
        List<UTClassInformation> classInformations = classInfoDao.getAllClassInformation();
        for (UTClassInformation perInformation : classInformations) {

            /** Access DataBase */
            /** 等待最新的性能解决方案    **/
            //CMProgramCourse programCourse = programCourseDao.selectByCourseId(clazz.getCourseId());

        }

        return classInformations;
    }

    @Override
    public UTClass getClassInfoById(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;
    }
}
