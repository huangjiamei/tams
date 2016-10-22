package cn.edu.cqu.ngtl.service.classservice.impl;

import cn.edu.cqu.ngtl.dao.cm.impl.CMCourseClassificationDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.*;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassInfoViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Autowired
    private UTClassDaoJpa utClassDaoJpa;

    @Autowired
    private UTCourseDaoJpa utCourseDaoJpa;

    @Autowired
    private CMCourseClassificationDaoJpa cmCourseClassificationDaoJpa;


    @Autowired
    private UTClassInstructorDaoJpa utClassInstructorDaoJpa;

    @Autowired
    private UTInstructorDaoJpa utInstructorDaoJpa;

    @Autowired
    private UTDepartmentDaoJpa utDepartmentDaoJpa;

    @Override
    public ClassInfoViewObject getClassInfoById(Integer classId) {
        ClassInfoViewObject classInfoViewObject = new ClassInfoViewObject();

        UTClass utClass = utClassDaoJpa.selectByClassId(classId);
        UTCourse utCourse = utCourseDaoJpa.selectOneById(utClass.getCourseOfferingId());
        UTClassInstructor utClassInstructor = utClassInstructorDaoJpa.selectOneByClassId(classId);
        UTInstructor utInstructor = utInstructorDaoJpa.getInstructorByIdWithoutCache(utClassInstructor.getInstructorId());
        UTDepartment utDepartment = utDepartmentDaoJpa.getUTDepartmentById(utCourse.getDepartmentId());

        classInfoViewObject.setClassName(utCourse.getName());
        classInfoViewObject.setStudentNumber(utClass.getLimit().toString());
        classInfoViewObject.setClassId(utCourse.getCodeR());
        classInfoViewObject.setClassHour(utCourse.getHour());
        classInfoViewObject.setCredit(utCourse.getCredit().toString());
        classInfoViewObject.setInstructor(utInstructor.getName());
        classInfoViewObject.setCourseDepartment(utDepartment.getName());
        classInfoViewObject.setStudentNumber(utClass.getLimit().toString());

        // 给定默认值
        classInfoViewObject.setRequired("必修");
        classInfoViewObject.setGrade("考试");
        classInfoViewObject.setClassNumber("0011");
        classInfoViewObject.setInstructorNumber("1");
        classInfoViewObject.setTaNumber("1");
        classInfoViewObject.setWorkHour("20");
        classInfoViewObject.setGradePercent("20%+20%+60%");
        classInfoViewObject.setClassKind("公共基础");
        return classInfoViewObject;
    }
}
