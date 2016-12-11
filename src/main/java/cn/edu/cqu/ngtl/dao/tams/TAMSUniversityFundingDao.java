package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSUniversityFunding;
import java.util.Map;
import java.util.List;
/**
 * Created by damei on 16/11/20.
 */
public interface TAMSUniversityFundingDao {
    //显示当前学期学校经费
    List<TAMSUniversityFunding> selectCurrBySession();
    //显示历史学校经费
    List<TAMSUniversityFunding> selectPreBySession();
    //过滤学校历史经费
    List<TAMSUniversityFunding> selectUniFundPreByCondition(Map<String, String> conditions);

    boolean insertOneByEntity(TAMSUniversityFunding tamsUniversityFunding);
}
