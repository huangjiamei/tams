package cn.edu.cqu.ngtl.service.adminservice.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dao.tams.*;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.enums.SESSION_ACTIVE;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.adminservice.IAdminService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CheckBoxStatus;
import cn.edu.cqu.ngtl.viewobject.adminInfo.RelationTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
/**
 * Created by tangjing on 16-10-25.
 */
@Service
@Component("AdminServiceImpl")
public class AdminServiceImpl implements IAdminService{

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
    private TAMSWorkflowStatusRDao workflowStatusRDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Autowired
    private TAMSWorkflowRoleFunctionDao workflowRoleFunctionDao;

    //课程负责人过滤
    @Autowired
    private TAMSCourseManagerDao tamsCourseManagerDao;

    @Override
    public List<TAMSCourseManager> getCourseManagerByCondition(Map<String, String> conditions){
        List<TAMSCourseManager> tamsCourseManagers = tamsCourseManagerDao.selectCourseManagerByCondition(conditions);
        return tamsCourseManagers;
    }

    //历史批次经费过滤
    @Autowired
    private TAMSUniversityFundingDao tamsUniversityFundingDao;

    @Override
    public List<TAMSUniversityFunding> getUniFundPreByCondition(Map<String, String> conditions){
        List<TAMSUniversityFunding> tamsUniversityFundings = tamsUniversityFundingDao.selectUniFundPreByCondition(conditions);
        return tamsUniversityFundings;
    }

    @Override
    public List<CMCourseClassification> getAllClassification() {
        List<CMCourseClassification> courseClassifications = courseClassificationDao.selectAll();
        return courseClassifications.size() != 0 ? courseClassifications : null;
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

    @Override
    public List<TAMSDeptFunding> getDepartmentCurrFundingBySession(){

        return deptFundingDao.selectDepartmentCurrBySession();
    }

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
    public void setWorkflowStatusRelationByRoleFunctionId(String rfId, RelationTable rt){
        //将原有的RFId的值删除，再加入新的值
        workflowStatusRDao.deleteTAMSWorkflowStatusRByRFId(rfId);

        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectAll();

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
        return deptFundingDao.selectAll();
    }
}
