package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;

import java.util.List;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface UTClassInstructorDao {

    UTClassInstructor selectOneByClassId(Integer classId);

    List<Object> selectClassIdsByInstructorId(String uId);

    List<UTClassInstructor> getAllClassInstructor();

    void saveClassInstructorByList(List<UTClassInstructor> utClassInstructors);

}
