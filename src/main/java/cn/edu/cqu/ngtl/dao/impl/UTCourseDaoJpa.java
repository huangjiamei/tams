package cn.edu.cqu.ngtl.dao.impl;

import cn.edu.cqu.ngtl.dao.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.UTCourse;
import org.kuali.rice.krad.data.KradDataServiceLocator;

/**
 * Created by tangjing on 16-10-13.
 */
public class UTCourseDaoJpa implements UTCourseDao {

    @Override
    public UTCourse selectOneById(Integer Id) {

        return KradDataServiceLocator.getDataObjectService().find(UTCourse.class, Id);

    }

}
