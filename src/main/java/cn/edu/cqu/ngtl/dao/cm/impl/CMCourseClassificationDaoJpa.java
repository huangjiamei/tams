package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import org.kuali.rice.krad.data.KradDataServiceLocator;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public class CMCourseClassificationDaoJpa implements CMCourseClassificationDao{

    @Override
    public CMCourseClassification selectOneById(Integer id) {
        return KradDataServiceLocator.getDataObjectService().find(CMCourseClassification.class, id);
    }
}
