package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTOfferingConfigDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourseOfferingConfig;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awake on 2016/12/8.
 */

@Repository
@Component("UTOfferingConfigDaoJpa")
public class UTOfferingConfigDaoJpa implements UTOfferingConfigDao {

    @Override
    public void saveUTOfferingConfigByList(List<UTCourseOfferingConfig> utCourseOfferingConfigList){
        for(UTCourseOfferingConfig utCourseOfferingConfig:utCourseOfferingConfigList){
            KradDataServiceLocator.getDataObjectService().save(utCourseOfferingConfig);
        }
    }
}
