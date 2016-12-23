package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;

import java.util.List;
import java.util.Map;
/**
 * Created by awake on 2016-10-26.
 */
public interface TAMSCourseManagerDao {


     List<TAMSCourseManager> getAllCourseManager();

     TAMSCourseManager getCourseManagerByInstructorId(String instructorId);

     void saveCourseManager(TAMSCourseManager tamsCourseManager);

     void deleteCourseManager(TAMSCourseManager tamsCourseManager);

     List<TAMSCourseManager> selectCourseManagerByCondition(Map<String, String> conditions);

}
