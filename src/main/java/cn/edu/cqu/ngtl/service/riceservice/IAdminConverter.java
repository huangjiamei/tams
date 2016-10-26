package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;

import java.util.List;

/**
 * Created by awake on 2016-10-26.
 */
public interface IAdminConverter {

    List<CourseManagerViewObject> getCourseManagerToTableViewObject(List<TAMSCourseManager> tamsCourseManagerList);

}
