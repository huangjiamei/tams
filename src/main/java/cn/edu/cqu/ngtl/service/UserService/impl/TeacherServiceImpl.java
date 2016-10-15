package cn.edu.cqu.ngtl.service.UserService.impl;

import cn.edu.cqu.ngtl.dao.CM.CMProgramCourseDao;
import cn.edu.cqu.ngtl.dao.UT.UTClassDao;
import cn.edu.cqu.ngtl.dataobject.CM.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.UT.UTClass;
import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;
import cn.edu.cqu.ngtl.dataobject.enums.CM_COURSE;
import cn.edu.cqu.ngtl.service.UserService.ITeacherService;
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
    private CMProgramCourseDao programCourseDao;

    @Autowired
    private UTClassDao classDao;

    @Override
    public List<CourseTeacherViewObject> getAllCoursesMappedByDepartment() {

        List<CourseTeacherViewObject> viewObjects = new ArrayList<>();

        /** Access DataBase */
        List<UTClass> classes = classDao.selectAllMappedByDepartment();

        for(UTClass clazz : classes) {
            CourseTeacherViewObject viewObject = new CourseTeacherViewObject();
            UTCourse currentCourse = clazz.getCourseOffering().getCourse();

            /** Access DataBase */
            CMProgramCourse programCourse = programCourseDao.selectByCourseId(currentCourse.getId());

            viewObject.setInstructors(clazz.getUtInstructors());
            viewObject.setDepartmentName(currentCourse.getDepartment().getName());
            viewObject.setCourseName(currentCourse.getName());
            viewObject.setCourseHour(currentCourse.getHour());
            viewObject.setCourseClassification(programCourse.getClassification().getName());
            viewObject.setCourseCode(currentCourse.getCodeR());
            viewObject.setCourseCredit(currentCourse.getCredit());
            viewObject.setIsRequired((programCourse.getRequired()== CM_COURSE.REQUIRED) ? "必修" : "选修");
            viewObject.setProgramName(programCourse.getProgram().getName());

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

}
