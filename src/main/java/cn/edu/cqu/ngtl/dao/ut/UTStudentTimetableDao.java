package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTStudentTimetable;

import java.util.List;

/**
 * Created by awake on 2016/12/12.
 */
public interface UTStudentTimetableDao {

    void insertOneByEntity(UTStudentTimetable utStudentTimetable);

    void insertOneByEntityList(List<UTStudentTimetable> utStudentTimetables);

    List<UTStudentTimetable> getStudentTimetableByUid(String uId);
}
