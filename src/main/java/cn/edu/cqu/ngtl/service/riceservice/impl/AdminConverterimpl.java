package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.dao.ut.impl.UTCourseDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.service.riceservice.IAdminConverter;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-26.
 */
@Service
public class AdminConverterimpl implements IAdminConverter {
    @Autowired
    private UTCourseDaoJpa utCourseDao;



    @Override
    public List<CourseManagerViewObject> getCourseManagerToTableViewObject(List<TAMSCourseManager> tamsCourseManagerList) {
        List<CourseManagerViewObject> courseManagerViewObjectList = new ArrayList(tamsCourseManagerList.size());
        for (TAMSCourseManager tamsCourseManager : tamsCourseManagerList) {
            UTCourse utCourse = new UTCourseDaoJpa().selectOneById(tamsCourseManager.getCourseId());
            if(utCourse!=null) {
                CourseManagerViewObject courseManagerViewObject = new CourseManagerViewObject();
                courseManagerViewObject.setId(tamsCourseManager.getCourseManagerId());
                courseManagerViewObject.setCourseNm(utCourse.getName());
                courseManagerViewObject.setCourseNmb(utCourse.getCodeR());
                courseManagerViewObject.setCourseManager(tamsCourseManager.getUtInstructor().getName());
                courseManagerViewObject.setInstructorCode(tamsCourseManager.getUtInstructor().getCode());
                courseManagerViewObjectList.add(courseManagerViewObject);
            }
        }

        return  courseManagerViewObjectList;
    }









}
