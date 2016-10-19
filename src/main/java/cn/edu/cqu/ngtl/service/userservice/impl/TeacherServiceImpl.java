package cn.edu.cqu.ngtl.service.userservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMProgramCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTClassInfoDaoJpa;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.userservice.ITeacherService;
import cn.edu.cqu.ngtl.viewobject.course.CourseTeacherViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
@Service
public class TeacherServiceImpl implements ITeacherService {


    @Autowired
    private UTClassInfoDaoJpa utClassInfoDaoJpa;

    @Autowired
    private CMProgramCourseDao programCourseDao;

    @Autowired
    private UTClassDao classDao;

    @Override
    public List<CourseTeacherViewObject> getAllCoursesMappedByDepartment() {

        List<CourseTeacherViewObject> viewObjects = new ArrayList<>();

        /** Access DataBase */
        List<UTClassInformation> classes = utClassInfoDaoJpa.getAllClassInformation();
        for (UTClassInformation clazz : classes) {
            CourseTeacherViewObject viewObject = new CourseTeacherViewObject();

            //if(clazz.getUtInstructors() != null && clazz.getUtInstructors().size() != 0)
            viewObject.setInstructorName("test");

            viewObject.setClassNumber(clazz.getClassNumber());

            viewObject.setDepartmentName(clazz.getDeptName());
            viewObject.setCourseName(clazz.getCourseName());
            viewObject.setCourseHour(clazz.getHour());
            viewObject.setCourseCode(clazz.getCourseCode());
            viewObject.setCourseCredit(clazz.getCredit().toString());

            /** Access DataBase */
            /** 等待最新的性能解决方案    **/
            //CMProgramCourse programCourse = programCourseDao.selectByCourseId(clazz.getCourseId());

            //if (programCourse != null) {
            viewObject.setCourseClassification("test");
            viewObject.setIsRequired("必修");
            viewObject.setProgramName("CS");
            //}
            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

}
