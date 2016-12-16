package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
@Repository
@Component("UTStudentDaoJpa")
public class UTStudentDaoJpa implements UTStudentDao {

	EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

	@Override
	public UTStudent getUTStudentById(String id) {

		return KradDataServiceLocator.getDataObjectService().find(UTStudent.class, id);

	}

	@Override
	public UTStudent saveUTStudent(UTStudent utStudent) {

		return KradDataServiceLocator.getDataObjectService().save(utStudent);

	}

	//根据姓名和学号查询学生
	public List<UTStudent> selectStudentByNameAndId(Map<String, String> conditions){
		//加通配符
		int countNull = 0;
		for (Map.Entry<String, String> entry : conditions.entrySet()) {
			if (entry.getValue() == null) {
				conditions.put(entry.getKey(), "%");
				countNull++;
			} else
				conditions.put(entry.getKey(), "%" + entry.getValue() + "%");
		}
		if(countNull != 2) {
			Query qr = em.createNativeQuery("SELECT * FROM UNITIME_STUDENT s WHERE s.NAME LIKE '"+conditions.get("StudentName")+"' AND s.UNIQUEID LIKE '"+conditions.get("StudentId")+"' AND s.UNIQUEID NOT IN (SELECT TA_ID FROM TAMS_TA) AND s.UNIQUEID NOT IN (SELECT APPLICANT_ID FROM TAMS_TA_APPLICATION) ", UTStudent.class);
			return qr.getResultList().size() !=0 ? qr.getResultList() : null;
		}
		//若输入为空，返回。。。。
		else{
			return null;
		}
	}

}
