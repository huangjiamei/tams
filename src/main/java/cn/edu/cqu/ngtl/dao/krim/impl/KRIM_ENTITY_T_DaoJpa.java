package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ENTITY_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_T;
import org.kuali.rice.krad.data.KradDataServiceLocator;

public class KRIM_ENTITY_T_DaoJpa implements KRIM_ENTITY_T_Dao {

	@Override
	public KRIM_ENTITY_T saveKrimEntityT(KRIM_ENTITY_T krimEntityT) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(krimEntityT);
	}

	@Override
	public KRIM_ENTITY_T createKrimEntityT() {
		// TODO Auto-generated method stub
		KRIM_ENTITY_T krimEntityT = new KRIM_ENTITY_T();
		krimEntityT.setActvInd("Y");
		return this.saveKrimEntityT(krimEntityT);
	}
	
	@Override
	public void delKrimEntityT(KRIM_ENTITY_T krim_ENTITY_T) {
		// TODO Auto-generated method stub
		KradDataServiceLocator.getDataObjectService().delete(krim_ENTITY_T);
		KradDataServiceLocator.getDataObjectService().flush(krim_ENTITY_T.getClass());
	}


	@Override
	public KRIM_ENTITY_T getKrimEntityTById(String id) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().find(KRIM_ENTITY_T.class, id);
	}

}
