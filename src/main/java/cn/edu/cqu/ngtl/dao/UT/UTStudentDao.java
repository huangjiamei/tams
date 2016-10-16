package cn.edu.cqu.ngtl.dao.UT;


import cn.edu.cqu.ngtl.dataobject.UT.UTStudent;


public interface UTStudentDao {

	UTStudent getUTStudentById(String id);
	
	UTStudent saveUTStudent(UTStudent utStudent);

}
