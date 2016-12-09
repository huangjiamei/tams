package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFundingDraft;

import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016/11/25.
 */
public interface TAMSDeptFundingDraftDao {

    List<TAMSDeptFunding> selectDepartmentCurrDraftBySession();

    TAMSDeptFundingDraft selectDeptDraftFundsByDeptIdAndSession(Integer deptId, Integer sessionId);

    boolean saveOneByEntity(TAMSDeptFundingDraft tamsDeptDraftFunding);

    List<TAMSDeptFunding> selectDeptFundDraftCurrByCondition(Map<String, String > conditions);



}
