package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DetailFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-11-1.
 */
public interface TAMSTaDao {

    TAMSTa selectById(String id);

    //查询全校所有助教
    List<TAMSTa> selectAll();

    List<TAMSTa> selectByClassIds(List<Object> classIds);

    List<Object> selectClassIdsByStudentId(String stuId);

    List<Object> selectClassIdsByStudentAuthId(String stuAuthId);

    List<TAMSTa> selectBatchByIds(List<String> ids);

    List<TAMSTa> selectBatchByTaIds(List<String> ids);

    boolean updateByEntity(TAMSTa ta);

    TAMSTa selectByStudentIdAndClassId(String stuId, String classId);

    boolean insertByEntity(TAMSTa newTa);

    List<TAMSTa> selectByClassId(String classId);

    //根据学院id查该学院的所有助教
    List<TAMSTa> selectByDeptId(String uId);

    //
    List<WorkBenchViewObject> selectAllCourseInfoByIds(List<Object> ids);

    List<TaFundingViewObject> selectTaFundByCondition(Map<String, String> conditions);

    //助教评优的辅助方法，用于判断助教当前状态是否可以被当前用户所操作
    boolean changeStatusAvailableForUser(String[] roleIds, String functionId, String taId);

    boolean toNextStatus(String[] roleIds, String functionId, String taId);

    boolean toPreviousStatus(String[] roleIds, String functionId, String taId);

    List<DetailFundingViewObject> selectDetailFundByCondition(Map<String, String> conditions);

    List<TaInfoViewObject> getTaInfoByConditions(Map<String,String> conditions);

    List<TAMSTa> selectByTaId(String taId);

    void deleteByTaIdAndClass(String taId,String classId);

    boolean changeStatusToSpecifiedStatus(String classId, String workflowStatusId);

    List<TAMSWorkflowStatus> getAvailableStatus(String[] roleIds ,String functionId,String taId);

    Map getAllHiredTaNumberMap();

    String countHiredTa(String classId);
    void deleteOneByEntity(TAMSTa tamsTa);
    TAMSTa selectByClassIdAndSessionId(String classId, String sessionId);

    TAMSTa selectByStudentIdAndClassIdAndSessionId(String stuId, String classId, String sessionId);
}
