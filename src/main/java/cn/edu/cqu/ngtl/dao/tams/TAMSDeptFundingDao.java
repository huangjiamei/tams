package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;

import java.util.List;

/**
 * Created by tangjing on 16-11-6.
 */
public interface TAMSDeptFundingDao {

    List<TAMSDeptFunding> selectCurrBySession();

    List<TAMSDeptFunding> selectPreBySession();

    List<TAMSDeptFunding> selectDepartmentCurrBySession();

    List<TAMSDeptFunding> selectDepartmentPreBySession();

    List<TAMSClassFunding> selectAll();
}
