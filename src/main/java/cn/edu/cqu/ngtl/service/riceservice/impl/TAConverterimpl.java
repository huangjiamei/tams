package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            viewObject.setId(information.getId());
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

        if (course != null) {
            viewObject.setCourseName(course.getName());

            viewObject.setCourseNumber(course.getCodeR().toString());

            viewObject.setStudyTime(course.getHour());

            viewObject.setStudyCode(course.getCredit() + "");

            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if (programCourse != null) {

            viewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");

            if (programCourse.getClassification() != null)
                viewObject.setCourseType(programCourse.getClassification().getName());

            viewObject.setProgramName(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        if (session != null) {

            Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;

            viewObject.setGrade((Integer.parseInt(session.getYear()) - year) + "");

        }
        //--------------------------目前没有值的加了默认---------------------------------------
        viewObject.setStudentNumber(100 + "");

        viewObject.setAssisstantNumber(1 + "");

        return viewObject;
    }

    public ApplyAssistantViewObject applyAssistantToTableViewObject(UTStudent student, UTClass clazz) {

        ApplyAssistantViewObject viewObject = new ApplyAssistantViewObject();

        if (student != null) {

            viewObject.setUsername(student.getName());
            viewObject.setStudentId(student.getId());
            viewObject.setUg_Major(student.getProgram() != null ?
                    student.getProgram().getName() : null);

            /** 需要修改 */
            viewObject.setG_Major("挖掘机");
        }

        if (clazz != null) {
            viewObject.setClassId(clazz.getId());

            StringBuilder sb = new StringBuilder();
            if (clazz.getUtInstructors() != null) {
                for (UTInstructor instructor : clazz.getUtInstructors()) {
                    sb.append(instructor.getName() + ",\n");
                }
            }
            viewObject.setApplyTeacher(sb.toString());
        }

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if (course != null) {
            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());

            viewObject.setClassHour(course.getHour());
        }

        if (programCourse != null) {
            viewObject.setApplyCourseType(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        return viewObject;
    }

    @Override
    public ClassDetailInfoViewObject classInfoToViewObject(UTClass clazz) {
        ClassDetailInfoViewObject classDetailInfoViewObject = new ClassDetailInfoViewObject();

        UTCourse course = new UTCourse();

        if (clazz != null) {
            classDetailInfoViewObject.setClassNumber(clazz.getClassNumber());
            course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

            //classInstructor为空的数据库，无法查询
            StringBuilder sb = new StringBuilder();
            Integer countTeacherNum = 0;
            if (clazz.getUtInstructors() != null) {

                for (UTInstructor instructor : clazz.getUtInstructors()) {
                    countTeacherNum++;
                    sb.append(instructor.getName() + ",\n");
                }
            }
            classDetailInfoViewObject.setInstructor(sb.toString());
            classDetailInfoViewObject.setInstructorNumber(countTeacherNum.toString());
        }

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;
        CMProgramCourse programCourse = new CMProgramCourse();
        CMProgram program = new CMProgram();

        if (course != null) {
            classDetailInfoViewObject.setCourseName(course.getName());
            classDetailInfoViewObject.setCourseId(course.getCodeR());
            classDetailInfoViewObject.setClassHour(course.getHour());   //目前为空
            classDetailInfoViewObject.setCredit(course.getCredit() + "");
            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if (programCourse != null) {

            classDetailInfoViewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");
            if (programCourse.getClassification() != null)
                classDetailInfoViewObject.setCourseClassification(programCourse.getClassification().getName());


            program = programCourse.getProgram() != null ? programCourse.getProgram() : null;


            UTDepartment department = new UTDepartment();
            if (session != null) {

                Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;


                if (program != null) {

                    classDetailInfoViewObject.setDepartmentAndGrade((Integer.parseInt(session.getYear()) - year) + "/" + program.getName());
                    department = program.getDepartment() != null ? program.getDepartment() : null;
                }
                if (department != null) {
                    classDetailInfoViewObject.setCourseDepartment(department.getName());
                }

            }
        }


        return classDetailInfoViewObject;
    }

    @Override
    public TAMSTaApplication submitInfoToTaApplication(ClassInfoForm form) {
        TAMSTaApplication application = new TAMSTaApplication();

        application.setApplicationId(form.getApplyAssistantViewObject().getStudentId());
        application.setApplicationClassId(form.getApplyAssistantViewObject().getClassId().toString());
        application.setApplicationTime(new StringDateConverter().convertToEntityAttribute(new Date()));
        application.setNote(form.getApplyReason());
        application.setEduBackground(form.getEduBackground());

        return application;
    }

    @Override
    public List<TermManagerViewObject> termInfoToViewObject(List<UTSession> sessions) {

        List<TermManagerViewObject> viewObjects = new ArrayList<>(sessions.size());

        for(UTSession session : sessions) {
            TermManagerViewObject viewObject = new TermManagerViewObject();
            viewObject.setTermName(session.getYear() + "年" + session.getTerm() + "季");
            viewObject.setEndDate(session.getEndDate());
            viewObject.setStartDate(session.getBeginDate());

            //// FIXME: 16-10-27 日后需要加上预算信息
            viewObject.setBudget(100000);

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public UTSession newTermToDataObject(TermManagerViewObject newTerm) {
        UTSession session = new UTSession();

        //// FIXME: 16-10-27 需要通过前端返回两个数据
        session.setYear("2016");
        session.setTerm("秋");

        session.setBeginDate(newTerm.getStartDate());
        session.setEndDate(newTerm.getEndDate());

        return session;
    }
}
