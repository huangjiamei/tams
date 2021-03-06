package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;

import java.util.List;
import java.util.Map;
/**
 * Created by tangjing on 16-10-13.
 */
public interface UTCourseDao {

    UTCourse selectOneById(Integer Id);

    List<UTCourse> selectAllMappedByDepartment();

    void InsertOneByEntity(UTCourse utCourse);

    List<UTCourse> getAllNeedManagerCourse();

    List<Map> getCourseNameIdMap();

}
