package cn.edu.cqu.ngtl.service.courseservice.impl;

import cn.edu.cqu.ngtl.dao.ut.impl.UTClassInfoDaoJpa;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class CourseInfoServiceimpl implements ICourseInfoService {

    @Autowired
    private UTClassInfoDaoJpa utClassInfoDaoJpa;

    @Override
    public List<UTClassInformation> getAllCoursesMappedByDepartment() {

        /** Access DataBase */
        List<UTClassInformation> classInformations = utClassInfoDaoJpa.getAllClassInformation();
        for (UTClassInformation perInformation : classInformations) {

            /** Access DataBase */
            /** 等待最新的性能解决方案    **/
            //CMProgramCourse programCourse = programCourseDao.selectByCourseId(clazz.getCourseId());

        }

        return classInformations;
    }

}
