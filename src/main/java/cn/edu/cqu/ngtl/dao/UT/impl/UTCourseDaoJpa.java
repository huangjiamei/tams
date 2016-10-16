package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTCourse;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangjing on 16-10-13.
 */
@Repository
@Component("UTCourseDaoJpa")
public class UTCourseDaoJpa implements UTCourseDao {

    @Override
    public UTCourse selectOneById(Integer id) {

        return KradDataServiceLocator.getDataObjectService().find(UTCourse.class, id);

    }

    @Override
    public List<UTCourse> selectAllMappedByDepartment() {

        return KradDataServiceLocator.getDataObjectService().findAll(UTCourse.class).getResults();

    }

}
