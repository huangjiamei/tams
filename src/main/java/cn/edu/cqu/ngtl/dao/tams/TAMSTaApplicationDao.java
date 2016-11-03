package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;

import java.util.List;

/**
 * Created by tangjing on 16-10-23.
 */
public interface TAMSTaApplicationDao {

    boolean insertOne(TAMSTaApplication taApplication);

    List<TAMSTaApplication> selectByClassId(List<Object> classIds);
}
