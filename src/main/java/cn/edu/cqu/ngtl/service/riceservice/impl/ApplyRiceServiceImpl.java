package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.cm.impl.CMCourseClassificationDaoJpa;
import cn.edu.cqu.ngtl.dao.cm.impl.CMProgramCourseDaoJpa;
import cn.edu.cqu.ngtl.dao.cm.impl.CMProgramDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.*;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.service.riceservice.ApplyRiceService;
import cn.edu.cqu.ngtl.viewobject.course.ApplyViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 金祖增 on 2016/10/20.
 */

@Service
public class ApplyRiceServiceImpl implements ApplyRiceService{

    @Autowired
    private UTClassInstructorDaoJpa daoUTClassInstructor;
    @Autowired
    private UTClassDaoJpa daoUTClass;
    @Autowired
    private UTCourseOfferingDaoJpa daoUTCourseOffering;
    @Autowired
    private UTCourseDaoJpa daoUTCourse;
    @Autowired
    private CMProgramCourseDaoJpa daoCMProgramCourse;
    @Autowired
    private CMCourseClassificationDaoJpa daoCMCourseClassification;
    @Autowired
    private UTSessionDaoJpa daoUTSession;
    @Autowired
    private CMProgramDaoJpa daoCMProgram;


    @Override
    public ApplyViewObject applyForAssisstant(User user)
    {
        ApplyViewObject viewObject = new ApplyViewObject();

        UTClassInstructor dataUTClassInstructor = daoUTClassInstructor.getClassInstructorByCode(user.getCode());

        UTClass dataUTClass = daoUTClass.getUTClassByID(dataUTClassInstructor.getClassId());

        UTCourseOffering dataUTCourseOffering = daoUTCourseOffering.getUTCourseOfferingByID(dataUTClass.getCourseOfferingId());

        UTCourse dataUTCourse = daoUTCourse.selectOneById(dataUTCourseOffering.getCourseId());

        CMProgramCourse dataCMProgramCourse = daoCMProgramCourse.selectByCourseId(dataUTCourse.getId());

        CMCourseClassification dataCMCourseClassification = daoCMCourseClassification.getCMCourseClassficationById(dataCMProgramCourse.getClassificationId());

        UTSession dataUTSession = daoUTSession.getUTSessionById(dataUTCourseOffering.getSessionId());

        Integer year = (Integer.parseInt(dataCMProgramCourse.getSemester())+1)/2;

        CMProgram dataCMProgram = daoCMProgram.getCMProgramById(dataCMProgramCourse.getProgramId());


        //-------------------------------------AddValue-------------------------------------------------
        viewObject.setTeacherName(user.getName());

        viewObject.setTeacherType(user.getType());

        viewObject.setClassNumber(dataUTClass.getClassNumber());

        viewObject.setCourseName(dataUTCourse.getName());

        viewObject.setCoureseNumber(dataUTCourse.getNumber().toString());

        viewObject.setStudyTime(dataUTCourse.getHour());

        viewObject.setStudyCode(dataUTCourse.getCodeR());

        viewObject.setRequired(dataCMProgramCourse.getRequired());

        viewObject.setCourseType(dataCMCourseClassification.getName());

        viewObject.setGrade((dataUTSession.getVersionNumber() - year) + "");

        viewObject.setProgramName(dataCMProgram.getName());

        //--------------------------目前没有值的加了默认---------------------------------------

        viewObject.setStudentNumber(100+"");

        viewObject.setAssisstantNumber(1+"");

        return viewObject;
    }

}
