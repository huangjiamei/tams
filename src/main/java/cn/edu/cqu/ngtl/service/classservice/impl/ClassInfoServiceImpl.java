package cn.edu.cqu.ngtl.service.classservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_MBR_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSClassApplyStatusDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.impl.TAMSWorkflowFunctionsDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.impl.TamsFileControllerServiceImpl;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.tools.utils.TimeUtil;
import cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    private static final Logger logger = Logger.getRootLogger();

    @Autowired
    private TAMSAttachmentsDao attachmentsDao;

    @Autowired
    private TAMSTaApplicationDao tamsTaApplicationDao;

    @Autowired
    private TAMSTaBlackListDao tamsTaBlackListDao;

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTClassDao classDao;

    @Autowired
    private UTStudentDao studentDao;

    @Autowired
    private UTInstructorDao utInstructorDao;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    @Autowired
    private TAMSTeachCalendarDao teachCalendarDao;

    @Autowired
    private TAMSClassFundingDraftDao tamsClassFundingDraftDao;

    @Autowired
    private TAMSTaDao taDao;

    @Autowired
    private TAMSActivityDao activityDao;

    @Autowired
    private TAMSClassTaApplicationDao classTaApplicationDao;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private TAMSClassEvaluationDao classEvaluationDao;

    @Autowired
    private TAMSClassApplyStatusDao classApplyStatusDao;

    @Autowired
    private TAMSWorkflowFunctionsDao workflowFunctionsDao;

    @Autowired
    private TAMSClassApplyFeedbackDao tamsClassApplyFeedbackDao;

    @Autowired
    private UTStudentTimetableDao utStudentTimetableDao;

    @Autowired
    private TAMSTimeSettingTypeDao tamsTimeSettingTypeDao;

    @Autowired
    private TAMSClassTaApplicationDao tamsClassTaApplicationDao;

    @Autowired
    private TAMSClassFundingDao tamsClassFundingDao;

    @Autowired
    private TAMSWorkflowStatusDao tamsWorkflowStatusDao;

    @Autowired
    private TAMSClassEvaluationDao tamsClassEvaluationDao;

    @Autowired
    private TAMSDeptFundingDao tamsDeptFundingDao;

    @Autowired
    private TAMSUniversityFundingDao tamsUniversityFundingDao;

    @Autowired
    private TAMSDeptFundingDraftDao tamsDeptFundingDraftDao;

    @Override
    public List<UTClassInformation> getAllCurSessionClasses() {

        /** Access DataBase */
        System.out.println(System.currentTimeMillis());
        List<UTClassInformation> classInformations = classInfoDao.getAllCurrentClassInformation();
        System.out.println(System.currentTimeMillis());
        return classInformations;
    }


    @Override
    public List<UTClassInformation> getAllCurSessionClassesWithFinalStatus(String functionId){
        Integer maxOrder = tamsWorkflowStatusDao.getMaxOrderByFunctionId(functionId);
        List<UTClassInformation> classInformations = classInfoDao.getAllCurrentClassInformationBySepStatus(maxOrder.toString());
        return classInformations;
    }

    @Override
    public UTClass getClassInfoById(String classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;
    }

    @Override
    public UTStudent getStudentInfoById(String stuId) {
        return studentDao.getUTStudentById(stuId);
    }


    @Override
    public UTClassInformation getAllClassesFilterByCLassId(String classId){
        return classInfoDao.getOneById(classId);
    }



    @Override
    public List<UTClassInformation> getAllClassesFilterByUid(String uId) {

        if (uId.equalsIgnoreCase("admin")) {
            return this.getAllCurSessionClasses();
        }
        if (userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId))
            return this.getAllCurSessionClasses();

        else if(userInfoService.isCollegeStaff(uId)){ //部门管理员
            UTInstructor utInstructor = utInstructorDao.getInstructorByIdWithoutCache(uId);
            List<UTClassInformation> list = new ArrayList<>();
            list = classInfoDao.getAllCurrentClassInformationByDeptId(utInstructor.getDepartmentId().toString());
            //用于比较
            List<Object> classIds = new ArrayList<>();
            for(UTClassInformation listone : list) {
                classIds.add(listone.getId());
            }
            //如果学院管理员同时是其他学学院课程的教师
            if(userInfoService.isInstructor(uId)) {
                List<UTClassInformation> listteacher = classInfoDao.selectBatchByIds(
                        classInstructorDao.selectClassIdsByInstructorId(uId)
                );
                if(listteacher != null) {
                    //用于比较
                    List<Object> classIdTeacher = new ArrayList<>();
                    for(UTClassInformation listtechaerone : listteacher) {
                        classIdTeacher.add(listtechaerone.getId());
                    }
                    for(Object per: classIdTeacher) {
                        if(!classIds.contains(per))
                            list.add(classInfoDao.getOneById(per.toString()));
                    }
                }
            }
            return list;
        }
        else if (userInfoService.isCourseManager(uId)) {  //默认是课程负责人一定是教师
            //担任课程负责人的课程
            List<Object> classIds = classInstructorDao.selectCourseManagerClassIdsByInstructorId(uId);
            //自己的课程
            List<Object> ownClassIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            for (Object classId : ownClassIds) {
                if (!classIds.contains(classId)) {
                    classIds.add(classId);
                }
            }
            return classInfoDao.selectBatchByIds(classIds);
        } else if (userInfoService.isInstructor(uId)) {  //如果是教师
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

            return classInfoDao.selectBatchByIds(classIds);
        } else if (userInfoService.isStudent(uId)) {  //如果是学生
            TAMSTimeSettingType timeSettingType = tamsTimeSettingTypeDao.selectByName("学生申请助教");
            if (timeSettingType == null) {
                return null;
            }
            TimeUtil timeUtil = new TimeUtil();
            if (timeUtil.isBetweenPeriod(timeSettingType.getId(), sessionDao.getCurrentSession().getId().toString())) {
                return this.getAllCurSessionClassesWithFinalStatus("1"); //工作流是审批
            }
            List<Object> classIds = taDao.selectClassIdsByStudentId(uId);
            List<UTStudentTimetable> utStudentTimetables = utStudentTimetableDao.getStudentTimetableByUid(uId);
            if (utStudentTimetables != null && utStudentTimetables.size() != 0) {
                for (UTStudentTimetable utStudentTimetable : utStudentTimetables) {
                    classIds.add(utStudentTimetable.getClassId());
                }
            }
            return classInfoDao.selectBatchByIds(classIds);
        }
        return null;
    }

    @Override
    public List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions) {
        if (userInfoService.isSysAdmin(uId) || userInfoService.isAcademicAffairsStaff(uId)) {
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            return classInformations;
        }
        else if (userInfoService.isCollegeStaff(uId)) { //如果是二级单位管理员则固定学院id
            if(conditions.get("DepartmentId") == null) {
                //若只是二级单位管理员，设置departmenId
                conditions.put("DepartmentId", ((User) GlobalVariables.getUserSession().retrieveObject("user")).getDepartmentId().toString());
                List<UTClassInformation> classInformations = new ArrayList<>();
                classInformations = classInfoDao.selectByConditions(conditions);
                //用于比较
                List<Object> classIds = new ArrayList<>();
                for(UTClassInformation listone : classInformations) {
                    classIds.add(listone.getId());
                }
                //同时是其他学院课程的教师，固定教师Id
                if(userInfoService.isInstructor(uId)) {
                    if(conditions.get("InstructorName") == null) {
                        conditions.put("DepartmentId", null);
                        conditions.put("InstructorName", ((User) GlobalVariables.getUserSession().retrieveObject("user")).getName());
                        List<UTClassInformation> listteacher = classInfoDao.selectByConditions(conditions);
                        if (listteacher != null) {
                            //用于比较
                            List<Object> classIdTeacher = new ArrayList<>();
                            for (UTClassInformation listtechaerone : listteacher) {
                                classIdTeacher.add(listtechaerone.getId());
                            }
                            for (Object per : classIdTeacher) {
                                if (!classIds.contains(per))
                                    classInformations.add(classInfoDao.getOneById(per.toString()));
                            }
                        }
                    }
                    else {
                        //FIXME
                        //问题：需要判断过滤的教师的名字是否是自己，不能判断
                        conditions.get("InstructorName").replace("%","");
                        if(((User) GlobalVariables.getUserSession().retrieveObject("user")).getName().equals(conditions.get("InstructorName")))
                            conditions.put("DepartmentId", null);
                        else
                            conditions.put("DepartmentId", ((User) GlobalVariables.getUserSession().retrieveObject("user")).getDepartmentId().toString());

                        List<UTClassInformation> listteacher = classInfoDao.selectByConditions(conditions);
                        if (listteacher != null) {
                            //用于比较
                            List<Object> classIdTeacher = new ArrayList<>();
                            for (UTClassInformation listtechaerone : listteacher) {
                                classIdTeacher.add(listtechaerone.getId());
                            }
                            for (Object per : classIdTeacher) {
                                if (!classIds.contains(per))
                                    classInformations.add(classInfoDao.getOneById(per.toString()));
                            }
                        }
                    }
                }
                return classInformations;
            }
            else {
                conditions.put("DepartmentId", ((User) GlobalVariables.getUserSession().retrieveObject("user")).getDepartmentId().toString());
                List<UTClassInformation> classInformations = new ArrayList<>();
                classInformations = classInfoDao.selectByConditions(conditions);
                return classInformations;
            }
        } else if (userInfoService.isCourseManager(uId)) {
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            List<Object> classIds = classInstructorDao.selectCourseManagerClassIdsByInstructorId(uId);
            //自己的课程
            List<Object> ownClassIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            for (Object classId : ownClassIds) {
                if (!classIds.contains(classId)) {
                    classIds.add(classId);
                }
            }
            List<String> strClassIds = new ArrayList<>();
            for(Object classid:classIds){
                strClassIds.add(classid.toString());
            }
            /*
                在结果中只显示负责人能看的课程
             */
            List<UTClassInformation> result = new ArrayList<>();
            for (UTClassInformation utClassInformation : classInformations) {
                if (strClassIds.contains(utClassInformation.getId()))
                    result.add(utClassInformation);
            }
            return result;
        } else if (userInfoService.isInstructor(uId)) {
            conditions.put("InstructorName", ((User) GlobalVariables.getUserSession().retrieveObject("user")).getName());
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            return classInformations;
        }else if (userInfoService.isStudent(uId)){
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            List<UTClassInformation> result = new ArrayList<>();
            TAMSTimeSettingType timeSettingType = tamsTimeSettingTypeDao.selectByName("学生申请助教");
            if (timeSettingType == null) {
                return null;
            }
            TimeUtil timeUtil = new TimeUtil();
            //整理学生能看到的classID;
            List<String> stuCanSeeClassId = new ArrayList<>();
            if (timeUtil.isBetweenPeriod(timeSettingType.getId(), sessionDao.getCurrentSession().getId().toString())) {
                List<UTClassInformation> classInformationList = this.getAllCurSessionClassesWithFinalStatus("1"); //在选课时间可以看到的课程
                for(UTClassInformation utClassInformation:classInformationList){
                    stuCanSeeClassId.add(utClassInformation.getId());
                }
            }else{
                List<Object> classIds = taDao.selectClassIdsByStudentId(uId);
                for(Object classid :classIds){
                    stuCanSeeClassId.add(classid.toString());
                }
            }
            //将不能看到的课程过滤掉
            for(UTClassInformation utClassInformation:classInformations){
                if(stuCanSeeClassId.contains(utClassInformation.getId())){
                    result.add(utClassInformation);
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId) {
        if (userInfoService.isSysAdmin(uId)) {
            List<TAMSTeachCalendar> teachCalendar = teachCalendarDao.selectAllByClassId(classId);
            return teachCalendar;
        } else if (userInfoService.isInstructor(uId)) {
            return teachCalendarDao.selectAllByClassId(classId);
        }
        return null;
    }

    @Override
    public TAMSTeachCalendar instructorAddTeachCalendar(String uId, String classId, TAMSTeachCalendar teachCalendar) {
        if (!userInfoService.isInstructor(uId)) {
            return null;
        } else if (userInfoService.isInstructor(uId)) {
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            Set<String> classIdStrings = new HashSet<>();
            for (Object obj : classIds)
                classIdStrings.add(obj.toString());
            if (classIdStrings.contains(classId) || userInfoService.isSysAdmin(uId)) {
                teachCalendar.setClassId(classId);
                return teachCalendarDao.insertByEntity(teachCalendar);
            } else
                return null;
        }
        return null;
    }

    @Override
    public boolean removeTeachCalenderById(String uId, String classId, String teachCalendarId) {
        //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
        if (userInfoService.isSysAdmin(uId)) {
            TAMSTeachCalendar teachCalendar = teachCalendarDao.selectById(teachCalendarId);
            return teachCalendarDao.deleteByEntity(teachCalendar);

        } else if (userInfoService.isInstructor(uId)) { //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            Set<String> classIdStrings = new HashSet<>();
            for (Object obj : classIds)
                classIdStrings.add(obj.toString());
            if (classIdStrings.contains(classId)) {
                TAMSTeachCalendar teachCalendar = teachCalendarDao.selectById(teachCalendarId);
                return teachCalendarDao.deleteByEntity(teachCalendar);
            } else
                return false;
        }
        return false;
    }

    @Override
    public List<TAMSTeachCalendar> getAllTaTeachActivityAsCalendarFilterByUidAndClassId(String uId, String classId) {
        if (userInfoService.isSysAdmin(uId) && !userInfoService.isInstructor(uId)) {//// FIXME: 16-11-18 无区别 ask for 唐靖
            List<TAMSTeachCalendar> calendars = teachCalendarDao.selectAllByClassId(classId);
            for (TAMSTeachCalendar calendar : calendars) {
                List<TAMSActivity> activities = activityDao.selectAllByCalendarId(calendar.getId());
                calendar.setActivityList(activities);
            }
            return calendars;
        } else if (userInfoService.isInstructor(uId)) {
            List<TAMSTeachCalendar> calendars = teachCalendarDao.selectAllByClassId(classId);
            for (TAMSTeachCalendar calendar : calendars) {
                List<TAMSActivity> activities = activityDao.selectAllByClassId(classId);
                if (calendar.getActivityList() != null) {
                    calendar.getActivityList().addAll(activities);
                } else {
                    calendar.setActivityList(activities);
                }
            }
            return calendars;
        }
        return null;
    }

    @Override
    public short instructorAddClassTaApply(String instructorId, String classId, String assistantNumber, List<TAMSClassEvaluation> classEvaluations, String totalTime, String totalBudget) {
        TAMSTimeSettingType timeSettingType = tamsTimeSettingTypeDao.selectByName("教师申请助教");
        TimeUtil timeUtil = new TimeUtil();
        if (timeSettingType == null) {
            return 10;
        }
        if (!timeUtil.isBetweenPeriod(timeSettingType.getId(), sessionDao.getCurrentSession().getId().toString())) {
            return 1;
        }
        TAMSClassTaApplication isExist = classTaApplicationDao.selectByInstructorIdAndClassId(instructorId, classId);
        if (isExist != null) {
            logger.warn("已存在数据！");
        } else {
            TAMSClassTaApplication entity = new TAMSClassTaApplication();
            //添加申请信息
            //预处理数据
            entity.setWorkHour(totalTime);
            entity.setApplicationFunds(totalBudget.replace("元", ""));
            entity.setApplicantId(instructorId);
            entity.setApplicationClassId(classId);
            entity.setApplicationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            entity.setTaNumber(Integer.parseInt(assistantNumber));
            entity.setSessionId(sessionDao.getCurrentSession().getId());
            entity = classTaApplicationDao.insertOneByEntity(entity);
            if (entity.getId() == null)
                return 3;
        }
        try {
            //添加课程考核信息
            List<TAMSClassEvaluation> currentEvaluation = getClassEvaluationByClassId(classId);
            if(currentEvaluation!=null){
                for(TAMSClassEvaluation tamsClassEvaluation:currentEvaluation){
                    classEvaluationDao.deleteByEntity(tamsClassEvaluation);
                }
            }
//            classEvaluationDao.deleteAllByClassId(classId);
            boolean flag;
            for (TAMSClassEvaluation classEvaluation : classEvaluations) {
                classEvaluation.setClassId(classId);
                flag = classEvaluationDao.insertOneByEntity(classEvaluation);
                if (!flag) {
//                    classEvaluationDao.deleteAllByClassId(classId);
                    return 4;
                }
            }
            //更改课程申请状态
            //教师默认工作方法为“审核”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(instructorId);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return 5;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("审核");
            if (function == null) {
                logger.error("未能找到'审核'的Function");
                return 6;
            }
            if (classApplyStatusDao.isInitializedStatus(function.getId(), classId)) {
                classApplyStatusDao.toNextStatus(roleIds, function.getId(), classId);
                if (isExist != null)
                    if(!classInfoDao.getOneById(isExist.getApplicationClassId()).getStatus().equals("1"))
                        return 2;
                //执行到这里既是成功
                //如果新提交的课程的状态并非初始状态，则返回true，表示已经通过了提交
                return 7;
            } else {
                return 8;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 9;
    }


    @Override
    public boolean deleteTaApplicationByStuIdAndClassId(String stuId, String classId) {
        TAMSTa tamsta = taDao.selectByStudentIdAndClassId(stuId, classId);
        if (tamsta != null) {
            taDao.deleteOneByEntity(tamsta);
            return true;
        }
        return false;
    }


    @Override
    public List<TAMSTa> getAllTaFilteredByClassid(String classId) {

        return taDao.selectByClassId(classId);

    }

    @Override
    public List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId) {

        return tamsTaApplicationDao.selectByClassId(classId);
    }

    @Override
    public boolean removeCalendarFileById(String classId, String attachmentId) {
        if (attachmentId == null)
            return false;
        TAMSAttachments isExist = attachmentsDao.selectById(attachmentId);
        if (isExist == null)
            return false;
        String uId = GlobalVariables.getUserSession().getPrincipalId();
        if (uId.equals(isExist.getAuthorId()) || userInfoService.isInstructor(uId))
            return new TamsFileControllerServiceImpl().deleteOneAttachment(classId, isExist);
        else
            return false;
    }

    @Override
    public boolean removeAllCalendarFilesByClassIdAndCalendarId(String classId, String calendarId) {
        List<TAMSAttachments> attachments = attachmentsDao.selectCalendarFilesByCalendarId(calendarId);
        boolean flag;
        for (TAMSAttachments attachment : attachments) {
            flag = new TamsFileControllerServiceImpl().deleteOneAttachment(classId, attachment);
        }
        return true;
    }

    @Override
    public boolean approveToNextStatus(List<String> classIds, String uid) {
        try {
            //更改课程申请状态
            //默认工作方法为“审批”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(uid);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return false;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("审核");
            if (function == null) {
                logger.error("未能找到'审核'的Function");
                return false;
            }
            for (String classId : classIds)
                if (!classApplyStatusDao.isInitializedStatus(function.getId(), classId)) {
                    boolean result = classApplyStatusDao.toNextStatus(roleIds, function.getId(), classId);
                }
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rejectToPreviousStatus(List<String> classIds, String uid) {
        try {
            //更改课程申请状态
            //默认工作方法为“审批”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(uid);
            if (roles == null || roles.size() == 0) {
                logger.error("未能找到用户所属角色！");
                return false;
            }
            String[] roleIds = new String[roles.size()];
            for (int i = 0; i < roleIds.length; i++) {
                roleIds[i] = roles.get(i).getRoleId();
            }
            TAMSWorkflowFunctions function = workflowFunctionsDao.selectOneByName("审核");
            if (function == null) {
                logger.error("未能找到'审核'的Function");
                return false;
            }
            for (String classId : classIds)
                if (!classApplyStatusDao.isInitializedStatus(function.getId(), classId)) {
                    boolean result = classApplyStatusDao.toPreviousStatus(roleIds, function.getId(), classId);
                }
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TAMSWorkflowStatus> classStatusAvailable(String uid, String classId) {
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
            TAMSWorkflowFunctions function = new TAMSWorkflowFunctionsDaoJpa().selectOneByName("审核");
            if (function == null) {
                logger.error("未能找到'审核'的Function");
                return null;
            }
            List<TAMSWorkflowStatus> result = new TAMSClassApplyStatusDaoJpa().getAvailableStatus(roleIds, function.getId(), classId);
            Collections.sort(result);
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean classStatusToCertainStatus(String uid, String classId, String workflowStatusId) {
        if (uid == null)
            return false;
        List<TAMSWorkflowStatus> availableStatus = this.classStatusAvailable(uid, classId);
        boolean flag = false;
        for (TAMSWorkflowStatus status : availableStatus) {
            if (status.getId().equals(workflowStatusId))
                flag = true;
        }
        if (!flag)  //此用户并不拥有改变为此状态的权力
            return false;

        return classApplyStatusDao.changeStatusToCertainStatus(classId, workflowStatusId);
    }

    @Override
    public boolean insertFeedBack(String classId, String uId, String reasons, String oldStatus, String newStatusId) {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TAMSClassApplyFeedback tamsClassApplyFeedback = new TAMSClassApplyFeedback();
        tamsClassApplyFeedback.setClassId(classId);
        tamsClassApplyFeedback.setFeedbackUid(uId);
        tamsClassApplyFeedback.setFeedback(reasons);
        tamsClassApplyFeedback.setNewStatus(tamsWorkflowStatusDao.getOneById(newStatusId).getWorkflowStatus());
        tamsClassApplyFeedback.setOldStatus(oldStatus);
        tamsClassApplyFeedback.setFeedbackTime(df1.format(new Date()));
        return tamsClassApplyFeedbackDao.saveFbByEntity(tamsClassApplyFeedback);
    }

    @Override
    public List<TAMSClassApplyFeedback> getFeedBackByClassId(String classId) {
        return tamsClassApplyFeedbackDao.getFbByClassId(classId);
    }

    @Override
    public boolean isInBlackList(String stuId){
        List<String> blackManList = new ArrayList<>();
        List<TAMSTaBlackList> allBlackMen = tamsTaBlackListDao.getAllBlackList(); //被加入黑名单的无法被查到
        if(allBlackMen!=null&&allBlackMen.size()!=0){
            for(TAMSTaBlackList tamsTaBlackList:allBlackMen) {
                blackManList.add(tamsTaBlackList.getTaId());
            }
        }else{
            blackManList.add("");
        }

        if(blackManList.contains(stuId)){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public void validClassFunds(String classId) {  //初始化课程经费
        UTSession curSession = sessionDao.getCurrentSession();
        //草稿课程经费表
        TAMSClassFundingDraft tamsClassFundingDraftExist = tamsClassFundingDraftDao.selectOneByClassID(classId);
        TAMSClassTaApplication tamsClassTaApplication = tamsClassTaApplicationDao.selectByClassId(classId);
        if (tamsClassFundingDraftExist == null) {
            TAMSClassFundingDraft tamsClassFundingDraft = new TAMSClassFundingDraft();
            tamsClassFundingDraft.setClassId(classId);
            tamsClassFundingDraft.setApplyFunding(tamsClassTaApplication.getApplicationFunds());  //将申请经费设置到初始化的课程经费中



            tamsClassFundingDraft.setAssignedFunding("0");
            tamsClassFundingDraft.setPhdFunding("0");
            tamsClassFundingDraft.setBonus("0");
            tamsClassFundingDraft.setTravelSubsidy("0");
            tamsClassFundingDraft.setSessionId(sessionDao.getCurrentSession().getId().toString());
            tamsClassFundingDraftDao.insertOneByEntity(tamsClassFundingDraft);
        } else {
            tamsClassFundingDraftExist.setApplyFunding(tamsClassTaApplication.getApplicationFunds());
            tamsClassFundingDraftDao.insertOneByEntity(tamsClassFundingDraftExist);
        }

        //课程经费表
        TAMSClassFunding tamsClassFundingExist = tamsClassFundingDao.getOneByClassId(classId);
        if (tamsClassFundingExist == null) {
            TAMSClassFunding tamsClassFunding = new TAMSClassFunding();
            tamsClassFunding.setClassId(classId);
            tamsClassFunding.setApplyFunding(tamsClassTaApplication.getApplicationFunds());  //将申请经费设置到初始化的课程经费中



            tamsClassFunding.setAssignedFunding("0");
            tamsClassFunding.setPhdFunding("0");
            tamsClassFunding.setBonus("0");
            tamsClassFunding.setTravelSubsidy("0");
            tamsClassFunding.setSessionId(sessionDao.getCurrentSession().getId().toString());
            tamsClassFundingDao.saveOneByEntity(tamsClassFunding);
        }else{
            tamsClassFundingExist.setApplyFunding(tamsClassTaApplication.getApplicationFunds());
            tamsClassFundingDao.saveOneByEntity(tamsClassFundingExist);
        }


        UTClassInformation currentClassInfo = classInfoDao.getOneById(classId);
        //更新学院草稿经费
        TAMSDeptFundingDraft tamsDeptFundingDraft = tamsDeptFundingDraftDao.selectDeptDraftFundsByDeptIdAndSession(
                currentClassInfo.getDepartmentId() , curSession.getId()
        );
        if(tamsDeptFundingDraft != null) {
            Integer sumApplyDeptDraft = Integer.parseInt(tamsDeptFundingDraft.getApplyFunding());
            sumApplyDeptDraft = sumApplyDeptDraft + Integer.parseInt(tamsClassTaApplication.getApplicationFunds().indexOf(".")>=0?tamsClassTaApplication.getApplicationFunds().substring(0,tamsClassTaApplication.getApplicationFunds().indexOf(".")):tamsClassTaApplication.getApplicationFunds());
            tamsDeptFundingDraft.setApplyFunding(sumApplyDeptDraft.toString());
            tamsDeptFundingDraftDao.saveOneByEntity(tamsDeptFundingDraft);
        }

        //更新学院的申请经费
        TAMSDeptFunding tamsDeptFunding = tamsDeptFundingDao.selectDeptFundsByDeptIdAndSession(
                currentClassInfo.getDepartmentId(), curSession.getId()
        );
        if(tamsDeptFunding != null) {
            Integer sumApplyDept = Integer.parseInt(tamsDeptFunding.getApplyFunding());
            sumApplyDept = sumApplyDept + Integer.parseInt(tamsClassTaApplication.getApplicationFunds().indexOf(".")>=0?tamsClassTaApplication.getApplicationFunds().substring(0,tamsClassTaApplication.getApplicationFunds().indexOf(".")):tamsClassTaApplication.getApplicationFunds());
            tamsDeptFunding.setApplyFunding(sumApplyDept.toString());
            tamsDeptFundingDao.saveOneByEntity(tamsDeptFunding);
        }

        //更新批次的申请经费
        TAMSUniversityFunding tamsUniversityFunding = tamsUniversityFundingDao.getOneBySessionId(curSession.getId());
        if(tamsUniversityFunding != null) {
            Integer sumApplyUni = Integer.parseInt(tamsUniversityFunding.getApplyFunding());
            sumApplyUni = sumApplyUni + Integer.parseInt(tamsClassTaApplication.getApplicationFunds().indexOf(".")>=0?tamsClassTaApplication.getApplicationFunds().substring(0,tamsClassTaApplication.getApplicationFunds().indexOf(".")):tamsClassTaApplication.getApplicationFunds());
            tamsUniversityFunding.setApplyFunding(sumApplyUni.toString());
            tamsUniversityFundingDao.insertOneByEntity(tamsUniversityFunding);
        }

    }

    @Override
    public TAMSClassTaApplication getClassApplicationByClassId(String classId) {
        return classTaApplicationDao.selectByClassId(classId);
    }


    @Override
    public List<TAMSClassEvaluation> getClassEvaluationByClassId(String classId){
        List<TAMSClassEvaluation> result = tamsClassEvaluationDao.getAllByClassId(classId);
        List<TAMSClassEvaluation> copy = new ArrayList<>();
        if(result!=null) {
            for (TAMSClassEvaluation tamsClassEvaluation : result) {
                TAMSClassEvaluation copyEntity = new TAMSClassEvaluation();
                copyEntity.setEvaluationPercent(tamsClassEvaluation.getEvaluationPercent());
                copyEntity.setEvaluationType(tamsClassEvaluation.getEvaluationType());
                copy.add(copyEntity);
            }
            return copy;
        }
        return result;
    }

    @Override
    public boolean canEmployByClassId(String classId){
        String maxOrder = tamsWorkflowStatusDao.getMaxOrderByFunctionId("1").toString();
        UTClassInformation utClassInformation = classInfoDao.getOneById(classId);
        if(utClassInformation==null){
            return false;
        }
        if(utClassInformation.getStatus().equals(maxOrder)){  //最终状态才可以聘用
            return true;
        }
        return false;
    }


    @Override
    public void releaseTaApplication(List<MyTaViewObject> taViewObjects){
        if(taViewObjects.size()==0){
            return;
        }

        //删除其他的申请
        for(MyTaViewObject myTaViewObject:taViewObjects){
            tamsTaApplicationDao.deleteBystuIdAndClassId(myTaViewObject.getTaIdNumber(),myTaViewObject.getApplicationClassId());
        }
    }

    @Override
    public Integer applyOutStanding(String applyOTReason, String StuId, String classId) {
        UTSession curSession = sessionDao.getCurrentSession();
        TAMSTa ta = taDao.selectByStudentIdAndClassIdAndSessionId(StuId, classId, curSession.getId().toString());
        if(ta.getOutStandingTaWorkflowStatusId().equals("6") || ta.getOsNote().equals(null)){
            ta.setOutStandingTaWorkflowStatusId("7");
            ta.setOsNote(applyOTReason);
            taDao.insertByEntity(ta);
            return 1;
        }
        else
            return 2;
    }

}
