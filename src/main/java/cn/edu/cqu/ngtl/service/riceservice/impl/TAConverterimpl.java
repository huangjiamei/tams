package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.course.ClassTeacherViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAConverterimpl implements ITAConverter {

    @Autowired
    private ICourseInfoService courseInfoService;

    @Override
    public List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informationlist) {

        List<ClassTeacherViewObject> viewObjects = new ArrayList<>(informationlist.size());

        for (UTClassInformation information : informationlist) {
            ClassTeacherViewObject viewObject = new ClassTeacherViewObject();

            //if(clazz.getUtInstructors() != null && clazz.getUtInstructors().size() != 0)
            viewObject.setInstructorName("test");

            viewObject.setClassNumber(information.getClassNumber());

            viewObject.setDepartmentName(information.getDeptName());
            viewObject.setCourseName(information.getCourseName());
            viewObject.setCourseHour(information.getHour());
            viewObject.setCourseCode(information.getCourseCode());
            viewObject.setCourseCredit(information.getCredit().toString());

            //if (programCourse != null) {
            viewObject.setCourseClassification("test");
            viewObject.setIsRequired("必修");
            viewObject.setProgramName("CS");
            //}
            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public ApplyViewObject classInfoToApplyObject(User user, UTClass clazz) {
        ApplyViewObject viewObject = new ApplyViewObject();

        viewObject.setTeacherName(user.getName());

        viewObject.setTeacherType(user.getType());

        viewObject.setClassNumber(clazz.getClassNumber());

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if(course != null) {
            viewObject.setCourseName(course.getName());

            viewObject.setCoureseNumber(course.getNumber().toString());

            viewObject.setStudyTime(course.getHour());

            viewObject.setStudyCode(course.getCodeR());

            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if(programCourse != null) {

            viewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");

            if(programCourse.getClassification() != null)
                viewObject.setCourseType(programCourse.getClassification().getName());

            viewObject.setProgramName(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        if(session != null) {

            Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;

            viewObject.setGrade((session.getVersionNumber() - year) + "");

        }
        //--------------------------目前没有值的加了默认---------------------------------------
        viewObject.setStudentNumber(100+"");

        viewObject.setAssisstantNumber(1+"");

        return viewObject;
    }
}
