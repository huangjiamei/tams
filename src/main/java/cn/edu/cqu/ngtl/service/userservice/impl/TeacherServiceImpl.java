package cn.edu.cqu.ngtl.service.userservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMProgramCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.enums.CM_COURSE;
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

            if (programCourse != null) {
                viewObject.setCourseClassification(programCourse.getClassification().getName());
                viewObject.setIsRequired((programCourse.getRequired()== CM_COURSE.REQUIRED) ? "必修" : "选修");
                viewObject.setProgramName(programCourse.getProgram().getName());
            }

            viewObject.setInstructors(clazz.getUtInstructors());
            viewObject.setDepartmentName(currentCourse.getDepartment().getName());
            viewObject.setCourseName(currentCourse.getName());
            viewObject.setCourseHour(currentCourse.getHour());
            viewObject.setCourseCode(currentCourse.getCodeR());
            viewObject.setCourseCredit(currentCourse.getCredit());


            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

}
