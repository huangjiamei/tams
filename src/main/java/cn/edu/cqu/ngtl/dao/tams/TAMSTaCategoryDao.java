package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-26.
 */




public interface TAMSTaCategoryDao {

    List<TAMSTaCategory> selectAll();

    TAMSTaCategory selectOneByName(String name);

    boolean insertOneByEntity(TAMSTaCategory newTaCategory);

    boolean updateOneByEntity(TAMSTaCategory tamsTaCategory);

    TAMSTaCategory selectOneById(Integer id);

    boolean deleteOneByEntity(TAMSTaCategory tamsTaCategory);

    Map getNameAndIdMap();
}
