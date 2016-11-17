package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaApplicationDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
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

    @Autowired
    private UTSessionDao sessionDao;

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

    @Override
    public boolean changeStatusBatchByIds(List<String> ids, String status) {

        List<TAMSTa> tas = taDao.selectBatchByIds(ids);

        for(TAMSTa ta : tas) {
            ta.setStatus(status);
            //出现错误，跳出循环
            if(!taDao.updateByEntity(ta))
                return false;
        }

        return true;
    }

    @Override
    public boolean employBatchByStuIdsWithClassId(List<StuIdClassIdPair> stuIdClassIdPairs) {
        for(StuIdClassIdPair per : stuIdClassIdPairs) {
            TAMSTa isExist = taDao.selectByStudentIdAndClassId(per.getStuId(),per.getClassId());

            if(isExist != null) {  //数据库中已存在数据
                continue;
//                return false;
                //TODO 应该警告并删除重复的申请信息
            }
            TAMSTa newTa = new TAMSTa();
            //录入基本信息
            newTa.setTaId(per.getStuId());
            newTa.setTaClassId(per.getClassId());
            //预处理录入信息
            newTa.setSessionId(sessionDao.getCurrentSession().getId().toString());
            newTa.setStatus(TA_STATUS.LIVING);

            if(taDao.insertByEntity(newTa)) {
                TAMSTaApplication readyToRemove = applicationDao.selectByStuIdAndClassId(per.getStuId(), per.getClassId());
                if(applicationDao.deleteByEntity(readyToRemove)) {
                    continue;
                }
                else //删除申请信息失败
                    return false;
            }
            else //新建信息失败
                return false;
        }

        return true;
    }

    @Override
    public boolean changeStatusBatchByTaIds(List<String> ids, String status) {
        List<TAMSTa> tas = taDao.selectBatchByTaIds(ids);

        for (TAMSTa ta : tas) {
            ta.setStatus(status);
            if(!taDao.updateByEntity(ta))
                return false;
        }

        return true;
    }
}
