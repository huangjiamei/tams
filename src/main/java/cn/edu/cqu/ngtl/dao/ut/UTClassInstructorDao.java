package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface UTClassInstructorDao {

    UTClassInstructor selectOneByClassId(Integer classId);

}
