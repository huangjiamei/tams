package cn.edu.cqu.ngtl.dao.UT;


import cn.edu.cqu.ngtl.dataobject.UT.UTRoom;

import java.util.List;

public interface UTRoomDao {

    List<UTRoom> getAllRooms();

    List<UTRoom> getRoomsByCampusIdForExam(Integer campusId);

}
