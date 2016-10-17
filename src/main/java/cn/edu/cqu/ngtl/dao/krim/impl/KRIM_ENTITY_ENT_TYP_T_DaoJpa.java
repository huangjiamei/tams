package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ENTITY_ENT_TYP_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_ENT_TYP_T;
import org.kuali.rice.krad.data.KradDataServiceLocator;

public class KRIM_ENTITY_ENT_TYP_T_DaoJpa implements KRIM_ENTITY_ENT_TYP_T_Dao {

	@Override
	public KRIM_ENTITY_ENT_TYP_T createKrimEntityEntTypTByEntityId(String entId) {
		// TODO Auto-generated method stub
		KRIM_ENTITY_ENT_TYP_T krimEntityEntTypT = new KRIM_ENTITY_ENT_TYP_T();
		krimEntityEntTypT.setEntTypCd("PERSON");
		krimEntityEntTypT.setActvInd("Y");
		krimEntityEntTypT.setEntId(entId);
		
		return this.saveKrimEntityEntTypT(krimEntityEntTypT);
	}

	
	@Override
	public KRIM_ENTITY_ENT_TYP_T saveKrimEntityEntTypT(KRIM_ENTITY_ENT_TYP_T krimEntityEntTypT) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(krimEntityEntTypT);
	}
	

}
