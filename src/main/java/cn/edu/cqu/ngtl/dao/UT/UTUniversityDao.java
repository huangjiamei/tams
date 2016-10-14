package cn.edu.cqu.ngtl.dao.UT;

import cn.edu.cqu.ngtl.dataobject.UT.UTUniversity;

import java.util.List;

public interface UTUniversityDao {

	List<UTUniversity> getAllUniversities();

	UTUniversity getEXMUniversityByUniversityCodeAndUniversityName(String code, String name);

	UTUniversity saveEXMUniversity(UTUniversity exmUniversity);

	void deleteEXMUniversity(UTUniversity exmUniversity);

	UTUniversity getIdByUniversityCode(String code);

}
