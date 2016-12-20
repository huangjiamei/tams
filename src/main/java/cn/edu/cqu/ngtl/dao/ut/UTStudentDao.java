package cn.edu.cqu.ngtl.dao.ut;


import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;

import java.util.List;
import java.util.Map;


public interface UTStudentDao {

	UTStudent getUTStudentById(String id);
	
	UTStudent selectByAuthId(String authId);

	List<UTStudent> selectStudentByNameAndId(Map<String, String> conditions);
}
