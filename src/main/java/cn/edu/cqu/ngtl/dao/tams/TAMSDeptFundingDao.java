package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-11-6.
 */
public interface TAMSDeptFundingDao {

    //List<TAMSDeptFunding> selectCurrBySession();

    //List<TAMSDeptFunding> selectPreBySession();

    List<TAMSDeptFunding> selectDepartmentCurrBySession();

    List<TAMSDeptFunding> selectDepartmentPreBySession();

    //List<TAMSDeptFunding> getDeptFundPreByCondition(Map<String, String> conditions);

    List<TAMSDeptFunding> selectDeptFundPreByCondition(Map<String, String> conditions);


    TAMSDeptFunding selectDeptFundsByDeptIdAndSession(Integer deptId,Integer sessionId);

    boolean saveOneByEntity(TAMSDeptFunding tamsDeptFunding);

    List<TAMSDeptFunding> selectDeptFundCurrByCondition(Map<String, String> conditions);

    TAMSDeptFunding selectDeptFundsByDeptId(Integer deptId);
}
