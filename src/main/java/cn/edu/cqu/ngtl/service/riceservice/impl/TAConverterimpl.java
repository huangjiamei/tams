package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.viewobject.course.CourseTeacherViewObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAConverterimpl implements ITAConverter {

    @Override
    public List<CourseTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informationlist) {

        List<CourseTeacherViewObject> viewObjects = new ArrayList<>(informationlist.size());

        for (UTClassInformation information : informationlist) {
            CourseTeacherViewObject viewObject = new CourseTeacherViewObject();

            //if(clazz.getUtInstructors() != null && clazz.getUtInstructors().size() != 0)
            viewObject.setInstructorName("test");

            viewObject.setClassNumber(information.getClassNumber());

            viewObject.setDepartmentName(information.getDeptName());
            viewObject.setCourseName(information.getCourseName());
            viewObject.setCourseHour(information.getHour());
            viewObject.setCourseCode(information.getCourseCode());
            viewObject.setCourseCredit(information.getCredit().toString());

            //if (programCourse != null) {
            viewObject.setCourseClassification("test");
            viewObject.setIsRequired("必修");
            viewObject.setProgramName("CS");
            //}
            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

}
