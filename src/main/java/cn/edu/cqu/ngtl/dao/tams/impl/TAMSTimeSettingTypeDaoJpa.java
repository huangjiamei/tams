package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTimeSettingTypeDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettingType;
import org.kuali.rice.core.api.criteria.OrderByField;
import org.kuali.rice.core.api.criteria.OrderDirection;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-23.
 */
@Repository
@Component("TAMSTimeSettingTypeDaoJpa")
public class TAMSTimeSettingTypeDaoJpa implements TAMSTimeSettingTypeDao {

    @Override
    public List<TAMSTimeSettingType> selectAll() {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create();
        OrderByField orderByField = OrderByField.Builder.create("id", OrderDirection.DESCENDING).build();
        criteria.setOrderByFields(orderByField);

        QueryResults<TAMSTimeSettingType> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTimeSettingType.class, criteria.build());
        return qr.getResults().isEmpty() ? null : qr.getResults();

    }

    @Override
    public boolean insertOneByEntity(TAMSTimeSettingType settingType) {
        String generatedId = KradDataServiceLocator.getDataObjectService().save(settingType).getId();
        return generatedId != null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSTimeSettingType settingType) {
        try{
            KradDataServiceLocator.getDataObjectService().delete(settingType);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOneByEntity(TAMSTimeSettingType settingType) {
        try {
            KradDataServiceLocator.getDataObjectService().save(settingType);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TAMSTimeSettingType selectByName(String typeName) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create();
        OrderByField orderByField = OrderByField.Builder.create("id", OrderDirection.DESCENDING).build();
        criteria.setOrderByFields(orderByField);
        criteria.setPredicates(
                equal("typeName", typeName)
        );
        QueryResults<TAMSTimeSettingType> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTimeSettingType.class, criteria.build());
        return qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }
}
