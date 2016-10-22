package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by 金祖增 on 2016/10/21.
 */
@Repository
@Component("CMCourseClassificationDaoJpa")
public class CMCourseClassificationDaoJpa implements CMCourseClassificationDao{

    @Override
    public CMCourseClassification getCMCourseClassficationById(Integer id) {

        return KRADServiceLocator.getDataObjectService().find(CMCourseClassification.class, id);

    }

}
