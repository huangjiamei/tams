package cn.edu.cqu.ngtl.dao;

import cn.edu.cqu.ngtl.dataobject.UTUniversity;

import java.util.List;

public interface UTUniversityDao {
	public List<UTUniversity> getAllUniversities();
	public UTUniversity getEXMUniversityByUniversityCodeAndUniversityName(String code, String name);
	public UTUniversity saveEXMUniversity(UTUniversity exmUniversity);
	public void deleteEXMUniversity(UTUniversity exmUniversity);
	public UTUniversity getIdByUniversityCode(String code);
}
