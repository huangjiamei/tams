package cn.edu.cqu.ngtl.service.adminservice;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;

import java.util.List;

/**
 * Created by tangjing on 16-10-25.
 */
public interface IAdminService {

    List<CMCourseClassification> getAllClassification();

    boolean addOneOnlyWithName(String name);

    boolean changeNameById(Integer id, String name);

    boolean removeOneById(Integer id);


    List<TAMSCourseManager> getAllCourseManager();

}
