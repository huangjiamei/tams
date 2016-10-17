package cn.edu.cqu.ngtl.dao.krim.impl;

import cn.edu.cqu.ngtl.dao.krim.KRIM_ROLE_PERM_T_Dao;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.data.KradDataServiceLocator;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class KRIM_ROLE_PERM_T_DaoJpa implements KRIM_ROLE_PERM_T_Dao {

	@Override
	public List<KRIM_ROLE_PERM_T> getKrimRolePermTsByRole(KRIM_ROLE_T krimRoleT) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(equal("roleId" , krimRoleT.getId())
						,equal("active" , true)
								));
		QueryResults<KRIM_ROLE_PERM_T> qr = KradDataServiceLocator.getDataObjectService().findMatching(
				KRIM_ROLE_PERM_T.class, criteria.build());
		return qr.getResults();
	}

	@Override
	public KRIM_ROLE_PERM_T saveKrimRolePermT(KRIM_ROLE_PERM_T krimRolePerm) {
		// TODO Auto-generated method stub
		return KradDataServiceLocator.getDataObjectService().save(krimRolePerm);
	}

	@Override
	public KRIM_ROLE_PERM_T getKrimRolePermTByRoleAndPerm(KRIM_ROLE_T krimRoleT, KRIM_PERM_T krimPermT) {
		// TODO Auto-generated method stub
		QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
				.setPredicates(and(
						 equal("roleId" , krimRoleT.getId())
						,equal("permissionId" , krimPermT.getId())
						));
		KRIM_ROLE_PERM_T qr = KradDataServiceLocator.getDataObjectService().findUnique(
				KRIM_ROLE_PERM_T.class, criteria.build());
		return qr;
	}

	@Override
	public void delKrimRolePermT(KRIM_ROLE_PERM_T krimRolePerm) {
		// TODO Auto-generated method stub
		KradDataServiceLocator.getDataObjectService().delete(krimRolePerm);
	}

	@Override
	public void saveKrimRolePermTByRoleAndPerms(KRIM_ROLE_T krimRoleT, List<KRIM_PERM_T> krimPermTs) {
		// TODO Auto-generated method stub
		RoleService roleService = KimApiServiceLocator.getRoleService();
		
		for(KRIM_PERM_T krimPermT:krimPermTs){
			KRIM_ROLE_PERM_T krimRolePermT= getKrimRolePermTByRoleAndPerm(krimRoleT, krimPermT);
			if(krimPermT.getChecked()){
				//roleService.assignPermissionToRole(krimPermT.getId(), krimRoleT.getId());
				if(krimRolePermT==null){
					krimRolePermT = new KRIM_ROLE_PERM_T();
					krimRolePermT.setRoleId(krimRoleT.getId());
					krimRolePermT.setPermissionId(krimPermT.getId());
					krimRolePermT.setActive(true);
					krimRolePermT = saveKrimRolePermT(krimRolePermT);
				}else{
					if(!krimRolePermT.getActive()){
						krimRolePermT.setActive(true);
						this.saveKrimRolePermT(krimRolePermT);
					}
				}
			}else{
				if(krimRolePermT!=null&&krimRolePermT.getActive()){
					krimRolePermT.setActive(false);
					this.saveKrimRolePermT(krimRolePermT);
					//this.delKrimRolePermT(krimRolePermT);
					//roleService.revokePermissionFromRole(krimPermT.getId(), krimRoleT.getId());
				}
			}
		}
	}
}
