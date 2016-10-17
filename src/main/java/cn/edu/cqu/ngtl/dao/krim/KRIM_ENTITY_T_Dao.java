package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_T;

/**
 * rice 每个person必须对应一个实体
 * 新建person前应该先新建一个实体
 * @author Bill
 *
 */
public interface KRIM_ENTITY_T_Dao {
	/**
	 * [saveKrimEntityT 保存或者更新一实体，如果新建一实体用createKrimEntityT方法]
	 * @param  krimEntityT [待更新实体]
	 * @return             [更新后实体]
	 */
	public KRIM_ENTITY_T saveKrimEntityT(KRIM_ENTITY_T krimEntityT);
	
	/**
	 * [createKrimEntityT 新建一实体，并设置为活动状态（active='Y',不然不可用）]
	 * @return [新建实体]
	 */
	public KRIM_ENTITY_T createKrimEntityT();
	
	public void delKrimEntityT(KRIM_ENTITY_T krim_ENTITY_T); 
	
	public KRIM_ENTITY_T getKrimEntityTById(String id);
}
