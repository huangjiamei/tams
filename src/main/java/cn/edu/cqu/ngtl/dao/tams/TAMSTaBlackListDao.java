package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaBlackList;

import java.util.List;

/**
 * Created by awake on 2016/12/29.
 */
public interface TAMSTaBlackListDao {

    List<TAMSTaBlackList> getAllBlackList();

    boolean insertOneByEntity(TAMSTaBlackList tamsTaBlackList);
    boolean deleteOneByEntity(TAMSTaBlackList blacklist);


}
