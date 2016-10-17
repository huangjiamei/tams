package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;

import java.util.List;

/**
 * 角色用户关联表
 * @author Bill
 *
 */
public interface KRIM_ROLE_MBR_T_Dao {
	public KRIM_ROLE_MBR_T saveKrimEntityEntTypT(KRIM_ROLE_MBR_T krimRoleMbrT);
	
	public KRIM_ROLE_MBR_T getKrimEntityEntTypTByRoleIdAndMbrId(String roleId, String mbrId);
	
	public List<KRIM_ROLE_MBR_T> getKrimEntityEntTypTsByMbrId(String mbrId);
	
	//根据将krimRoleTs中checked保存，用户角色信息
	public void saveKrimRoleMbrTByPrncpltAndRoles(KRIM_PRNCPL_T krimPrncplt, List<KRIM_ROLE_T> krimRoleTs);
	
	public void delKrimRoleMbrT(KRIM_ROLE_MBR_T krimRoleMbrT);
}
