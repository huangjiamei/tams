package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class KRIM_ROLE_T_DaoJpa implements KRIM_ROLE_T_Dao {

	private static String  nameSpace = "KR_EXM";
	@Override
	public List<KRIM_ROLE_T> getAllKrimRoleTs() {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("namespaceCode" , nameSpace)));
		QueryResults<KRIM_ROLE_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_ROLE_T.class, criteria.build());
		return qr.getResults().isEmpty()?null:qr.getResults();
	}

	@Override
	public KRIM_ROLE_T getKrimRoleTByName(String name) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(
						equal("namespaceCode" , nameSpace)
						,equal("name" , name)
						));
		KRIM_ROLE_T qr = KradDataServiceLocator.getDataObjectService().findUnique(
				KRIM_ROLE_T.class, criteria.build());
		return qr;
	}

	@Override
	public KRIM_ROLE_T saveKrimRoleT(KRIM_ROLE_T krimRoleT) {
		// TODO Auto-generated method stub
		if(krimRoleT.getNamespaceCode()==null)
			krimRoleT.setNamespaceCode(nameSpace);
		if(krimRoleT.getKimTypeId()==null)
			krimRoleT.setKimTypeId("1");
		return KradDataServiceLocator.getDataObjectService().save(krimRoleT);
	}

}
