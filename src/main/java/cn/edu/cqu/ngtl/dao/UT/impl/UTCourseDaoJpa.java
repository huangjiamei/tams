package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;
import org.kuali.rice.krad.data.KradDataServiceLocator;

/**
 * Created by tangjing on 16-10-13.
 */
public class UTCourseDaoJpa implements UTCourseDao {

    @Override
    public UTCourse selectOneById(Integer id) {

        return KradDataServiceLocator.getDataObjectService().find(UTCourse.class, id);

    }

}
