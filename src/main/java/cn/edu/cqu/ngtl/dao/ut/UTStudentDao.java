package cn.edu.cqu.ngtl.dao.ut;


import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;


public interface UTStudentDao {

	UTStudent getUTStudentById(String id);
	
	UTStudent saveUTStudent(UTStudent utStudent);

}
