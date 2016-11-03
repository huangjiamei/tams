package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaApplicationDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAServiceimpl implements ITAService {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTClassDao classDao;

    @Autowired
    private TAMSTaApplicationDao tamsTaApplicationDao;

    @Autowired
    private TAMSTaDao taDao;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    @Autowired
    private TAMSTaApplicationDao applicationDao;

    @Override
    public UTClassInformation getClassInfoById(Integer id) {
        return classInfoDao.getOneById(id);
    }

    @Override
    public UTClass applicationTable(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;

    }

    @Override
    public UTClass applicationAssistantTable(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;

    }

    @Override
    public boolean submitApplicationAssistant(TAMSTaApplication application) {

        if(tamsTaApplicationDao.insertOne(application))
            return true;

        return false;
    }

    @Override
    public List<TAMSTa> getAllTaFilteredByUid(String uId) {

        //// FIXME: 16-11-1 测试所以加上了!,正式发行的时候务必去掉 非运算符 '!'
        if(!userInfoService.isSysAdmin(uId))
            return taDao.selectAll();
        else {
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

            return taDao.selectByClassId(classIds);
        }
    }

    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByUid(String uId) {

        List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

        return applicationDao.selectByClassId(classIds);
    }
}
