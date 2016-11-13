package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;

import java.util.List;

/**
 * Created by tangjing on 16-11-1.
 */
public interface TAMSTaDao {

    List<TAMSTa> selectAll();

    List<TAMSTa> selectByClassId(List<Object> classIds);

    List<Object> selectClassIdsByStudentId(String uId);

    List<TAMSTa> selectBatchByIds(List<String> ids);

    boolean updateByEntity(TAMSTa ta);
}
