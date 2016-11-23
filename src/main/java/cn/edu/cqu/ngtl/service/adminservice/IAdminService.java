package cn.edu.cqu.ngtl.service.adminservice;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.viewobject.adminInfo.RelationTable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
/**
 * Created by tangjing on 16-10-25.
 */
public interface IAdminService {

    List<CMCourseClassification> getAllClassification();

    boolean addCourseClassificationOnlyWithName(String name);

    boolean changeCourseClassificationNameById(Integer id, String name);

    boolean removeCourseClassificationById(Integer id);

    List<TAMSCourseManager> getAllCourseManager();

    List<TAMSTaCategory> getAllTaCategories();

    boolean addTaCategory(TAMSTaCategory newTaCategory);

    boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory);

    boolean removeTaCategoryById(Integer id);

    List<TAMSIssueType> getAllIssueTypes();

    boolean addTaIssueType(TAMSIssueType issueType);

    List<UTSession> getAllSessions();

    List<UTSession> getSelectedSessions(String termName, String startTime, String endTime) throws ParseException;

    boolean addSession(UTSession session);

    boolean changeIssueType(TAMSIssueType issueType);

    boolean removeIssueTypeById(String id);

    boolean changeSession(UTSession session);

    boolean removeTermByYearAndTerm(String termYear, String termTerm);

/*
    List<TAMSDeptFunding> getCurrFundingBySession();

    List<TAMSDeptFunding> getPreviousFundingBySession();
    */

    //获取学校经费
    List<TAMSUniversityFunding> getCurrFundingBySession();

    List<TAMSUniversityFunding> getPreviousFundingBySession();

    List<TAMSWorkflowStatusR> getWorkflowStatusRelationByRoleFunctionId(String roleFunctionId);

    String getRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId);

    String setRoleFunctionIdByRoleIdAndFunctionId(String roleId, String functionId);

    void setWorkflowStatusRelationByRoleFunctionId(String rfId, RelationTable rt);

    List<TAMSDeptFunding> getDepartmentCurrFundingBySession();

    List<TAMSDeptFunding> getDepartmentPreFundingBySession();

    List<TAMSClassFunding> getFundingByClass();

    List<TAMSCourseManager> getCourseManagerByCondition(Map<String, String> conditions);

    //学校经费过滤
    List<TAMSUniversityFunding> getUniFundPreByCondition(Map<String, String> conditions);
}
