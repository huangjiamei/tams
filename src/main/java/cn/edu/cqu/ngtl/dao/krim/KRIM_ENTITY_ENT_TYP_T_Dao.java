package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_ENT_TYP_T;

/**
 *
 * @author Bill
 *
 */
public interface KRIM_ENTITY_ENT_TYP_T_Dao {

	public KRIM_ENTITY_ENT_TYP_T createKrimEntityEntTypTByEntityId(String entId);
	
	public KRIM_ENTITY_ENT_TYP_T saveKrimEntityEntTypT(KRIM_ENTITY_ENT_TYP_T krimEntityEntTypT);
	
}
