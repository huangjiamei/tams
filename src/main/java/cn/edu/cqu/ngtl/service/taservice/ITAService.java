package cn.edu.cqu.ngtl.service.taservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAService {

    TAMSTaApplication getApplicationByStuIdAndClassId(String stuId,String classId);


    //查询申请人电话
    String getApplicationPhoneNbr(String stuId, String classId);

    //查询申请人理由
    String getApplicationReason(String stuId, String classId);

    //根据姓名和学号查找候选人
    List<UTStudent> getConditionTaByNameAndId(Map<String, String> conditions);

    //根据studentid查询担任助教的classids
    List<Object> getClassIdsByUid();

    //根据classids查询classinfo的信息
    List<WorkBenchViewObject> getClassInfoByIds(List<Object> ids);

    UTClass applicationTable(Integer classId);

    UTClass applicationAssistantTable(Integer classId);

    /**
     * 助教提交申请的函数<br>
     * return : 状态码<br>
     * 1 : 不在学生申请助教期间<br>
     * 2 : 重复申请
     * 3 : 已经被此课程聘用
     * 4 : 成功
     * 5 : 未知错误
     *
     * @param application
     * @return
     */
    short submitApplicationAssistant(TAMSTaApplication application);

    List<TAMSTa> getAllTaFilteredByUid(String uId);

    //根据classid查申请者列表
    List<TAMSTaApplication> getAllApplicationFilterByClassid(String classId);

    //根据uid查看申请者列表
    List<TAMSTaApplication> getAllApplicationFilterByUid(String uId);

    List<Object> getMycourseByUid(String uId);

    boolean changeStatusBatchByIds(List<String> ids, String status);

    boolean changeStatusBatchByTaIds(List<String> ids, String status);

    int employBatchByStuIdsWithClassId(List<StuIdClassIdPair> stuIdClassIdPairs);

    boolean appraiseOutstandingToSpecifiedStatus(List<String> taIds, String uId, String workFlowStatusId);

    List<TAMSTeachCalendar> getTeachCalendarByClassId(String classId);

    TAMSTa getTaByTaId(String taId, String classId);

    List<TAMSTaTravelSubsidy> getTaTravelByStuIdAndClassId(String taId, String classId);

    boolean saveTravelSubsidy(TAMSTaTravelSubsidy tamsTaTravelSubsidy);

    boolean appraiseOutstanding(List<String> taIds, String uId);

    boolean revocationOutstanding(List<String> taIds, String uid);

    List<TaInfoViewObject> seachTainfoListByConditions(Map<String, String> conditions);

    List<TAMSWorkflowStatus> appriseStatusAvailable(String uid, String taId);

    String getTamsTaIdByStuIdAndClassId(String stuId, String classId);

    boolean deleteTravelSubsidyByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy);

    void countTravelSubsidy(String stuId, String classId, String totalTravelSubsidy);

    boolean addPhdFunds(String phdFundsNumber, String classId);

    boolean addBonus(String phdBonus, String classId);

    boolean isTravelSubsidy(String stuId, String classId);

    boolean dismissTa(List<StuIdClassIdPair> pairs, String uId);

}
