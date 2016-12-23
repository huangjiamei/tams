package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_MBR_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Repository
@Component("KRIM_ROLE_MBR_T_DaoJpa")
public class KRIM_ROLE_MBR_T_DaoJpa implements KRIM_ROLE_MBR_T_Dao {

	@Override
	public KRIM_ROLE_MBR_T saveKrimEntityEntTypT(KRIM_ROLE_MBR_T krimRoleMbrT) {
		// TODO Auto-generated method stub
		if(krimRoleMbrT.getMriTypCd()==null)
			krimRoleMbrT.setMriTypCd("P");
		return KradDataServiceLocator.getDataObjectService().save(krimRoleMbrT);
	}

	@Override
	public KRIM_ROLE_MBR_T getKrimEntityEntTypTByRoleIdAndMbrId(String roleId, String mbrId) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("roleId" , roleId),equal("mbrId",mbrId)));
		QueryResults<KRIM_ROLE_MBR_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_ROLE_MBR_T.class, criteria.build());
		return qr.getResults().isEmpty()?null:qr.getResults().get(0);
	}

	@Override
	public List<KRIM_ROLE_MBR_T> getKrimEntityEntTypTsByMbrId(String mbrId) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(equal("mbrId",mbrId));
		QueryResults<KRIM_ROLE_MBR_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_ROLE_MBR_T.class, criteria.build());
		return qr.getResults();
	}

	@Override
	public void saveKrimRoleMbrTByPrncpltAndRoles(KRIM_PRNCPL_T krimPrncplt, List<KRIM_ROLE_T> krimRoleTs) {
		// TODO Auto-generated method stub
		
		for(KRIM_ROLE_T krimRoleT:krimRoleTs){
			KRIM_ROLE_MBR_T krimRoleMbrT= this.getKrimEntityEntTypTByRoleIdAndMbrId(krimRoleT.getId(), krimPrncplt.getId());
			if(krimRoleT.getChecked()){
				//roleService.assignPermissionToRole(krimPermT.getId(), krimRoleT.getId());
				if(krimRoleMbrT==null){
					krimRoleMbrT = new KRIM_ROLE_MBR_T();
					krimRoleMbrT.setRoleId(krimRoleT.getId());
					krimRoleMbrT.setMbrId(krimPrncplt.getId());
					krimRoleMbrT = saveKrimEntityEntTypT(krimRoleMbrT);
				}
			}else{
				if(krimRoleMbrT!=null)
					this.delKrimRoleMbrT(krimRoleMbrT);
				}
			}
	}

	@Override
	public void delKrimRoleMbrT(KRIM_ROLE_MBR_T krimRoleMbrT) {
		// TODO Auto-generated method stub
		 KradDataServiceLocator.getDataObjectService().delete(krimRoleMbrT);
	}
}
