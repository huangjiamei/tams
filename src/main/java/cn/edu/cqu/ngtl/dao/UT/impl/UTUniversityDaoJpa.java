package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTUniversityDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTUniversity;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("UTUniversityDaoJpa")
public class UTUniversityDaoJpa implements UTUniversityDao {
	@Override
	public List<UTUniversity> getAllUniversities(){

		return KradDataServiceLocator.getDataObjectService().findAll(UTUniversity.class).getResults();

	}
	
	@Override
	public UTUniversity getEXMUniversityByUniversityCodeAndUniversityName(String code, String name) {

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(and(equal("code" , code),equal("name" , name)));

		QueryResults<UTUniversity> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTUniversity.class, criteria.build());

		return qr.getResults().isEmpty()?null:qr.getResults().get(0);

	}
	
	@Override
	public UTUniversity saveEXMUniversity(UTUniversity exmUniversity){

		return KradDataServiceLocator.getDataObjectService().save(exmUniversity);

	}
	
	@Override
	public void deleteEXMUniversity(UTUniversity exmUniversity){

		KradDataServiceLocator.getDataObjectService().delete(exmUniversity);

	}
	
	@Override
	public UTUniversity getIdByUniversityCode(String code){

		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(and(equal("code" , code)));

		QueryResults<UTUniversity> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				UTUniversity.class, criteria.build());

		return qr.getResults().isEmpty()?null:qr.getResults().get(0);

	}
}
