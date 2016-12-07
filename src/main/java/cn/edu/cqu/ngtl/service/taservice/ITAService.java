package cn.edu.cqu.ngtl.service.taservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAService {

    //根据姓名和学号查找候选人
    List<UTStudent> getConditionTaByNameAndId(Map<String, String> conditions);

    //根据studentid查询担任助教的classids
    List<Object> getClassIdsByUid();

    //根据classids查询classinfo的信息
    List<WorkBenchViewObject> getClassInfoByIds(List<Object> ids);

    UTClass applicationTable(Integer classId);

    UTClass applicationAssistantTable(Integer classId);

    boolean submitApplicationAssistant(TAMSTaApplication application);

    List<TAMSTa> getAllTaFilteredByUid(String uId);

    //根据classid查申请者列表
    //List<TAMSTaApplication> getAllApplicationFilterByUid(String classId);

    //根据uid查看申请者列表
    List<TAMSTaApplication> getAllApplicationFilterByUid(String uId);

    boolean changeStatusBatchByIds(List<String> ids, String status);

    boolean changeStatusBatchByTaIds(List<String> ids, String status);

    boolean employBatchByStuIdsWithClassId(List<StuIdClassIdPair> stuIdClassIdPairs);


    List<TAMSTeachCalendar> getTeachCalendarByClassId(String classId);

    TAMSTa getTaByTaId(String taId,String classId);

    List<TAMSTaTravelSubsidy> getTaTravelByStuIdAndClassId(String taId,String classId);

    boolean saveTravelSubsidy(TAMSTaTravelSubsidy tamsTaTravelSubsidy);

    boolean appraiseOutstanding(List<String> taIds, String uId);

    boolean revocationOutstanding(List<String> taIds, String uid);
}
