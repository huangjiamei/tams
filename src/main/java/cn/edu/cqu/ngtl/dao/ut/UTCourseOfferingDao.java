package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTCourseOffering;

import java.util.List;

/**
 * Created by 金祖增 on 2016/10/20.
 */
public interface UTCourseOfferingDao {

    UTCourseOffering getUTCourseOfferingByID(Integer id);

    void saveCourseOfferingByList(List<UTCourseOffering> courseOfferingList);

}
