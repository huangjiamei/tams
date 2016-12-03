package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaApplicationDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaTravelSubsidyDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTeachCalendarDao;
import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAServiceimpl implements ITAService {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

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

    @Autowired
    private UTStudentDao studentDao;

    @Autowired
    private TAMSTaTravelSubsidyDao tamsTaTravelSubsidyDao;

    //根据姓名和学号查找候选人
    public List<UTStudent> getConditionTaByNameAndId(Map<String, String> conditions){
        List<UTStudent> studentInfo = studentDao.selectStudentByNameAndId(conditions);
        return studentInfo;
    }

    //根据studentid查询担任助教的classids
    public List<Object> getClassIdsByUid(){
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        return taDao.selectClassIdsByStudentId(user.getTag());
    }


    //根据classids查询classinfo的信息
    @Autowired
    private TAMSTeachCalendarDao tamsTeachCalendarDao;

    @Override
    public List<WorkBenchViewObject> getClassInfoByIds(List<Object> ids) {
        return taDao.selectAllCourseInfoByIds(ids);
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

    //添加申请人
    @Override
    public boolean submitApplicationAssistant(TAMSTaApplication application) {

        if (tamsTaApplicationDao.insertOne(application))
            return true;
        else
            return false;
    }


    //根据用户id显示助教列表，不同用户助教列表不同（包括tamanagement和talist界面）
    @Override
    public List<TAMSTa> getAllTaFilteredByUid(String uId) {

        //// FIXME: 16-11-1 测试所以加上了!,正式发行的时候务必去掉 非运算符 '!'
        //系统管理员，查看全校所有助教
        //测试：01302022
        if (userInfoService.isSysAdmin(uId))
            return taDao.selectAll();

            //教务处管理员，查看全校所有助教
            //测试:02015508
            else if (userInfoService.isAcademicAffairsStaff(uId))
                return taDao.selectAll();

            //学院管理员(二级单位管理员)，查看本学院课程的助教
            //测试：02015402光电学院
        else if(userInfoService.isCollegeStaff(uId)){//先根据uId查询对应的学院id
            User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
            return taDao.selectByDeptId(user.getDepartmentId().toString());
        }

                //教师，查看自己课程的助教
                //测试：01012657
                else {
                     //先根据教师id查到该教师所教授的批量课程id，然后再根据批量的课程id查出所有的助教
                     List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
                     return taDao.selectByClassIds(classIds);
                }

    }



    //根据uid查看申请者列表
    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByUid(String uId) {

        List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

        return applicationDao.selectByClassIds(classIds);
    }


/*

    //根据classId查看申请者列表
    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByUid(String classId) {
        return tamsTaApplicationDao.selectByClassId(classId);
    }
*/

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

    //聘用助教
    @Override
    public boolean employBatchByStuIdsWithClassId(List<StuIdClassIdPair> stuIdClassIdPairs) {
        for(StuIdClassIdPair per : stuIdClassIdPairs) {
            TAMSTa isExist = taDao.selectByStudentIdAndClassId(per.getStuId(),per.getClassId());

            if(isExist != null) {  //数据库中已存在数据
                TAMSTaApplication readyToRemove = applicationDao.selectByStuIdAndClassId(per.getStuId(), per.getClassId());
                applicationDao.deleteByEntity(readyToRemove);
                continue;
                //TODO 应该警告并删除重复的申请信息
            }
            TAMSTa newTa = new TAMSTa();
            //录入基本信息
            newTa.setTaId(per.getStuId());
            newTa.setTaClassId(per.getClassId());
            //预处理录入信息
            newTa.setSessionId(sessionDao.getCurrentSession().getId().toString());
            newTa.setStatus(TA_STATUS.LIVING);
            TAMSTaApplication readyToRemove = applicationDao.selectByStuIdAndClassId(per.getStuId(), per.getClassId());
            newTa.setApplicationNote(readyToRemove.getNote());
            if(taDao.insertByEntity(newTa)) {
                if(applicationDao.deleteByEntity(readyToRemove)) {
                    continue;
                }
                else
                    //删除申请信息失败
                    return false;
            }
            else
                //新建信息失败
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


    @Override
    public List<TAMSTeachCalendar> getTeachCalendarByClassId(String classId){
        return tamsTeachCalendarDao.selectAllByClassId(classId);
    }


    @Override
    public TAMSTa getTaByTaId(String taId,String classId){
        return taDao.selectByStudentIdAndClassId(taId,classId);
    }

    @Override
    public List<TAMSTaTravelSubsidy> getTaTravelByStuIdAndClassId(String taId, String classId){
        return  tamsTaTravelSubsidyDao.getTAMSTaTravelSubsidyByStuIdAndTaId(taId,classId);
    }

    @Override
    public boolean saveTravelSubsidy(TAMSTaTravelSubsidy tamsTaTravelSubsidy){

        return tamsTaTravelSubsidyDao.insertOneByEntity(tamsTaTravelSubsidy);

    }


}
