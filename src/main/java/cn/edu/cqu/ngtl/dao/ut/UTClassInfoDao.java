package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016-10-19.
 */
public interface UTClassInfoDao {

    List<UTClassInformation> getAllCurrentClassInformation();

    List<UTClassInformation> getAllCurrentClassInformationByDeptId(String departmentId);

    UTClassInformation getOneById(String id);

    List<UTClassInformation> selectBatchByIds(List<Object> classIds);

    List<UTClassInformation> selectByConditionsWithUid(Map<String, String> conditions,String uId);

    List<UTClassInformation> selectByConditions(Map<String, String> conditions);

    //List<UTClassInformation> selectAllCourseInfoByIds(List<Object> ids);
    UTClassInformation getOneByIdAndDept(String id, Integer dept);

    List<UTClassInformation> getAllCurrentClassInformationBySepStatus(String statusId);

    List<UTClassInformation> getClassesByCourseId(String courseId);
}
