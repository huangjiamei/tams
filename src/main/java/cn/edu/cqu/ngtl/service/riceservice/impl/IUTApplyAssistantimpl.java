package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.ut.impl.UTClassInfoDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTClassInstructorDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTCourseDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTStudentDaoJpa;
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
        //TODO
        return null;
    }
}
