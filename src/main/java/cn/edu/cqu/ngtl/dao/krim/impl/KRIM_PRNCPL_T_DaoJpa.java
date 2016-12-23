package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_PRNCPL_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("KRIM_PRNCPL_T_DaoJpa")
public class KRIM_PRNCPL_T_DaoJpa implements KRIM_PRNCPL_T_Dao {

	@Override
	public KRIM_PRNCPL_T saveKrimPrncplT(KRIM_PRNCPL_T krimPrncplT) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(krimPrncplT);
	}

	@Override
	public KRIM_PRNCPL_T createKrimPrncplT(String entId,String prncplId,String prncplNm,String prncplPswd,String actvInd) {
		// TODO Auto-generated method stub
		KRIM_PRNCPL_T krimPrncplT = new KRIM_PRNCPL_T();
		krimPrncplT.setId(prncplId);
		krimPrncplT.setPrncplNm(prncplNm);
		krimPrncplT.setPrncplPswd(prncplPswd);
		krimPrncplT.setActvInd(actvInd);
		krimPrncplT.setEntId(entId);
		return new KRIM_PRNCPL_T_DaoJpa().saveKrimPrncplT(krimPrncplT);
	}

	@Override
	public KRIM_PRNCPL_T getKrimEntityEntTypTByPrncplNm(String prncplNm) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(equal("prncplNm" , prncplNm));
		QueryResults<KRIM_PRNCPL_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_PRNCPL_T.class, criteria.build());
		return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}

	@Override
	public KRIM_PRNCPL_T getKrimEntityEntTypTByPrncplId(String prncplId) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().find(KRIM_PRNCPL_T.class, prncplId);
	}

}
