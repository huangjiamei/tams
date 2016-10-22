package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
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

            viewObject.setCoureseNumber(course.getCodeR().toString());

            viewObject.setStudyTime(course.getHour());

            viewObject.setStudyCode(course.getCredit()+"");

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

            viewObject.setGrade((Integer.parseInt(session.getYear()) - year) + "");

        }
        //--------------------------目前没有值的加了默认---------------------------------------
        viewObject.setStudentNumber(100+"");

        viewObject.setAssisstantNumber(1+"");

        return viewObject;
    }

    public ApplyAssistantViewObject applyAssistantToTableViewObject(User user, UTClass clazz) {

        ApplyAssistantViewObject viewObject = new ApplyAssistantViewObject();

        if(user != null){

            viewObject.setUsername(user.getName());
            viewObject.setStudentId(user.getType());

        }

        if(clazz != null){
            StringBuilder sb = new StringBuilder();
            if(clazz.getUtInstructors()!=null){
                for(UTInstructor instructor : clazz.getUtInstructors()){
                    sb.append(instructor.getName() + ",\n");
                }
            }
            viewObject.setApplyTeacher(sb.toString());
        }

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if(course != null){
            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());

            viewObject.setClassHour(course.getHour());
        }

        if(programCourse != null){
            viewObject.setApplyCourseType(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        //缺失
        viewObject.setApplyTeacher("缺省");

        return viewObject;
    }

    @Override
    public ClassInfoViewObject classInfoToViewObject(UTClass clazz) {
        ClassInfoViewObject classInfoViewObject = new ClassInfoViewObject();

        UTCourse course = new UTCourse();

        if(clazz!=null){
            course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;
            classInfoViewObject.setClassNumber(clazz.getClassNumber());

            StringBuilder sb = new StringBuilder();
            if(clazz.getUtInstructors()!=null){
                for(UTInstructor instructor : clazz.getUtInstructors()){
                    sb.append(instructor.getName() + ",\n");
                }
            }
            classInfoViewObject.setInstructor(sb.toString());
        }

        if(course != null){

            classInfoViewObject.setClassId(course.getCodeR());
            classInfoViewObject.setClassHour(course.getHour());
            classInfoViewObject.setCredit(course.getCredit()+"");
        }

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;
        CMProgramCourse programCourse = new CMProgramCourse();
        CMProgram program = new CMProgram();

        if(programCourse != null){

            classInfoViewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");
            if(programCourse.getClassification() != null)
                classInfoViewObject.setClassKind(programCourse.getClassification().getName());

            classInfoViewObject.setClassName(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);

            program = programCourse.getProgram() != null ? programCourse.getProgram() : null;
        }

        if(session != null) {

            Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;

            classInfoViewObject.setDepartmentAndGrade((Integer.parseInt(session.getYear()) - year) + "");

        }

        UTDepartment department = new UTDepartment();

        if(program != null){
            classInfoViewObject.setClassName(program.getName());

            department = program.getDepartment() != null ? program.getDepartment() :null;
        }

        if(department != null){
            classInfoViewObject.setCourseDepartment(department.getName());
        }

        return classInfoViewObject;
    }

}
