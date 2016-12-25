package cn.edu.cqu.ngtl.service.adminservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_MBR_T_Dao;
import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_T_Dao;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_PRNCPL_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dao.ut.UTInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.enums.SESSION_ACTIVE;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.tools.utils.TimeUtil;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-25.
 */
@Service
@Component("AdminServiceImpl")
public class AdminServiceImpl implements IAdminService {

    private static final Logger logger = Logger.getRootLogger();
    private static final String COURSE_MANAGER_ROLE_NAME = "课程负责人";
    @Autowired
    private TAMSTimeSettingTypeDao timeSettingTypeDao;
    @Autowired
    private IUserInfoService iUserInfoService;
    @Autowired
    private TAMSDeptFundingDraftDao tamsDeptFundingDraftDao;
    @Autowired
    private UTInstructorDao utInstructorDao;
    @Autowired
    private TAMSDeptFundingDao tamsDeptFundingDao;
    @Autowired
    private TAMSClassFundingDao tamsClassFundingDao;
    @Autowired
    private TAMSClassFundingDraftDao tamsClassFundingDraftDao;
    @Autowired
    private CMCourseClassificationDao courseClassificationDao;
    @Autowired
    private TAMSTaCategoryDao tamsTaCategoryDao;
    @Autowired
    private TAMSIssueTypeDao issueTypeDao;
    @Autowired
    private UTSessionDao sessionDao;
    @Autowired
    private UTDepartmentDao utDepartmentDao;
    @Autowired
    private TAMSDeptFundingDao deptFundingDao;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private TAMSTimeSettingsDao timeSettingsDao;
    @Autowired
    private TAMSWorkflowStatusRDao workflowStatusRDao;
    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;
    @Autowired
    private TAMSWorkflowRoleFunctionDao workflowRoleFunctionDao;
    //课程负责人过滤
    @Autowired
    private TAMSCourseManagerDao tamsCourseManagerDao;
    @Autowired
    private TAMSUniversityFundingDao tamsUniversityFundingDao;
    @Autowired
    private UTCourseDao utCourseDao;
    @Autowired
    private KRIM_ROLE_MBR_T_Dao krim_role_mbr_t_dao;
    @Autowired
    private KRIM_ROLE_T_Dao krim_role_t_dao;
    @Autowired
    private KRIM_PRNCPL_T_DaoJpa krim_prncpl_t_dao;
    @Autowired
    private TAMSTaDao tamsTaDao;

    @Override
    public List<TAMSCourseManager> getCourseManagerByCondition(Map<String, String> conditions) {
        List<TAMSCourseManager> tamsCourseManagers = tamsCourseManagerDao.selectCourseManagerByCondition(conditions);
        return tamsCourseManagers;
    }

    //历史批次经费过滤
    @Override
    public List<TAMSUniversityFunding> getUniFundPreByCondition(Map<String, String> conditions) {
        List<TAMSUniversityFunding> tamsUniversityFundings = tamsUniversityFundingDao.selectUniFundPreByCondition(conditions);
        return tamsUniversityFundings;
    }

    //课程经费过滤
    @Override
    public List<ClassFundingViewObject> getClassFundByCondition(Map<String, String> conditions) {
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        /**如果是教务处管理员或者系统管理员则显示草稿表的内容，在下拉框里显示发布的数据
         */
        if (userInfoService.isAcademicAffairsStaff(user.getCode()) || userInfoService.isSysAdmin(user.getCode())) {
            return tamsClassFundingDraftDao.selectClassFundDraftByCondition(conditions);
        } else {
            return tamsClassFundingDao.selectClassFundByCondition(conditions);
        }

    }
    //助教经费过滤

    @Override
    public List<TaFundingViewObject> getTaFundByCondition(Map<String, String> conditions) {
        List<TaFundingViewObject> taFundingViewObjects = tamsTaDao.selectTaFundByCondition(conditions);
        return taFundingViewObjects;
    }

    //经费明细过滤
    @Override
    public List<DetailFundingViewObject> getDetailFundByCondition(Map<String, String> conditions) {
        return tamsTaDao.selectDetailFundByCondition(conditions);
    }

    @Override
    public List<CMCourseClassification> getAllClassification() {
        List<CMCourseClassification> courseClassifications = courseClassificationDao.selectAll();
        return courseClassifications.size() != 0 ? courseClassifications :null;
    }

    //获取工作流类型
    @Override
    public List<TAMSWorkflowStatus> getWorkFlowCategory() {
        List<TAMSWorkflowStatus> workflowStatuses = workflowStatusDao.selectAll();
        return workflowStatuses.size() != 0 ? workflowStatuses :null;
    }

    //工作流类型过滤
    @Override
    public List<TAMSWorkflowStatus> getWorkFlowCategoryByCondition(String workflowfunction) {
        List<TAMSWorkflowStatus> workflowStatuses = workflowStatusDao.selectWorkFlowByCondition(workflowfunction);
        return workflowStatuses.size() != 0 ? workflowStatuses :null;
    }

    /*
    //添加工作流类型
    @Override
    public boolean addWorkFlowCategory(Map<String, String> conditions) {
        if(workflowStatusDao.insertOne(conditions))
            return true;
        else
            return false;
    }

    //修改工作流类型
    @Override
    public boolean modifyWorkFlowCategory(Map<String, String> conditions, String status, String order) {
        if(workflowStatusDao.modifyOne(conditions,status,order))
            return true;
        else
            return false;
    }
    */

    //保存工作流类型对象
    @Override
    public boolean saveWorkFlowCategory(TAMSWorkflowStatus tamsWorkflowStatus) {
        if (workflowStatusDao.saveOne(tamsWorkflowStatus))
            return true;
        else
            return false;
    }


    //删除工作流类型
    @Override
    public boolean deleteWorkFlowCategory(TAMSWorkflowStatus tamsWorkflowStatus) {
        if (workflowStatusDao.deleteOne(tamsWorkflowStatus))
            return true;
        else
            return false;
    }

    @Override
    public boolean addCourseClassificationOnlyWithName(String name) {

        if (courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
            return false;

        CMCourseClassification courseClassification = new CMCourseClassification();
        courseClassification.setName(name);

        return courseClassificationDao.insertOneByEntity(courseClassification);
    }

    @Override
    public boolean changeCourseClassificationNameById(Integer id, String name) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if (courseClassification == null)
            return false;
        if (courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
            return false;

        courseClassification.setId(id);
        courseClassification.setName(name);

        return courseClassificationDao.updateOneByEntity(courseClassification);
    }

    @Override
    public boolean removeCourseClassificationById(Integer id) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if (courseClassification == null)
            return false;

        return courseClassificationDao.deleteOneByEntity(courseClassification);
    }

    @Override
    public List<TAMSTaCategory> getAllTaCategories() {
        List<TAMSTaCategory> tamsTaCategories = tamsTaCategoryDao.selectAll();
        return tamsTaCategories.size() != 0 ? tamsTaCategories :null;
    }

    @Override
    public boolean addTaCategory(TAMSTaCategory newTaCategory) {
        if (tamsTaCategoryDao.selectOneByName(newTaCategory.getName()) != null)
            //存在同名数据
            return false;

        return tamsTaCategoryDao.insertOneByEntity(newTaCategory);
    }

    @Override
    public boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory) {
        TAMSTaCategory isExist = tamsTaCategoryDao.selectOneByName(tamsTaCategory.getName());
        if (isExist != null && !isExist.getId().equals(tamsTaCategory.getId()))
            //存在同名数据,而且非本数据
            return false;

        return tamsTaCategoryDao.updateOneByEntity(tamsTaCategory);
    }

    @Override
    public boolean removeTaCategoryById(Integer id) {
        TAMSTaCategory tamsTaCategory = tamsTaCategoryDao.selectOneById(id);

        if (tamsTaCategory == null)
            return false;

        return tamsTaCategoryDao.deleteOneByEntity(tamsTaCategory);
    }

    @Override
    public List<TAMSIssueType> getAllIssueTypes() {

        return issueTypeDao.selectAll();

    }

    @Override
    public boolean addTaIssueType(TAMSIssueType issueType) {

        TAMSIssueType isInDataBase = issueTypeDao.selectOneByTypeName(issueType.getTypeName());

        if (isInDataBase != null)
            return false;

        return issueTypeDao.insertOneByEntity(issueType);
    }

    @Override
    public List<TAMSCourseManager> getAllCourseManager() {

        List<TAMSCourseManager> allTamsCourseManagers = tamsCourseManagerDao.getAllCourseManager();

        return allTamsCourseManagers;
    }

    @Override
    public List<UTSession> getAllSessions() {

        return sessionDao.selectAll();

    }

    @Override
    public List<UTSession> getSelectedSessions(String termName, String startTime, String endTime) throws ParseException {
        return sessionDao.selectByCondition(termName, startTime, endTime);
    }

    @Override
    public boolean addSession(UTSession session) {
        UTSession isExist = sessionDao.selectByYearAndTerm(session.getYear(), session.getTerm());

        if (isExist != null)
            return false;

        //新建数据的预处理
        session.setActive(SESSION_ACTIVE.NO);
        //// FIXME: 16-10-27 还需要处理预算

        if (sessionDao.insertOneByEntity(session)) {
            //批次经费初始化
            TAMSUniversityFunding tamsUniversityFunding = new TAMSUniversityFunding();
            Integer sessionId = sessionDao.selectSessionByCondition(session.getTerm(), session.getYear());
            tamsUniversityFunding.setSessionId(sessionId);
            tamsUniversityFunding.setUniversityId("1");
            tamsUniversityFunding.setPlanFunding("0");
            tamsUniversityFunding.setActualFunding("0");
            tamsUniversityFunding.setApplyFunding("0");
            tamsUniversityFunding.setPhdFunding("0");
            tamsUniversityFunding.setBonus("0");
            tamsUniversityFunding.setTravelSubsidy("0");
            tamsUniversityFundingDao.insertOneByEntity(tamsUniversityFunding);
            //学院经费初始化
            List<TAMSDeptFundingDraft> tamsDeptFundingDrafts = new ArrayList<>();
            List<UTDepartment> departments = utDepartmentDao.getAllHasCourseDepartment(); //只初始化有课程的学院
            for (int i = 0; i < departments.size(); i++) {
                TAMSDeptFundingDraft tamsDeptFundingDraft = new TAMSDeptFundingDraft();
                tamsDeptFundingDraft.setSessionId(sessionId);
                tamsDeptFundingDraft.setDepartmentId(departments.get(i).getId());
                tamsDeptFundingDraft.setPlanFunding("0");
                tamsDeptFundingDraft.setActualFunding("0");
                tamsDeptFundingDraft.setApplyFunding("0");
                tamsDeptFundingDraft.setPhdFunding("0");
                tamsDeptFundingDraft.setBonus("0");
                tamsDeptFundingDraft.setTravelSubsidy("0");
                tamsDeptFundingDrafts.add(tamsDeptFundingDraft);
            }
            tamsDeptFundingDraftDao.saveBatchByEntities(tamsDeptFundingDrafts);
            return true;
        } else
            return false;
    }


    @Override
    public boolean changeIssueType(TAMSIssueType issueType) {
        TAMSIssueType isExist = issueTypeDao.selectOneByTypeName(issueType.getTypeName());
        if (isExist != null && !isExist.getId().equals(issueType.getId()))
            //存在同名数据,而且非本数据
            return false;

        return issueTypeDao.updateOneByEntity(issueType);
    }

    @Override
    public boolean removeIssueTypeById(String id) {
        TAMSIssueType issueType = issueTypeDao.selectOneById(id);

        if (issueType == null)
            return false;

        return issueTypeDao.deleteOneByEntity(issueType);
    }

    @Override
    public boolean changeSession(UTSession session) {
        return sessionDao.updateOneByEntity(session);
    }

    @Override
    public boolean removeTermByYearAndTerm(String termYear, String termTerm) {
        UTSession session = sessionDao.selectByYearAndTerm(termYear, termTerm);

        if (session == null)
            return false;

        return sessionDao.deleteOneByEntity(session);
    }


    @Override
    public boolean setCurrentSession(String termYear, String termTerm) {

        UTSession session = sessionDao.selectByYearAndTerm(termYear, termTerm);
        if (session.getActive().equals("Y")) {
            return true;
        } else {
            UTSession utSession = sessionDao.getCurrentSession();
            utSession.setActive("N");
            sessionDao.updateOneByEntity(utSession);
            session.setActive("Y");
            return sessionDao.updateOneByEntity(session);

        }
    }

    @Override
    public List<UTInstructor> getInstructorByconditions(Map<String, String> conditions) {
        return utInstructorDao.getInstructorByConditions(conditions);
    }


    /*
    @Override
    public List<TAMSDeptFunding> getCurrFundingBySession() {

        return deptFundingDao.selectCurrBySession();

    }

    @Override
    public List<TAMSDeptFunding> getPreviousFundingBySession() {

        return deptFundingDao.selectPreBySession();

    }
    */

    //获取学校经费
    @Override
    public List<TAMSUniversityFunding> getCurrFundingBySession() {

        return tamsUniversityFundingDao.selectCurrBySession();

    }

    @Override
    public List<TAMSUniversityFunding> getPreviousFundingBySession() {

        return tamsUniversityFundingDao.selectPreBySession();

    }

    //获取学院当前经费
    @Override
    public List<TAMSDeptFunding> getDepartmentCurrFundingBySession() {

        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示草稿表的内容，在下拉框里显示发布的数据
         */
        if (userInfoService.isAcademicAffairsStaff(user.getCode()) || userInfoService.isSysAdmin(user.getCode())) {
            return tamsDeptFundingDraftDao.selectDepartmentCurrDraftBySession();
        } else
            return deptFundingDao.selectDepartmentCurrBySession();
    }

    //学院当前经费过滤
    public List<TAMSDeptFunding> getDeptFundCurrByCondition(Map<String, String> conditions) {
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示过滤后草稿表的内容，在下拉框里显示发布的数据
         */
        if (userInfoService.isAcademicAffairsStaff(user.getCode()) || userInfoService.isSysAdmin(user.getCode())) {
            List<TAMSDeptFunding> list = tamsDeptFundingDraftDao.selectDeptFundDraftCurrByCondition(conditions);
            return list;
        } else {
            return tamsDeptFundingDao.selectDeptFundCurrByCondition(conditions);
        }
    }

    //获取学院历史经费
    @Override
    public List<TAMSDeptFunding> getDepartmentPreFundingBySession() {

        return deptFundingDao.selectDepartmentPreBySession();
    }

    @Override
    public List<TAMSDeptFunding> getDepartmentPreFundingByCondition(Map<String, String> conditions) {
        /*
        if (!userInfoService.isSysAdmin(uId)){
            List<TAMSDeptFunding> deptFundings = deptFundingDao.selectDeptFundPreByCondition(conditions);
            return deptFundings;
        }
        return null;
        */
        return deptFundingDao.selectDeptFundPreByCondition(conditions);
    }

    @Override
    public List<TAMSWorkflowStatusR> getWorkflowStatusRelationByRoleFunctionId(String roleFunctionId) {
        if (roleFunctionId == null)
            return null;

        return workflowStatusRDao.selectByRFId(roleFunctionId);

    }

    @Override
    public String getRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId) {
        if (roleId == null || functionId == null)
            return null;

        return workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
    }

    @Override
    public String setRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId) {
        //如果找得到RFId就找找不到就创建新的
        String roleFunctionId = this.getRoleFunctionIdByRoleIdAndFunctionId(roleId, functionId);

        if (roleFunctionId == null) {
            TAMSWorkflowRoleFunction workflowRoleFunction = new TAMSWorkflowRoleFunction();
            workflowRoleFunction.setRoleId(roleId);
            workflowRoleFunction.setWorkflowFunctionId(functionId);

            //新增roleFunctionPair
            workflowRoleFunction = workflowRoleFunctionDao.insertByEntity(workflowRoleFunction);

            roleFunctionId = workflowRoleFunction.getId();
        }

        return roleFunctionId;
    }

    @Override
    public void setWorkflowStatusRelationByRoleFunctionId(String functionId, String rfId, RelationTable rt) {
        //将原有的RFId的值删除，再加入新的值
        workflowStatusRDao.deleteTAMSWorkflowStatusRByRFId(rfId);

        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

        int length = allStatus.size();
        CheckBoxStatus[][] matrix = rt.getData();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j].isChecked()) {
                    TAMSWorkflowStatusR dataWorkflowStatusR = new TAMSWorkflowStatusR();
                    dataWorkflowStatusR.setStatusId1(allStatus.get(i).getId());
                    dataWorkflowStatusR.setStatusId2(allStatus.get(j).getId());
                    dataWorkflowStatusR.setRoleFunctionId(rfId);

                    workflowStatusRDao.saveTAMSWorkflowStatusR(dataWorkflowStatusR);
                }
            }
        }
    }


    @Override
    public List<TAMSClassFunding> getFundingByClass() {


        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示草稿表的内容，在下拉框里显示发布的数据
         */
        if (userInfoService.isAcademicAffairsStaff(user.getCode()) || userInfoService.isSysAdmin(user.getCode()) || userInfoService.isCollegeStaff(user.getCode())) {
            return tamsClassFundingDraftDao.selectAll();
        }
        //如果是老师，待定
        else {
            return tamsClassFundingDao.selectAll(user);
        }
    }

    @Override
    public boolean addTimeSetting(User user, String typeId, String startTime, String endTime) {
        if (!userInfoService.hasPermission(user, "ViewConsolePage")) {
            logger.warn("未授权用户请求：(新增时间段)addTimeSetting(User user, String typeId, String startTime, String endTime)\n");
            logger.warn("未授权用户信息："+user.toString()+"\n");
            return false;
        }
        TAMSTimeSettings isExist = timeSettingsDao.selectByTypeId(typeId);
        if (isExist != null) {
            logger.warn("管理员请求新增时间段失败："+user.toString()+"\n");
            logger.warn("本学期对应类型的时间段已设置\n");
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                isExist.setStartTime(
                        output.format(
                                input.parse(startTime)
                        )
                );
                isExist.setEndTime(
                        output.format(
                                input.parse(endTime)
                        )
                );
            } catch (ParseException e) {
                logger.error("输入日期格式不正确！");
                return false;
            }

            isExist.setEditTime(output.format(new Date()));
            return timeSettingsDao.updateOneByEntity(isExist);


        }
        TAMSTimeSettings timeSetting = new TAMSTimeSettings();
        timeSetting.setTimeSettingTypeId(typeId);
        timeSetting.setSessionId(sessionDao.getCurrentSession().getId().toString());

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timeSetting.setStartTime(
                    output.format(
                            input.parse(startTime)
                    )
            );
            timeSetting.setEndTime(
                    output.format(
                            input.parse(endTime)
                    )
            );
        } catch (ParseException e) {
            logger.error("输入日期格式不正确！");
            return false;
        }
        timeSetting.setEditTime(output.format(new Date()));

        return timeSettingsDao.insetOneByEntity(timeSetting);
    }


    @Override
    public boolean deleteOneTimeSetting(TAMSTimeSettings tamsTimeSettings) {
        return timeSettingsDao.deleteOneByEntity(tamsTimeSettings);
    }

    @Override
    public List<TAMSTimeSettings> getallTimeSettings() {
        return timeSettingsDao.selectAllCurrentSession();
    }


    @Override
    public Long releaseDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects) {
        UTSession curSession = sessionDao.getCurrentSession();
        Long result = 0l;
        for (DepartmentFundingViewObject per : departmentFundingViewObjects) {
            TAMSDeptFunding exist = deptFundingDao.selectDeptFundsByDeptIdAndSession(per.getDepartmentId(), curSession.getId());
            TAMSDeptFundingDraft existDraft = tamsDeptFundingDraftDao.selectDeptDraftFundsByDeptIdAndSession(per.getDepartmentId(), curSession.getId());
            //将管理员设置的数额保存到正式部门经费表
            if (exist == null) {
                TAMSDeptFunding tamsDeptFunding = new TAMSDeptFunding();
                tamsDeptFunding.setSessionId(curSession.getId());
                tamsDeptFunding.setDepartmentId(per.getDepartmentId());
                tamsDeptFunding.setActualFunding(per.getActualFunding());
                tamsDeptFunding.setBonus(per.getBonus());
                tamsDeptFunding.setApplyFunding(per.getApplyFunding());
                tamsDeptFunding.setPhdFunding(per.getPhdFunding());
                tamsDeptFunding.setPlanFunding(per.getPlanFunding());
                tamsDeptFunding.setTravelSubsidy(per.getTrafficFunding());
                deptFundingDao.saveOneByEntity(tamsDeptFunding);

                //保存批准经费变更到批次经费 如果之前没有发布过，那么直接将金额加到现有的批准经费上面
                List<TAMSUniversityFunding> tamsUniversityFundings = tamsUniversityFundingDao.selectCurrBySession();
                if(tamsUniversityFundings!=null){
                    if(tamsUniversityFundings.get(0)!=null){
                        TAMSUniversityFunding tamsUniversityFundingExist = tamsUniversityFundings.get(0);
                        Long oldActualFunds = Long.valueOf(tamsUniversityFundingExist.getActualFunding());
                        Long newActualFunds = oldActualFunds + Long.valueOf(per.getActualFunding());
                        result += Long.valueOf(per.getActualFunding());
                        tamsUniversityFundingExist.setActualFunding(newActualFunds.toString());
                        tamsUniversityFundingDao.insertOneByEntity(tamsUniversityFundingExist);
                    }
                }

            } else {
                if (!per.getActualFunding().equals(exist.getActualFunding()) || !per.getPlanFunding().equals(exist.getPlanFunding())) { //如果金额有变化
                    Long changedFunds = Long.valueOf(per.getActualFunding())-Long.valueOf(exist.getActualFunding()); //变化额度等于新设置的金额减去旧金额
                    result += changedFunds;
                    exist.setActualFunding(per.getActualFunding()); //保存批准经费
                    exist.setPlanFunding(per.getPlanFunding());//保存计划经费
                    deptFundingDao.saveOneByEntity(exist);

                    //保存批准经费变更到批次经费 如果之前发布过，那么是加上变化的额度
                    List<TAMSUniversityFunding> tamsUniversityFundings = tamsUniversityFundingDao.selectCurrBySession();
                    if(tamsUniversityFundings!=null){
                        if(tamsUniversityFundings.get(0)!=null){
                            TAMSUniversityFunding tamsUniversityFundingExist = tamsUniversityFundings.get(0);
                            Long oldActualFunds = Long.valueOf(tamsUniversityFundingExist.getActualFunding());
                            Long newActualFunds = oldActualFunds + changedFunds;
                            tamsUniversityFundingExist.setActualFunding(newActualFunds.toString());
                            tamsUniversityFundingDao.insertOneByEntity(tamsUniversityFundingExist);
                        }
                    }
                }
            }
            //保存草稿表
            existDraft.setActualFunding(per.getActualFunding());//保存批准经费
            existDraft.setPlanFunding(per.getPlanFunding());//保存计划经费
            tamsDeptFundingDraftDao.saveOneByEntity(existDraft);
        }
        return result;
    }

    //发布课程经费
    @Override
    public boolean releaseClassFunding(List<ClassFundingViewObject> classFundingViewObjects) {
        UTSession curSession = sessionDao.getCurrentSession();
        for (ClassFundingViewObject per : classFundingViewObjects) {
            TAMSClassFunding exist = tamsClassFundingDao.getOneByClassIdAndSessionId(per.getClassId(), curSession.getId().toString());
            TAMSClassFundingDraft existDraft = tamsClassFundingDraftDao.selectOneByClassIdAndSessionId(per.getClassId(), curSession.getId().toString());
            //若为空，则直接将classfundingdraft中的经费添加到classfunding里面去
            if (exist == null) {
                TAMSClassFunding tamsClassFunding = new TAMSClassFunding();
                tamsClassFunding.setSessionId(curSession.getId().toString());
                tamsClassFunding.setClassId(per.getClassId());
                tamsClassFunding.setApplyFunding(per.getApplyFunding());
                tamsClassFunding.setAssignedFunding(per.getAssignedFunding());
                tamsClassFunding.setPhdFunding(per.getPhdFunding());
                tamsClassFunding.setBonus(per.getBonus());
                tamsClassFunding.setTravelSubsidy(per.getTravelSubsidy());
                tamsClassFundingDao.saveOneByEntity(tamsClassFunding);
            }
            //若不为空，则修改对应的classfunding
            else {
                if (!per.getAssignedFunding().equals(exist.getAssignedFunding())) {
                    exist.setAssignedFunding(per.getAssignedFunding());
                    tamsClassFundingDao.saveOneByEntity(exist);
                }
            }
            //保存草稿
            existDraft.setAssignedFunding(per.getAssignedFunding());
            tamsClassFundingDraftDao.insertOneByEntity(existDraft);
        }
        return true;
    }

    @Override
    public void saveDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects) {
        UTSession curSession = sessionDao.getCurrentSession();
        for (DepartmentFundingViewObject per : departmentFundingViewObjects) {
            TAMSDeptFundingDraft existDraft = tamsDeptFundingDraftDao.selectDeptDraftFundsByDeptIdAndSession(per.getDepartmentId(), curSession.getId());
            existDraft.setActualFunding(per.getActualFunding());//保存批准经费
            existDraft.setPlanFunding(per.getPlanFunding());//保存计划经费
            tamsDeptFundingDraftDao.saveOneByEntity(existDraft);
        }
    }

    @Override
    public void saveClassFunding(List<ClassFundingViewObject> classFundingViewObjects) {
        UTSession curSession = sessionDao.getCurrentSession();
        for (ClassFundingViewObject per : classFundingViewObjects) {
            TAMSClassFundingDraft existDraft = tamsClassFundingDraftDao.selectOneByClassIdAndSessionId(per.getClassId(), curSession.getId().toString());
            existDraft.setAssignedFunding(per.getAssignedFunding());
            tamsClassFundingDraftDao.insertOneByEntity(existDraft);
        }
    }

    @Override
    public void saveTaFunding(List<TaFundingViewObject> taFundingViewObjects) {
        UTSession curSession = sessionDao.getCurrentSession();
        for (TaFundingViewObject per : taFundingViewObjects) {
            TAMSTa exist = tamsTaDao.selectByClassIdAndSessionId(per.getClassNbr(), curSession.getId().toString());
            exist.setAssignedFunding(per.getAssignedFunding());
            tamsTaDao.insertByEntity(exist);
        }
    }

    @Override
    public void saveSessionFunding(List<SessionFundingViewObject> sessionFundingViewObjects) {
        for (SessionFundingViewObject per : sessionFundingViewObjects) {
            TAMSUniversityFunding existFunding = tamsUniversityFundingDao.selectCurrBySession().get(0);
            existFunding.setPlanFunding(per.getPlanFunding());
            tamsUniversityFundingDao.insertOneByEntity(existFunding);
        }
    }

    //获取工作时间
    @Override
    public short getWorkTime() {
        TAMSTimeSettingType timeSettingType = timeSettingTypeDao.selectByName("批次经费和学院经费设置");
        TAMSTimeSettingType tamsTimeSettingType = timeSettingTypeDao.selectByName("课程经费设置");
        TAMSTimeSettingType timeSettingTypeTaFunds = timeSettingTypeDao.selectByName("助教经费设置");
        TimeUtil timeUtil = new TimeUtil();
        if (timeSettingType == null || tamsTimeSettingType == null || timeSettingTypeTaFunds == null) {
            return 10;
        }
        if (!timeUtil.isBetweenPeriod(timeSettingType.getId(), sessionDao.getCurrentSession().getId().toString()))
            return 1;
        if (!timeUtil.isBetweenPeriod(tamsTimeSettingType.getId(), sessionDao.getCurrentSession().getId().toString()))
            return 2;
        if (!timeUtil.isBetweenPeriod(timeSettingTypeTaFunds.getId(), sessionDao.getCurrentSession().getId().toString()))
            return 3;
        else
            return 0;
    }

    /*
    @Override
    public void saveClassFunding(List<ClassFundingViewObject> classFundingViewObjects){
        UTSession curSession = sessionDao.getCurrentSession();

        for(ClassFundingViewObject classFundingViewObject:classFundingViewObjects){
            TAMSClassFunding exitFunding = tamsClassFundingDao.getOneByClassIdAndSessionId(classFundingViewObject.getClassId(),curSession.getId().toString());
            exitFunding.setAssignedFunding(classFundingViewObject.getAssignedFunding());
            tamsClassFundingDao.saveOneByEntity(exitFunding);
        }

    }
    */


    @Override
    public List<TAMSTimeSettingType> getAllTimeCategory() {
        return timeSettingTypeDao.selectAll();
    }

    @Override
    public boolean saveTimeCategory(TAMSTimeSettingType timeSettingType) {
        return timeSettingTypeDao.insertOneByEntity(timeSettingType);
    }

    @Override
    public boolean deleteTimeCategory(TAMSTimeSettingType timeSettingType) {
        return timeSettingTypeDao.deleteOneByEntity(timeSettingType);
    }

    @Override
    public String getSessionFundingStatistics() {
        List<TAMSDeptFunding> deptFunds = tamsDeptFundingDraftDao.selectDepartmentCurrDraftBySession();
        String totalPlan = "";
        Long setted = 0l;
        for (TAMSDeptFunding deptFunding : deptFunds) {
            setted = setted+Integer.parseInt(deptFunding.getPlanFunding());
        }
        if (tamsUniversityFundingDao.selectCurrBySession() == null || tamsUniversityFundingDao.selectCurrBySession().size() == 0)
            totalPlan = "0";
        else
            totalPlan = tamsUniversityFundingDao.selectCurrBySession().get(0).getPlanFunding();

        return setted+"("+totalPlan+")";
    }


    @Override
    public String getSessionFundingStatistics(String totalPlan) {
        List<TAMSDeptFunding> deptFundings = tamsDeptFundingDraftDao.selectDepartmentCurrDraftBySession();
        Long setted = 0l;
        for (TAMSDeptFunding deptFunding : deptFundings) {
            setted = setted+Integer.parseInt(deptFunding.getPlanFunding());
        }

        return setted+"("+totalPlan+")";
    }

    @Override
    public String getSessionFundingTotalApprove() {
        List<TAMSDeptFunding> deptFundings = tamsDeptFundingDraftDao.selectDepartmentCurrDraftBySession();
        Long setted = 0l;
        for (TAMSDeptFunding deptFunding : deptFundings) {
            setted = setted+Integer.parseInt(deptFunding.getActualFunding());
        }
        return setted.toString();
    }

    @Override
    public String getSessionFundingTotalApprove(List<DepartmentFundingViewObject> departmentFundingViewObjects) {
        Long totalApproved = 0l;
        for (DepartmentFundingViewObject departmentFundingViewObject : departmentFundingViewObjects) {
            totalApproved += Integer.parseInt(departmentFundingViewObject.getActualFunding());
        }
        return totalApproved.toString();
    }

    //从学院经费的表查询出各个学院批准的经费，作为课程的总批准经费
    @Override
    public String getClassTotalAssignedFunding() {
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        TAMSDeptFunding deptFunding;
        if (userInfoService.isSysAdmin(user.getCode()) || userInfoService.isAcademicAffairsStaff(user.getCode()))
            return this.getSessionFundingTotalApprove();
            //二级单位管理员看到该学院的批准经费
        else
            deptFunding = deptFundingDao.selectDeptFundsByDeptIdAndSession(user.getDepartmentId(), sessionDao.getCurrentSession().getId());
        if (deptFunding == null || deptFunding.getActualFunding().toString().equals("0"))
            return "经费未分配";  //表明该学院还未分配经费
        else
            return deptFunding.getActualFunding();
    }


    @Override
    public boolean initCourseManagerData() {
        boolean result = false;
        int i = 0;
        List<UTCourse> needManagerCourse = utCourseDao.getAllNeedManagerCourse();
        if (needManagerCourse != null) {
            for (UTCourse utCourse : needManagerCourse) {
                TAMSCourseManager tamsCourseManager = new TAMSCourseManager();
                tamsCourseManager.setCourseId(utCourse.getId().toString());
                tamsCourseManager.setCourseManagerId(null);
                tamsCourseManagerDao.saveCourseManager(tamsCourseManager);
                System.out.println(i++);
            }
            return true;
        }
        return result;

    }

    @Override
    public List<UTInstructor> getInstructorByNameAndCode(String name, String code) {
        if (name == null)
            name = "";
        if (code == null)
            code = "";

        List<UTInstructor> result = utInstructorDao.getInstructorByCode(name, code);
        if (result == null)
            result.add(new UTInstructor());  //填一个空对象
        return result;
    }

    @Override
    public boolean addCourseManagerByInsIdAndCourseId(String instructorId, String courseId) {
        TAMSCourseManager tamsCourseManagerExit = tamsCourseManagerDao.getCourseManagerByCourseId(courseId);
        if (tamsCourseManagerExit != null) {
            /**
             * 将该人添加为课程负责人角色
             */
            KRIM_ROLE_T krim_role_t = krim_role_t_dao.getKrimRoleTByName(COURSE_MANAGER_ROLE_NAME);
            krim_role_t.setChecked(true);
            List<KRIM_ROLE_T> needToAddRoleList = new ArrayList<>();
            needToAddRoleList.add(krim_role_t);
            KRIM_PRNCPL_T currentEntity = krim_prncpl_t_dao.getKrimEntityEntTypTByPrncplId(instructorId);
            if (krim_role_t != null && currentEntity != null) {
                krim_role_mbr_t_dao.saveKrimRoleMbrTByPrncpltAndRoles(currentEntity, needToAddRoleList);
                tamsCourseManagerExit.setCourseManagerId(instructorId);
                return tamsCourseManagerDao.saveCourseManager(tamsCourseManagerExit);   //将该用户保存到对应的course上
            }
        }
        return false;
    }

}
