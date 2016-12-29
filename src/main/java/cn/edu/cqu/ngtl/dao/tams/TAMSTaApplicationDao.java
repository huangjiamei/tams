package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;

import java.util.List;

/**
 * Created by tangjing on 16-10-23.
 */
public interface TAMSTaApplicationDao {

    //以实体的形式插入一条数据
    boolean insertOne(TAMSTaApplication taApplication);

    List<TAMSTaApplication> selectByClassIds(List<Object> classIds);

    List<TAMSTaApplication> selectByClassId(String classId);

    List<TAMSTaApplication> selectByStuId(String classId);

    //ßßList<TAMSTaApplication> selectByClassIds(String classIds);

    TAMSTaApplication selectByStuIdAndClassId(String stuId, String classId);

    boolean deleteByEntity(TAMSTaApplication readyToRemove);

    void deleteBystuIdAndClassId(String stuId,String classId);
}
