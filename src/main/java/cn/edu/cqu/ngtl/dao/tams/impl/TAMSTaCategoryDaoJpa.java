package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaCategoryDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-26.
 */
@Repository
@Component("TAMSTaCategoryDaoJpa")
public class TAMSTaCategoryDaoJpa implements TAMSTaCategoryDao {

    @Override
    public List<TAMSTaCategory> selectAll() {

        return KRADServiceLocator.getDataObjectService().findAll(TAMSTaCategory.class).getResults();

    }

    @Override
    public boolean insertOneByEntity(TAMSTaCategory newTaCategory) {

        return KRADServiceLocator.getDataObjectService().save(newTaCategory) != null;

    }

    @Override
    public TAMSTaCategory selectOneByName(String name) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("name" , name));
        QueryResults<TAMSTaCategory> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaCategory.class,
                criteria.build()
        );

        return qr.getResults().isEmpty()?null:qr.getResults().get(0);
    }

    @Override
    public boolean updateOneByEntity(TAMSTaCategory tamsTaCategory) {

        return KRADServiceLocator.getDataObjectService().save(tamsTaCategory) != null;

    }
}
