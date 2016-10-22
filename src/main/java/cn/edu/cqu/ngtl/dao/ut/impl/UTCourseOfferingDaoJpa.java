package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseOfferingDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourseOffering;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by 金祖增 on 2016/10/20.
 */
@Repository
@Component("UTCourseOfferingDaoJpa")
public class UTCourseOfferingDaoJpa implements UTCourseOfferingDao {

    @Override
    public UTCourseOffering getUTCourseOfferingByID(Integer id)
    {
        return KRADServiceLocator.getDataObjectService().find(UTCourseOffering.class, id);
    }

}
