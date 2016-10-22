package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Repository
@Component("CMCourseClassificationDaoJpa")
public class CMCourseClassificationDaoJpa implements CMCourseClassificationDao{

    @Override
    public CMCourseClassification selectOneById(Integer id) {
        return KradDataServiceLocator.getDataObjectService().find(CMCourseClassification.class, id);
    }
}
