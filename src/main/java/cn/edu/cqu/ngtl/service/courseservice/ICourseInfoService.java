package cn.edu.cqu.ngtl.service.courseservice;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ICourseInfoService {

    List<UTClassInformation> getAllCoursesMappedByDepartment();

}
