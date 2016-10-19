package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.viewobject.course.CourseTeacherViewObject;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAConverter {

    List<CourseTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informations);

}
