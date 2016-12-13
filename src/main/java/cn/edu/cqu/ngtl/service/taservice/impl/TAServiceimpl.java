package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_MBR_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAServiceimpl implements ITAService {

    private static final Logger logger = Logger.getRootLogger();

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

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

    @Autowired
    private TAMSWorkflowFunctionsDao workflowFunctionsDao;

    @Autowired
    private TAMSTaDao tamstadao;

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

        UTClass clazz = classDao.selectByClassId(classId.toString());

        return clazz;

    }

    @Override
    public UTClass applicationAssistantTable(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId.toString());

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
                else if(userInfoService.isInstructor(uId)){
                     //先根据教师id查到该教师所教授的批量课程id，然后再根据批量的课程id查出所有的助教
                     List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
                     return taDao.selectByClassIds(classIds);
                }

                //助教，查询自己担任的课程
                else {
            return taDao.selectByTaId(uId);
        }

    }



    //根据uid查看申请者列表
    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByUid(String uId) {

        List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

        return applicationDao.selectByClassIds(classIds);
    }






    //根据classId查看申请者列表
    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId) {
        return tamsTaApplicationDao.selectByClassId(classId);
    }


    @Override
    public boolean changeStatusBatchByIds(List<String> ids, String status) {
        if(ids == null || ids.size() == 0)
            return true;

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
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("评优");
            List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(function.getId());
            if(allStatus != null && !allStatus.isEmpty())
                newTa.setOutStandingTaWorkflowStatusId(allStatus.get(0).getOrder().toString());
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

    @Override
    public boolean appraiseOutstanding(List<String> taIds, String uId) {
        try {
            if(taIds == null || taIds.size() == 0 || uId == null)
                return false;
            //更改课程申请状态
            //默认工作方法为“评优”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(uId);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return false;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("评优");
            if (function == null) {
                logger.error("未能找到'评优'的Function");
                return false;
            }
            for(String id : taIds)
                if(taDao.changeStatusAvailableForUser(roleIds, function.getId(), id)) {
                    boolean result = taDao.toNextStatus(roleIds, function.getId(), id);
                }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean revocationOutstanding(List<String> taIds, String uId) {
        try {
            if(taIds == null || taIds.size() == 0 || uId == null)
                return false;
            //更改课程申请状态
            //默认工作方法为“评优”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(uId);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return false;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("评优");
            if (function == null) {
                logger.error("未能找到'评优'的Function");
                return false;
            }
            for(String id : taIds)
                if(taDao.changeStatusAvailableForUser(roleIds, function.getId(), id)) {
                    boolean result = taDao.toPreviousStatus(roleIds, function.getId(), id);
                }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<TaInfoViewObject> seachTainfoListByConditions(Map<String, String> conditions){

        return  tamstadao.getTaInfoByConditions(conditions);
    }
}
