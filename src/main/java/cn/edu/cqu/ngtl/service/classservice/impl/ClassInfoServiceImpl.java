package cn.edu.cqu.ngtl.service.classservice.impl;

import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_MBR_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    private static final Logger logger = Logger.getRootLogger();


    @Autowired
    private TAMSTaApplicationDao tamsTaApplicationDao;

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTClassDao classDao;

    @Autowired
    private UTStudentDao studentDao;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    @Autowired
    private TAMSTeachCalendarDao teachCalendarDao;

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

    @Override
    public List<UTClassInformation> getAllClassesMappedByDepartment() {

        /** Access DataBase */
        List<UTClassInformation> classInformations = classInfoDao.getAllCurrentClassInformation();

        return classInformations;
    }

    @Override
    public UTClass getClassInfoById(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;
    }

    @Override
    public UTStudent getStudentInfoById(String stuId) {
        return studentDao.getUTStudentById(stuId);
    }

    @Override
    public List<UTClassInformation> getAllClassesFilterByUid(String uId) {

        //// FIXME: 16-11-4 因为测试加上了非 '!'，正式使用需要去掉
        if(uId.equalsIgnoreCase("admin")){
            return this.getAllClassesMappedByDepartment();   //FIXME 测试代码。需要删除
        }
        if(userInfoService.isSysAdmin(uId) && !userInfoService.isInstructor(uId))
            return this.getAllClassesMappedByDepartment();
        else if (userInfoService.isInstructor(uId)) {
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

            return classInfoDao.selectBatchByIds(classIds);
        }
        else if (userInfoService.isStudent(uId)) {
            List<Object> classIds = taDao.selectClassIdsByStudentId(uId);

            return classInfoDao.selectBatchByIds(classIds);
        }
        return null;
    }

    @Override
    public List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions) {
        if(userInfoService.isSysAdmin(uId)) {
            /** Access DataBase */
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            return classInformations;
        }
        else if (userInfoService.isInstructor(uId)) {
            //FIXME 按照教师ID去查课程信息
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
//            return classInfoDao.selectBatchByIds(classIds);
        }
        return null;
    }

    @Override
    public List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId) {
        if(userInfoService.isSysAdmin(uId)) {//// FIXME: 16-11-18 无区别 ask for 唐靖
            List<TAMSTeachCalendar> teachCalendar = teachCalendarDao.selectAllByClassId(classId);
            return teachCalendar;
        }
        else if (userInfoService.isInstructor(uId)) {
            return teachCalendarDao.selectAllByClassId(classId);
        }
        return null;
    }

    @Override
    public TAMSTeachCalendar instructorAddTeachCalendar(String uId, String classId, TAMSTeachCalendar teachCalendar) {
        //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
        if(userInfoService.isSysAdmin(uId) && !userInfoService.isInstructor(uId)) {
            return null;
        }
        else if (userInfoService.isInstructor(uId)) {
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            Set<String> classIdStrings = new HashSet<>();
            for(Object obj : classIds)
                classIdStrings.add(obj.toString());
            if(classIdStrings.contains(classId)) {
                teachCalendar.setClassId(classId);
                return teachCalendarDao.insertByEntity(teachCalendar);
            }
            else
                return null;
        }
        return null;
    }

    @Override
    public boolean removeTeachCalenderById(String uId, String classId, String teachCalendarId) {
        //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
        if(userInfoService.isSysAdmin(uId) && !userInfoService.isInstructor(uId)) {
            return true;
        }
        else if (userInfoService.isInstructor(uId)) { //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            Set<String> classIdStrings = new HashSet<>();
            for(Object obj : classIds)
                classIdStrings.add(obj.toString());
            if(classIdStrings.contains(classId)) {
                TAMSTeachCalendar teachCalendar = teachCalendarDao.selectById(teachCalendarId);
                return teachCalendarDao.deleteByEntity(teachCalendar);
            }
            else
                return false;
        }
        return false;
    }

    @Override
    public List<TAMSTeachCalendar> getAllTaTeachActivityAsCalendarFilterByUidAndClassId(String uId, String classId) {
        if(userInfoService.isSysAdmin(uId) && !userInfoService.isInstructor(uId)) {//// FIXME: 16-11-18 无区别 ask for 唐靖
            List<TAMSTeachCalendar> calendars = teachCalendarDao.selectAllByClassId(classId);
            for(TAMSTeachCalendar calendar : calendars) {
                List<TAMSActivity> activities = activityDao.selectAllByCalendarId(calendar.getId());
                calendar.setActivityList(activities);
            }
            return calendars;
        }
        else if (userInfoService.isInstructor(uId)) {
            List<TAMSTeachCalendar> calendars = teachCalendarDao.selectAllByClassId(classId);
            for(TAMSTeachCalendar calendar : calendars) {
                List<TAMSActivity> activities = activityDao.selectAllByClassId(classId);
                if(calendar.getActivityList()!=null) {
                    calendar.getActivityList().addAll(activities);
                }else{
                    calendar.setActivityList(activities);
                }
            }
            return calendars;
        }
        return null;
    }

    @Override
    public boolean instructorAddClassTaApply(String instructorId, String classId, String assistantNumber, List<TAMSClassEvaluation> classEvaluations) {
        TAMSClassTaApplication isExist = classTaApplicationDao.selectByInstructorIdAndClassId(instructorId, classId);
        if(isExist != null) {
            logger.warn("已存在数据！");
        }
        else {
            TAMSClassTaApplication entity = new TAMSClassTaApplication();
            //添加申请信息
            //预处理数据
            entity.setApplicantId(instructorId);
            entity.setApplicationClassId(classId);
            entity.setApplicationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            entity.setTaNumber(Integer.parseInt(assistantNumber));
            entity.setSessionId(sessionDao.getCurrentSession().getId());
            entity = classTaApplicationDao.insertOneByEntity(entity);
            if (entity.getId() == null)
                return false;
        }
        try {

            //添加课程考核信息
            classEvaluationDao.deleteAllByClassId(classId);
            boolean flag;
            for (TAMSClassEvaluation classEvaluation : classEvaluations) {
                classEvaluation.setClassId(classId);
                flag = classEvaluationDao.insertOneByEntity(classEvaluation);
                if (!flag) {
                    classEvaluationDao.deleteAllByClassId(classId);
                    return false;
                }
            }
            //更改课程申请状态
            //教师默认工作方法为“审核”
            List<KRIM_ROLE_MBR_T> roles = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(instructorId);
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
            boolean result = classApplyStatusDao.toNextStatus(roleIds, function.getId(), classId);
            //执行到这里既是成功

            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<TAMSTa> getAllTaFilteredByClassid(String classId){

        return taDao.selectByClassId(classId);

    }


    public List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId){

        return tamsTaApplicationDao.selectByClassId(classId);
    }

}
