package cn.edu.cqu.ngtl.service.adminservice;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;

import java.util.List;

/**
 * Created by tangjing on 16-10-25.
 */
public interface IAdminService {

    List<CMCourseClassification> getAllClassification();

    boolean addCourseClassificationOnlyWithName(String name);

    boolean changeCourseClassificationNameById(Integer id, String name);

    boolean removeCourseClassificationById(Integer id);

}
