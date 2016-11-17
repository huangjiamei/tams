package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;

import java.util.List;

/**
 * Created by tangjing on 16-11-15.
 */
public interface TAMSTeachCalendarDao {

    List<TAMSTeachCalendar> selectAllByClassId(String classId);

    boolean insertByEntity(TAMSTeachCalendar teachCalendar);
}