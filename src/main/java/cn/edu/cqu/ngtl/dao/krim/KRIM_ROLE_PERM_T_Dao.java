package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;

import java.util.List;

/**
 * 角色权限关联表
 * @author Bill
 */
public interface KRIM_ROLE_PERM_T_Dao {
	public List<KRIM_ROLE_PERM_T> getKrimRolePermTsByRole(KRIM_ROLE_T krimRoleT);
	
	public KRIM_ROLE_PERM_T getKrimRolePermTByRoleAndPerm(KRIM_ROLE_T krimRoleT, KRIM_PERM_T krimPermT);
	
	public KRIM_ROLE_PERM_T saveKrimRolePermT(KRIM_ROLE_PERM_T krimRolePerm);
	
	public void saveKrimRolePermTByRoleAndPerms(KRIM_ROLE_T krimRoleT, List<KRIM_PERM_T> krimPermTs);
	
	public void delKrimRolePermT(KRIM_ROLE_PERM_T krimRolePerm);
}
