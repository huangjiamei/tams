package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016-10-19.
 */
public interface UTClassInfoDao {

    List<UTClassInformation> getAllCurrentClassInformation();

    UTClassInformation getOneById(Integer id);

    List<UTClassInformation> selectBatchByIds(List<Object> classIds);

    List<UTClass> selectByConditions(Map<String, String> conditions);
}
