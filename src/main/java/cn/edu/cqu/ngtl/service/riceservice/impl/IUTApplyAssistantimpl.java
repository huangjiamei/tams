package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.cm.impl.CMProgramDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.*;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.riceservice.IUTApplyAssistant;
import cn.edu.cqu.ngtl.viewobject.course.ApplyAssistantViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by long2ice
 * Date on 2016/10/22
 */
@Service
public class IUTApplyAssistantimpl implements IUTApplyAssistant {
    @Autowired
    private UTStudentDaoJpa utStudentDaoJpa;
    @Autowired
    private UTCourseDaoJpa utCourseDaoJpa;
    @Autowired
    private UTClassInfoDaoJpa utClassInfoDaoJpa;
    @Autowired
    private UTClassInstructorDaoJpa utClassInstructorDaoJpa;

    public ApplyAssistantViewObject getApplyAssistantById(User user, Integer id) {
        ApplyAssistantViewObject applyAssistantViewObject = new ApplyAssistantViewObject();

        UTClassInformation utClassInformation = utClassInfoDaoJpa.getOneById(id);
        UTCourse course = utCourseDaoJpa.selectOneById(utClassInformation.getCourseId());
        UTClassInstructor instructor = utClassInstructorDaoJpa.getClassInstructorByCode(user.getCode());
        UTStudent student = utStudentDaoJpa.getUTStudentById(user.getCode());

        applyAssistantViewObject.setUsername(user.getName());
        applyAssistantViewObject.setStudentId(user.getCode());
        applyAssistantViewObject.setClassHour(course.getHour());
        //暂不知道在哪个表
        applyAssistantViewObject.setTeacher("");
        applyAssistantViewObject.setApplyCourseType(course.getSubject());
        applyAssistantViewObject.setApplyTeacher(instructor.getUtInstructor().getName());
        //暂不知道在哪个表
        applyAssistantViewObject.setG_Major("");
        applyAssistantViewObject.setUg_Major("");
        applyAssistantViewObject.setPhone(student.getEmail());
        return applyAssistantViewObject;
    }
}
