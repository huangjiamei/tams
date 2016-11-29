package cn.edu.cqu.ngtl.service.taservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAService {

    UTClassInformation getClassInfoById(Integer id);

    UTClass applicationTable(Integer classId);

    UTClass applicationAssistantTable(Integer classId);

    boolean submitApplicationAssistant(TAMSTaApplication application);

    List<TAMSTa> getAllTaFilteredByUid(String uId);

    List<TAMSTaApplication> getAllApplicationFilterByUid(String uId);

    boolean changeStatusBatchByIds(List<String> ids, String status);

    boolean changeStatusBatchByTaIds(List<String> ids, String status);

    boolean employBatchByStuIdsWithClassId(List<StuIdClassIdPair> stuIdClassIdPairs);

    List<TAMSTeachCalendar> getTeachCalendarByClassId(String classId);

    TAMSTa getTaByTaId(String taId,String classId);

}
