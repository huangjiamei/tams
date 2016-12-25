package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016/11/25.
 */
public interface TAMSClassFundingDao {

    List<TAMSClassFunding> selectAll(User user);
    List<ClassFundingViewObject> selectClassFundByCondition(Map<String, String> conditions);

    TAMSClassFunding getOneByClassIdAndSessionId(String classId,String SessionId);

    boolean saveOneByEntity(TAMSClassFunding tamsClassFunding);

    TAMSClassFunding getOneByClassId(String classId);

    List<TAMSClassFunding> selectByClassIds(List<String> classids);
}
