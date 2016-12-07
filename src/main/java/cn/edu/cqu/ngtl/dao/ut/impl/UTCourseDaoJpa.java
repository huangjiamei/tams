package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
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

    @Override
    public void InsertOneByEntity(UTCourse utCourse){
        KradDataServiceLocator.getDataObjectService().save(utCourse);
    }




}
