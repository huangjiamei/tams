package cn.edu.cqu.ngtl.dao.krim;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;

/**
 * prncple包含一个用户的用户名密码等信息
 * @author Bill
 *
 */
public interface KRIM_PRNCPL_T_Dao {
	/**
	 * [getKrimEntityEntTypTByPrncplId description]
	 * @param  prncplId [description]
	 * @return          [description]
	 */
	public KRIM_PRNCPL_T getKrimEntityEntTypTByPrncplId(String prncplId);
	
	/**
	 * [createKrimPrncplT 新建一个prncple]
	 * @param  entId      [对应的实体ID（外键）]
	 * @param  prncplId   [id]
	 * @param  prncplNm   [用户名]
	 * @param  prncplPswd [密码]
	 * @param  actvInd    [活动'Y'，不活动'N']
	 * @return            [新建用户]
	 */
	public KRIM_PRNCPL_T createKrimPrncplT(String entId, String prncplId, String prncplNm, String prncplPswd, String actvInd);
	
	public KRIM_PRNCPL_T saveKrimPrncplT(KRIM_PRNCPL_T krimPrncplT);
	
	/**
	 * 根据用户名获取用户
	 * @param  prncplNm [用户名（唯一）]
	 * @return          [用户]
	 */
	public KRIM_PRNCPL_T getKrimEntityEntTypTByPrncplNm(String prncplNm);
}
