package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface UTClassInstructorDao {

    List<UTClassInstructor> selectByClassId(String classId);

    List<Object> selectClassIdsByInstructorId(String uId);

    List<UTClassInstructor> getAllClassInstructor();

    void saveClassInstructorByList(List<UTClassInstructor> utClassInstructors);

    List<Map> getAllClassIdAndInstructorId(Map InstructorMap);

    List<Object> selectCourseManagerClassIdsByInstructorId(String uId);

}
