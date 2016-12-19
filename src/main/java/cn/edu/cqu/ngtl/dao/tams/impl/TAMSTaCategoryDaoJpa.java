package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaCategoryDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-26.
 */
@Repository
@Component("TAMSTaCategoryDaoJpa")
public class TAMSTaCategoryDaoJpa implements TAMSTaCategoryDao {

    //查看所有助教
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

    @Override
    public TAMSTaCategory selectOneById(Integer id) {

        return KRADServiceLocator.getDataObjectService().find(TAMSTaCategory.class, id.toString());

    }

    @Override
    public boolean deleteOneByEntity(TAMSTaCategory tamsTaCategory) {

        KRADServiceLocator.getDataObjectService().delete(tamsTaCategory);

        return true;
    }

    @Override
    public Map getNameAndIdMap(){
        Map result = new HashMap();
        List<TAMSTaCategory> tamsTaCategories = this.selectAll();
        for(TAMSTaCategory tamsTaCategory:tamsTaCategories){
            result.put(tamsTaCategory.getId(),tamsTaCategory.getName());
        }
    return result;
    }
}
