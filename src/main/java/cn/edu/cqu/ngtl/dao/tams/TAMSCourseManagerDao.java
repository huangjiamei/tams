package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;

import java.util.List;

/**
 * Created by awake on 2016-10-26.
 */
public interface TAMSCourseManagerDao {


    public List<TAMSCourseManager> getAllCourseManager();

    public TAMSCourseManager getCourseManagerByInstructorId(String instructorId);

    public void saveCourseManager(TAMSCourseManager tamsCourseManager);

    public void deleteCourseManager(TAMSCourseManager tamsCourseManager);

}
