package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_PERM_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("KRIM_PERM_T_DaoJpa")
public class KRIM_PERM_T_DaoJpa implements KRIM_PERM_T_Dao {
	
	private static String nameSpace ="KR_TAMS";
	
	@Override
	public List<KRIM_PERM_T> getAllPermissions() {
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("namespaceCode" , nameSpace)));
		QueryResults<KRIM_PERM_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_PERM_T.class, criteria.build());
		return qr.getResults();
	}

	@Override
	public void addPermissions(KRIM_PERM_T krim_perm_t) {
		krim_perm_t.setNamespaceCode(nameSpace);
		KradDataServiceLocator.getDataObjectService().save(krim_perm_t);
	}


	@Override
	public void delPermissions(KRIM_PERM_T krim_perm_t){
		KradDataServiceLocator.getDataObjectService().delete(krim_perm_t);
	}


	@Override
	public KRIM_PERM_T findPermissionByName(String name){
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(equal("name" , name));
		return KRADServiceLocator.getDataObjectService().findUnique(KRIM_PERM_T.class,criteria.build());
	}
}
