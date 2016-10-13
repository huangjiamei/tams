package cn.edu.cqu.ngtl.dao;


import cn.edu.cqu.ngtl.dataobject.UTRoom;

import java.util.List;

public interface UTRoomDao {
	
  public List<UTRoom> getAllRooms();
  
  public List<UTRoom> getRoomsByCampusIdForExam(Integer campusId);
}
