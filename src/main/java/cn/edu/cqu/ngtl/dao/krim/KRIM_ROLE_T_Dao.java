package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;

import java.util.List;

/**
 *
 * @author Bill
 *
 */
public interface KRIM_ROLE_T_Dao {
	public  List<KRIM_ROLE_T>  getAllKrimRoleTs();
	
	public KRIM_ROLE_T getKrimRoleTByName(String name);
	
	public KRIM_ROLE_T saveKrimRoleT(KRIM_ROLE_T krimRoleT);
}
