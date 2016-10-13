package cn.edu.cqu.ngtl.dao.impl;

import cn.edu.cqu.ngtl.dao.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.CMProgram;
import cn.edu.cqu.ngtl.dataobject.UTStudent;
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
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().find(UTStudent.class, id);
	}

	@Override
	public UTStudent saveUTStudent(UTStudent utStudent) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(utStudent);
	}

	@Override
	public void delUTStudent(UTStudent utStudent) {
		// TODO Auto-generated method stub
		KradDataServiceLocator.getDataObjectService().delete(utStudent);
	}

	@Override
	public List<UTStudent> getAllUTStudent() {
		// TODO Auto-generated method stub
		EntityManagerFactory entityManagerFactory = KRADServiceLocator.getEntityManagerFactory();
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<UTStudent> q = em.createQuery("select c from UTStudent c", UTStudent.class);
		List<UTStudent> utStudents = q.getResultList();
		em.close();
		return utStudents;
		
/*		QueryResults<UTStudent> qr =  KradDataServiceLocator.getDataObjectService().findAll(UTStudent.class);
		return qr.getResults();*/
	}

	@Override
	public List<UTStudent> getUTStudentWithCase(String campusId, String departmentId, String programId, String grade) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<Predicate>();
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

	@Override
	public Boolean inEnglishProgram(UTStudent utStudent) {
		// TODO Auto-generated method stub
		if(utStudent.getProgram()==null)
			return false;
		return utStudent.getProgram().getName().equals("英语");

	}

	@Override
	public Boolean inSmallLanguageProgram(UTStudent utStudent) {
		// TODO Auto-generated method stub
		String departmentName = utStudent.getDepartment().getName();
		if(departmentName.equals("外国语学院")){
			CMProgram program = utStudent.getProgram();
			if(program==null)
				return false;
			return ! program.getName().equals("英语");
		}else
			return false;
	}

	//是否在 弘深、博雅、uc 学院
	@Override
	public Boolean inUcOrByDepartment(UTStudent utStudent) {
		// TODO Auto-generated method stub
		if(utStudent.getDepartment()==null)
			return false;
		String code = utStudent.getDepartment().getDeptcode();
		if(code==null)
			return false;
		//35 UC 34 博雅  31 弘深
		return code.equals("35")||code.equals("34")||code.equals("31");
	}
	
	@Override 
	public List<UTStudent> getUtStudentByCondition(String exmStudentId, String exmStudentAuthId, String exmStudentName,
			String exmStudentGender, String exmStudentDepartmentId, String exmStudentProgram, String exmStudentGrade,
			String exmStudentIdNumber, String exmStudentVirtual,String exmStudentCurrent) { 
		
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
