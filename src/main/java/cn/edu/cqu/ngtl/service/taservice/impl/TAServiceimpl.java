package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_MBR_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSTaDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowFunctionsDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.enums.TA_STATUS;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudentTimetable;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.tools.utils.TimeUtil;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAServiceimpl implements ITAService {

    private static final Logger logger = Logger.getRootLogger();

    private static final Integer MAX_APPLY_COURSE_NUMBER = 2;  //最多同时申请和担任的助教数量（两样之和）

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Autowired
    private UTClassDao classDao;

    @Autowired
    private TAMSTaApplicationDao tamsTaApplicationDao;

    @Autowired
    private TAMSWorkflowStatusDao tamsWorkflowStatusDao;

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

    @Autowired
    private UTStudentTimetableDao utStudentTimetableDao;

    @Autowired
    private TAMSTimeSettingTypeDao tamsTimeSettingTypeDao;


    @Override
    public String getApplicationPhoneNbr(String stuId, String classId){
        TAMSTaApplication tamsTaApplication =  tamsTaApplicationDao.selectByStuIdAndClassId(stuId,classId);
        if(tamsTaApplication!=null) {
            return tamsTaApplication.getPhoneNbr();
        }
        return null;
    }

    @Override
    public String getApplicationReason(String stuId, String classId){
        TAMSTaApplication tamsTaApplication =  tamsTaApplicationDao.selectByStuIdAndClassId(stuId,classId);
        if(tamsTaApplication!=null) {
            return tamsTaApplication.getNote();
        }
        return null;
    }

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

    @Override
    public short submitApplicationAssistant(TAMSTaApplication application) {
        TAMSTimeSettingType timeSettingType = tamsTimeSettingTypeDao.selectByName("学生申请助教");
        TimeUtil timeUtil = new TimeUtil();
        if(!timeUtil.isBetweenPeriod(timeSettingType.getId(), sessionDao.getCurrentSession().getId().toString())) {
            return 1;
        }
        //判断学生申请同一个class的助教的数量
        //若已经申请过该课程，则不能再申请
        if(tamsTaApplicationDao.selectByStuIdAndClassId(application.getApplicationId(),application.getApplicationClassId())!=null){
            return 2;
        }
        //若已被该课程聘用，则不能再聘请
        if(taDao.selectByStudentIdAndClassId(application.getApplicationId(),application.getApplicationClassId())!=null) {
            return 3;
        }
        //判断学生申请助教的数量
        //若已经申请过两门课程的助教，则不能再申请
        if(tamsTaApplicationDao.selectByStuId(application.getApplicationId()) != null ) {
            int countTaApplication = tamsTaApplicationDao.selectByStuId(application.getApplicationId()).size();
            if (countTaApplication == 2)
                return 7;
        }
        //若该学生已经是两门课程的助教，也不能申请
        if(taDao.selectByTaId(application.getApplicationId() )!= null ) {
            if (taDao.selectByTaId(application.getApplicationId()).size() == 2)
                return 8;
        }
        Integer stuApplications = tamsTaApplicationDao.selectByStuId(application.getApplicationId())==null?0:tamsTaApplicationDao.selectByStuId(application.getApplicationId()).size();
        Integer stuTaNumber = taDao.selectByTaId(application.getApplicationId())==null?0:taDao.selectByTaId(application.getApplicationId()).size();
        if(stuApplications+stuTaNumber>MAX_APPLY_COURSE_NUMBER){
            return 6;
        }
        if (tamsTaApplicationDao.insertOne(application))
            return 4;
        else
            return 5;
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


    @Override
    public List<UTStudentTimetable> getStudentTimetableByUid(String uId){

        return utStudentTimetableDao.getStudentTimetableByUid(uId);

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
            //新添默认数据
            newTa.setAssignedFunding("0");
            newTa.setApplicationNote("0");
            newTa.setPhdFunding("0");
            newTa.setTravelSubsidy("0");
            newTa.setBonus("0");
            newTa.setMonth1("0");
            newTa.setMonth2("0");
            newTa.setMonth3("0");
            newTa.setMonth4("0");
            newTa.setMonth5("0");
            newTa.setMonth6("0");
            newTa.setMonth7("0");
            newTa.setMonth8("0");
            newTa.setMonth9("0");
            newTa.setMonth10("0");
            newTa.setMonth11("0");
            newTa.setMonth12("0");
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("评优");
            String workFlowStatus = tamsWorkflowStatusDao.getFirstStatusByFunctionId(function.getId());
            newTa.setOutStandingTaWorkflowStatusId(workFlowStatus==null?"":workFlowStatus);
            //newTa.setType("1");
            UTStudent utStudent = studentDao.getUTStudentById(per.getStuId());
            if(utStudent != null){
                if(utStudent.getProgram() != null){
                    if(utStudent.getProgram().getDuration() == 3){
                        newTa.setType("1");
                    }
                    newTa.setType("2"); //TODO
                }
                else
                    newTa.setType("缺失");
            }
            //初始状态设为已聘请
            newTa.setStatus("1");
            //设置未评价
            newTa.setEvaluation("未评价");
            newTa.setStuEva("未评价");
//            List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(function.getId());
//            if(allStatus != null && !allStatus.isEmpty())
//                newTa.setOutStandingTaWorkflowStatusId(allStatus.get(0).getOrder().toString());
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
        /*
            复制避免unmodifiableList错误
         */
        List<TAMSTaTravelSubsidy>  result =  tamsTaTravelSubsidyDao.getTAMSTaTravelSubsidyByStuIdAndTaId(taId,classId);
        List<TAMSTaTravelSubsidy> copy = new ArrayList<>();
        if(result!=null) {
            for (TAMSTaTravelSubsidy tamsTaTravelSubsidy : result) {
                copy.add(tamsTaTravelSubsidy);
            }
            return copy;
        }
        return null;
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



    public boolean appraiseOutstandingToSpecifiedStatus(List<String> taIds, String uId, String workFlowStatusId){
        Set availableStat = new HashSet();
        if(uId == null)
            return false;
        for(String taid : taIds) {
            List<TAMSWorkflowStatus> availableStatus = this.appriseStatusAvailable(uId, taid);
            for(TAMSWorkflowStatus tamsWorkflowStatus:availableStatus) {
                availableStat.add(tamsWorkflowStatus);
            }
        }
        boolean flag = false;

        Iterator it = availableStat.iterator();
        while(it.hasNext()){
            TAMSWorkflowStatus tamsWorkflowStatus = (TAMSWorkflowStatus)it.next();
            if(tamsWorkflowStatus.getId().equals(workFlowStatusId))
                flag = true;
        }

        if(!flag)  //此用户并不拥有改变为此状态的权力
            return false;

        for(String taid :taIds) {
             tamstadao.changeStatusToSpecifiedStatus(taid, workFlowStatusId);
        }
        return true;
    }

    public List<TAMSWorkflowStatus> appriseStatusAvailable(String uid, String taId){
        try {
            //更改课程申请状态
            //默认工作方法为“审批”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(uid);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return null;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = new TAMSWorkflowFunctionsDaoJpa().selectOneByName("评优");
            if (function == null) {
                logger.error("未能找到'评优'的Function");
                return null;
            }
            List<TAMSWorkflowStatus> result = new TAMSTaDaoJpa().getAvailableStatus(roleIds, function.getId(), taId);
            Collections.sort(result);
            return result;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TaInfoViewObject> seachTainfoListByConditions(Map<String, String> conditions){

        return  tamstadao.getTaInfoByConditions(conditions);
    }

    @Override
    public String getTamsTaIdByStuIdAndClassId(String stuId, String classId){
        TAMSTa tamsTa = tamstadao.selectByStudentIdAndClassId(stuId, classId);
        return tamsTa==null?null:tamsTa.getId();
    }

    @Override
    public void deleteTravelSubsidyByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy){
        tamsTaTravelSubsidyDao.deleteOneByEntity(tamsTaTravelSubsidy);
    }
}
