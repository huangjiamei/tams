package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTClassDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTClass;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-15.
 */
@Repository
@Component("UTClassDaoJpa")
public class UTClassDaoJpa implements UTClassDao {

    @Override
    public List<UTClass> selectAllMappedByDepartment() {

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("minPerWeek" , 90));
        QueryResults<UTClass> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTClass.class,
                criteria.build()
        );

        return qr.getResults().subList(0, 20);

    }

}
