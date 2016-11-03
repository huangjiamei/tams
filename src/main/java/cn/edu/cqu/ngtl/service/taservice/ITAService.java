package cn.edu.cqu.ngtl.service.taservice;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
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
}
