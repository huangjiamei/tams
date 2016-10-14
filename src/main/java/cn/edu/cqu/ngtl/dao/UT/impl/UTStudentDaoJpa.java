package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.CM.CMProgram;
import cn.edu.cqu.ngtl.dataobject.UT.UTStudent;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

@Repository
public class UTStudentDaoJpa implements UTStudentDao {

	@Override
	public UTStudent getUTStudentById(String id) {

		return KradDataServiceLocator.getDataObjectService().find(UTStudent.class, id);

	}

	@Override
	public UTStudent saveUTStudent(UTStudent utStudent) {

		return KradDataServiceLocator.getDataObjectService().save(utStudent);

	}

	@Override
	public void delUTStudent(UTStudent utStudent) {

		KradDataServiceLocator.getDataObjectService().delete(utStudent);

	}

	@Override
	public List<UTStudent> getAllUTStudent() {

		/*
		QueryResults<UTStudent> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTStudent.class);
		return qr.getResults();
		*/

		EntityManagerFactory entityManagerFactory = KRADServiceLocator.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<UTStudent> q = em.createQuery("select c from UTStudent c", UTStudent.class);
		List<UTStudent> utStudents = q.getResultList();
		em.close();

		return utStudents;

	}

	@Override
	public List<UTStudent> getUTStudentWithCase(String campusId, String departmentId, String programId, String grade) {

		List<Predicate> predicates = new ArrayList();
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create();
		
		if(campusId!=null){
			predicates.add(equal("campusId",campusId));
		}
		if(departmentId != null){
			predicates.add(equal("departmentId", departmentId));
		}
		if(programId !=null){
			predicates.add(equal("programId", programId));
		}
		if(grade !=null){
			predicates.add(equal("grade", grade));
		}
		Predicate[] preds = predicates.toArray(new Predicate[predicates.size()]);
		criteria.setPredicates(preds);
		
		QueryResults<UTStudent> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTStudent.class, criteria.build());

		return qr.getResults();

	}

	/**
	 * 判断是否在弘深、博雅、UC学院
	 * @param utStudent
	 * @return true or false
	 */
	@Override
	public Boolean inUcOrByDepartment(UTStudent utStudent) {

		if(utStudent.getDepartment()==null)
			return false;
		String code = utStudent.getDepartment().getDeptcode();
		if(code==null)
			return false;
		//35 UC 34 博雅  31 弘深
		return code.equals("35")||code.equals("34")||code.equals("31");

	}
	
	@Override 
	public List<UTStudent> getUtStudentByCondition(String exmStudentId, String exmStudentAuthId,
												   String exmStudentName, String exmStudentGender,
												   String exmStudentDepartmentId, String exmStudentProgram,
												   String exmStudentGrade, String exmStudentIdNumber,
												   String exmStudentVirtual,String exmStudentCurrent) {
		
		if(exmStudentId==null){
				exmStudentId = "*";
		}else{
			exmStudentId =  "*" + exmStudentId + "*";
		}

		if(exmStudentGender==null||exmStudentGender.equalsIgnoreCase("0")){
			exmStudentGender = "*";
		}

		if(exmStudentAuthId==null){
			exmStudentAuthId =  "*";
		}else{
			exmStudentAuthId =  "*" + exmStudentAuthId + "*";
		}

		if(exmStudentName==null){
			exmStudentName = "*";
		}else{
			exmStudentName = "*" + exmStudentName + "*";
		}

		if(exmStudentDepartmentId==null){
			exmStudentDepartmentId = "*";
		}

		if(exmStudentProgram==null){
			exmStudentProgram = "*";
		}

		if(exmStudentGrade==null){
			exmStudentGrade = "*";
		}

		if(exmStudentIdNumber==null){
			exmStudentIdNumber = "*";
		}else{
			exmStudentIdNumber = "*" + exmStudentIdNumber + "*";
		}

		if(exmStudentVirtual==null){
			exmStudentVirtual = "*";
		}

		if(exmStudentCurrent==null){
			exmStudentCurrent = "*";
		}
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( and(like("id" , exmStudentId),
									like("authId" , exmStudentAuthId),
									like("name" , exmStudentName),
									like("gender" , exmStudentGender),
									like("department.id" , exmStudentDepartmentId),
									like("program.id" , exmStudentProgram),
									like("grade" , exmStudentGrade),
									like("idNumber" , exmStudentIdNumber),
									like("virtual" , exmStudentVirtual),
									like("enrolled" , exmStudentCurrent)));

		QueryResults<UTStudent> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTStudent.class, criteria.build());

		return qr.getResults();

	}
	
	public List<UTStudent> getUtStudentByDepartment(Integer departmentId){

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( and(equal("departmentId" , departmentId)));

		QueryResults<UTStudent> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTStudent.class, criteria.build());

		return qr.getResults();

	}
	
	public List<UTStudent> getUtStudentByDepartmentAndEnrolled(Integer departmentId,String enrolled){

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( and(equal("departmentId" , departmentId),equal("enrolled" , enrolled)));

		QueryResults<UTStudent> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTStudent.class, criteria.build());

		return qr.getResults();
	}
	
	public List<UTStudent> getUtStudentByEnrolled(String enrolled){

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates( and(equal("enrolled" , enrolled)));

		QueryResults<UTStudent> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTStudent.class, criteria.build());

		return qr.getResults();

	}
}
