package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTCourseOfferingConfig;

import java.util.List;

/**
 * Created by awake on 2016/12/8.
 */
public interface UTOfferingConfigDao {

    void saveUTOfferingConfigByList(List<UTCourseOfferingConfig> utCourseOfferingConfigList);
}
