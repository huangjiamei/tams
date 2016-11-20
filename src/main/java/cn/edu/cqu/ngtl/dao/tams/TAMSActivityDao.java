package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSActivity;
import java.util.List;

/**
 * Created by damei on 16/11/15.
 */
public interface TAMSActivityDao {
    List<TAMSActivity> selectAllByCalendarId(String CalendarId);
}
