package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;

import java.util.List;

public interface KRIM_PERM_T_Dao {
	/**
	 * 获取namespace为kr-exm下所有的权限
	 * @return [权限列表]
	 */
	public List<KRIM_PERM_T> getAllPermissions();


	public void addPermissions(KRIM_PERM_T krim_perm_t);


	public void delPermissions(KRIM_PERM_T krim_perm_t);

	public KRIM_PERM_T findPermissionByName(String name);
}
