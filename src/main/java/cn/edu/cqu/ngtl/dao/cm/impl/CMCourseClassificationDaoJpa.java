package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMCourseClassificationDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by 金祖增 on 2016/10/21.
 */
@Repository
@Component("CMCourseClassificationDaoJpa")
public class CMCourseClassificationDaoJpa implements CMCourseClassificationDao{

    @Override
    public CMCourseClassification selectOneById(Integer id) {

        return KRADServiceLocator.getDataObjectService().find(CMCourseClassification.class, id);

    }

    @Override
    public List<CMCourseClassification> selectAll() {

        return KRADServiceLocator.getDataObjectService().findAll(CMCourseClassification.class).getResults();

    }

    @Override
    public boolean insertOneByEntity(CMCourseClassification courseClassification) {

        return KRADServiceLocator.getDataObjectService().save(courseClassification) != null;

    }

    @Override
    public boolean updateOneByEntity(CMCourseClassification courseClassification) {

        return KRADServiceLocator.getDataObjectService().save(courseClassification) != null;

    }

    @Override
    public boolean deleteOneByEntity(CMCourseClassification courseClassification) {

        KradDataServiceLocator.getDataObjectService().delete(courseClassification);

        return true;
    }

    @Override
    public CMCourseClassification selectOneByName(String name) {

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("name" , name));
        QueryResults<CMCourseClassification> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                CMCourseClassification.class,
                criteria.build()
        );

        return qr.getResults().isEmpty()?null:qr.getResults().get(0);

    }
}
