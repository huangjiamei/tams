package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;

import java.util.List;

/**
 * Created by awake on 2016/12/3.
 */
public interface TAMSTaTravelSubsidyDao {


    List<TAMSTaTravelSubsidy> getTAMSTaTravelSubsidyByStuIdAndTaId(String taId,String classId);

    boolean insertOneByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy);

    boolean deleteOneByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy);
}
