package cn.edu.cqu.ngtl.dao.UT;

import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;

import java.util.List;

/**
 * Created by tangjing on 16-10-13.
 */
public interface UTCourseDao {

    UTCourse selectOneById(Integer Id);

    List<UTCourse> selectAllMappedByDepartment();

}
