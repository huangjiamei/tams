package cn.edu.cqu.ngtl.service.UserService;

import cn.edu.cqu.ngtl.viewobject.course.CourseTeacherViewObject;

import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
public interface ITeacherService {

    List<CourseTeacherViewObject> getAllCoursesMappedByDepartment();

}
