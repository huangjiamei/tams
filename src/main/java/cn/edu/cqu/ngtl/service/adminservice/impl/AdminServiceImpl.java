package cn.edu.cqu.ngtl.service.adminservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.enums.SESSION_ACTIVE;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-25.
 */
@Service
@Component("AdminServiceImpl")
public class AdminServiceImpl implements IAdminService{

    private static final Logger logger = Logger.getRootLogger();

    @Autowired
    private TAMSTimeSettingTypeDao timeSettingTypeDao;

    @Autowired
    private TAMSDeptFundingDraftDao tamsDeptFundingDraftDao;


    @Autowired
    private TAMSDeptFundingDao tamsDeptFundingDao;

    @Autowired
    private TAMSClassFundingDao tamsClassFundingDao;

    @Autowired
    private TAMSClassFundingDraftDao  tamsClassFundingDraftDao;

    @Autowired
    private CMCourseClassificationDao courseClassificationDao;

    @Autowired
    private TAMSTaCategoryDao tamsTaCategoryDao;

    @Autowired
    private TAMSIssueTypeDao issueTypeDao;

    @Autowired
    private UTSessionDao sessionDao;

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


    @Override
    public List<TAMSCourseManager> getCourseManagerByCondition(Map<String, String> conditions){
        List<TAMSCourseManager> tamsCourseManagers = tamsCourseManagerDao.selectCourseManagerByCondition(conditions);
        return tamsCourseManagers;
    }

    //历史批次经费过滤

    @Override
    public List<TAMSUniversityFunding> getUniFundPreByCondition(Map<String, String> conditions){
        List<TAMSUniversityFunding> tamsUniversityFundings = tamsUniversityFundingDao.selectUniFundPreByCondition(conditions);
        return tamsUniversityFundings;
    }

    //课程经费过滤
    @Override
    public List<ClassFundingViewObject> getClassFundByCondition(Map<String, String> conditions) {
        List<ClassFundingViewObject> classFundingViewObjects = tamsClassFundingDao.selectClassFundByCondition(conditions);
        return classFundingViewObjects;
    }

    @Autowired
    private TAMSTaDao tamsTaDao;
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
        return courseClassifications.size() != 0 ? courseClassifications : null;
    }

    //获取工作流类型
    @Override
    public List<TAMSWorkflowStatus> getWorkFlowCategory(){
        List<TAMSWorkflowStatus> workflowStatuses = workflowStatusDao.selectAll();
        return workflowStatuses.size() !=0 ? workflowStatuses : null;
    }

    //工作流类型过滤
    @Override
    public List<TAMSWorkflowStatus> getWorkFlowCategoryByCondition(String workflowfunction){
        List<TAMSWorkflowStatus> workflowStatuses = workflowStatusDao.selectWorkFlowByCondition(workflowfunction);
        return workflowStatuses.size() !=0 ? workflowStatuses : null;
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
        if(workflowStatusDao.saveOne(tamsWorkflowStatus))
            return true;
        else
            return false;
    }


    //删除工作流类型
    @Override
    public boolean deleteWorkFlowCategory(TAMSWorkflowStatus tamsWorkflowStatus){
        if(workflowStatusDao.deleteOne(tamsWorkflowStatus))
            return true;
        else
            return false;
    }

    @Override
    public boolean addCourseClassificationOnlyWithName(String name) {

        if(courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
            return false;

        CMCourseClassification courseClassification = new CMCourseClassification();
        courseClassification.setName(name);

        return courseClassificationDao.insertOneByEntity(courseClassification);
    }

    @Override
    public boolean changeCourseClassificationNameById(Integer id, String name) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if(courseClassification == null)
            return false;
        if(courseClassificationDao.selectOneByName(name) != null)
            //存在同名数据
            return false;

        courseClassification.setId(id);
        courseClassification.setName(name);

        return courseClassificationDao.updateOneByEntity(courseClassification);
    }

    @Override
    public boolean removeCourseClassificationById(Integer id) {
        CMCourseClassification courseClassification = courseClassificationDao.selectOneById(id);

        if(courseClassification == null)
            return false;

        return courseClassificationDao.deleteOneByEntity(courseClassification);
    }

    @Override
    public List<TAMSTaCategory> getAllTaCategories() {
        List<TAMSTaCategory> tamsTaCategories = tamsTaCategoryDao.selectAll();
        return tamsTaCategories.size() != 0 ? tamsTaCategories : null;
    }

    @Override
    public boolean addTaCategory(TAMSTaCategory newTaCategory) {
        if(tamsTaCategoryDao.selectOneByName(newTaCategory.getName()) != null)
            //存在同名数据
            return false;

        return tamsTaCategoryDao.insertOneByEntity(newTaCategory);
    }

    @Override
    public boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory) {
        TAMSTaCategory isExist = tamsTaCategoryDao.selectOneByName(tamsTaCategory.getName());
        if(isExist != null && ! isExist.getId().equals(tamsTaCategory.getId()))
            //存在同名数据,而且非本数据
            return false;

        return tamsTaCategoryDao.updateOneByEntity(tamsTaCategory);
    }

    @Override
    public boolean removeTaCategoryById(Integer id) {
        TAMSTaCategory tamsTaCategory = tamsTaCategoryDao.selectOneById(id);

        if(tamsTaCategory == null)
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

        if(isInDataBase != null)
            return false;

        return issueTypeDao.insertOneByEntity(issueType);
    }

    @Override
    public List<TAMSCourseManager> getAllCourseManager(){

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

        if(isExist != null)
            return false;

        //新建数据的预处理
        session.setActive(SESSION_ACTIVE.NO);
        //// FIXME: 16-10-27 还需要处理预算

        return sessionDao.insertOneByEntity(session);
    }


    @Override
    public boolean changeIssueType(TAMSIssueType issueType) {
        TAMSIssueType isExist = issueTypeDao.selectOneByTypeName(issueType.getTypeName());
        if(isExist != null && ! isExist.getId().equals(issueType.getId()))
            //存在同名数据,而且非本数据
            return false;

        return issueTypeDao.updateOneByEntity(issueType);
    }

    @Override
    public boolean removeIssueTypeById(String id) {
        TAMSIssueType issueType = issueTypeDao.selectOneById(id);

        if(issueType == null)
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

        if(session == null)
            return false;

        return sessionDao.deleteOneByEntity(session);
    }


    @Override
    public boolean setCurrentSession (String termYear, String termTerm){

        UTSession session = sessionDao.selectByYearAndTerm(termYear, termTerm);
        if(session.getActive().equals("Y")){
            return true;
        }
        else{
            UTSession utSession = sessionDao.getCurrentSession();
            utSession.setActive("N");
            sessionDao.updateOneByEntity(utSession);
            session.setActive("Y");
            return sessionDao.updateOneByEntity(session);

        }
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
    public List<TAMSDeptFunding> getDepartmentCurrFundingBySession(){

        User user = (User)GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示草稿表的内容，在下拉框里显示发布的数据
         */
        if(userInfoService.isAcademicAffairsStaff(user.getCode())||userInfoService.isSysAdmin(user.getCode())){
            return tamsDeptFundingDraftDao.selectDepartmentCurrDraftBySession();
        }
        return deptFundingDao.selectDepartmentCurrBySession();
    }

    //学院当前经费过滤
    public List<TAMSDeptFunding> getDeptFundCurrByCondition(Map<String, String> conditions) {
        User user = (User)GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示过滤后草稿表的内容，在下拉框里显示发布的数据
         */
        if(userInfoService.isAcademicAffairsStaff(user.getCode())||userInfoService.isSysAdmin(user.getCode())) {
            List<TAMSDeptFunding> list =tamsDeptFundingDraftDao.selectDeptFundDraftCurrByCondition(conditions);
            return list;
        }
        else{
            return tamsDeptFundingDao.selectDeptFundCurrByCondition(conditions);
        }
    }

    //获取学院历史经费
    @Override
    public List<TAMSDeptFunding> getDepartmentPreFundingBySession(){

        return deptFundingDao.selectDepartmentPreBySession();
    }

    @Override
    public List<TAMSDeptFunding> getDepartmentPreFundingByCondition(String uId, Map<String, String> conditions){
        if (!userInfoService.isSysAdmin(uId)){
            List<TAMSDeptFunding> deptFundings = deptFundingDao.selectDeptFundPreByCondition(conditions);
            return deptFundings;
        }
        return null;
    }

    @Override
    public List<TAMSWorkflowStatusR> getWorkflowStatusRelationByRoleFunctionId(String roleFunctionId) {
        if(roleFunctionId == null)
            return null;

        return workflowStatusRDao.selectByRFId(roleFunctionId);

    }

    @Override
    public String getRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId) {
        if(roleId == null || functionId == null)
            return null;

        return workflowRoleFunctionDao.selectIdByRoleIdAndFunctionId(roleId, functionId);
    }

    @Override
    public String setRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId){
        //如果找得到RFId就找找不到就创建新的
        String roleFunctionId = this.getRoleFunctionIdByRoleIdAndFunctionId(roleId, functionId);

        if(roleFunctionId == null) {
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
    public void setWorkflowStatusRelationByRoleFunctionId(String functionId, String rfId, RelationTable rt){
        //将原有的RFId的值删除，再加入新的值
        workflowStatusRDao.deleteTAMSWorkflowStatusRByRFId(rfId);

        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);

        int length = allStatus.size();
        CheckBoxStatus[][] matrix = rt.getData();
        for(int i=0;i<length;i++){
            for(int j=0;j<length;j++){
                if(matrix[i][j].isChecked()) {
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


        User user = (User)GlobalVariables.getUserSession().retrieveObject("user");

        /**如果是教务处管理员或者系统管理员则显示草稿表的内容，在下拉框里显示发布的数据
         */
        if(userInfoService.isAcademicAffairsStaff(user.getCode())||userInfoService.isSysAdmin(user.getCode())){
            return tamsClassFundingDraftDao.selectAll();
        }
            return tamsClassFundingDao.selectAll(user);
    }

    @Override
    public boolean addTimeSetting(User user, String typeId, String startTime, String endTime) {
        //// FIXME: 16-11-23 测试需要，必须去掉一个!
        if(!!userInfoService.hasPermission(user, "ViewConsolePage")) {
            logger.warn("未授权用户请求：(新增时间段)addTimeSetting(User user, String typeId, String startTime, String endTime)\n");
            logger.warn("未授权用户信息：" + user.toString() + "\n");
            return false;
        }
        TAMSTimeSettings isExist = timeSettingsDao.selectByTypeId(typeId);
        if(isExist != null) {
            logger.warn("管理员请求新增时间段失败：" + user.toString() + "\n");
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
            }
            catch (ParseException e) {
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
        }
        catch (ParseException e) {
            logger.error("输入日期格式不正确！");
            return false;
        }
        timeSetting.setEditTime(output.format(new Date()));

        return timeSettingsDao.insetOneByEntity(timeSetting);
    }


    @Override
    public boolean deleteOneTimeSetting(TAMSTimeSettings tamsTimeSettings){
        return timeSettingsDao.deleteOneByEntity(tamsTimeSettings);
    }

    @Override
    public List<TAMSTimeSettings> getallTimeSettings() {
        return timeSettingsDao.selectAll();
    }


    @Override
    public boolean releaseDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects){
        UTSession curSession = sessionDao.getCurrentSession();
        for(DepartmentFundingViewObject per : departmentFundingViewObjects){
            TAMSDeptFunding exist = deptFundingDao.selectDeptFundsByDeptIdAndSession(per.getDepartmentId(),curSession.getId());
            TAMSDeptFundingDraft existDraft = tamsDeptFundingDraftDao.selectDeptDraftFundsByDeptIdAndSession(per.getDepartmentId(),curSession.getId());
            //将管理员设置的数额保存到正式部门经费表
            if(exist==null) {
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
            }else{
                if(!per.getActualFunding().equals(exist.getActualFunding())||!per.getPlanFunding().equals(exist.getPlanFunding())){
                    exist.setActualFunding(per.getActualFunding()); //保存批准经费
                    exist.setPlanFunding(per.getPlanFunding());//保存计划经费
                    deptFundingDao.saveOneByEntity(exist);
                }
            }
            //保存草稿表
            existDraft.setActualFunding(per.getActualFunding());//保存批准经费
            existDraft.setPlanFunding(per.getPlanFunding());//保存计划经费
            tamsDeptFundingDraftDao.saveOneByEntity(existDraft);
        }
        return true;
    }

    @Override
    public void saveDeptFunding(List<DepartmentFundingViewObject> departmentFundingViewObjects){
        UTSession curSession = sessionDao.getCurrentSession();
        for(DepartmentFundingViewObject per : departmentFundingViewObjects){
            TAMSDeptFundingDraft existDraft = tamsDeptFundingDraftDao.selectDeptDraftFundsByDeptIdAndSession(per.getDepartmentId(),curSession.getId());
            existDraft.setActualFunding(per.getActualFunding());//保存批准经费
            existDraft.setPlanFunding(per.getPlanFunding());//保存计划经费
            tamsDeptFundingDraftDao.saveOneByEntity(existDraft);
        }
    }

    @Override
    public void saveSessionFunding(List<SessionFundingViewObject> sessionFundingViewObjects){
        UTSession curSession = sessionDao.getCurrentSession();
        for(SessionFundingViewObject per:sessionFundingViewObjects){
            TAMSUniversityFunding existFunding = tamsUniversityFundingDao.selectCurrBySession().get(0);
            existFunding.setActualFunding(per.getActualFunding());
            tamsUniversityFundingDao.insertOneByEntity(existFunding);
        }
    }


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
}
