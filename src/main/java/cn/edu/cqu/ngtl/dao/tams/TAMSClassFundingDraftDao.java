package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016/11/25.
 */
public interface TAMSClassFundingDraftDao {


    List<TAMSClassFunding> selectAll();

    List<ClassFundingViewObject> selectClassFundDraftByCondition(Map<String, String> conditions);
}
