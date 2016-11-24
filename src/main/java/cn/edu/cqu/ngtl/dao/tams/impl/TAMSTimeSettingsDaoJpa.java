package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTimeSettingsDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-23.
 */
@Repository
@Component("TAMSTimeSettingsDaoJpa")
public class TAMSTimeSettingsDaoJpa implements TAMSTimeSettingsDao {

    @Autowired
    private UTSessionDao sessionDao;

    @Override
    public boolean insetOneByEntity(TAMSTimeSettings timeSetting) {
        String generatedId = KradDataServiceLocator.getDataObjectService().save(timeSetting).getId();
        return generatedId != null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSTimeSettings timeSetting) {
        try{
            KradDataServiceLocator.getDataObjectService().delete(timeSetting);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean updateOneByEntity(TAMSTimeSettings timeSetting) {
        try {
            KradDataServiceLocator.getDataObjectService().save(timeSetting);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TAMSTimeSettings selectByTypeId(String typeId) {
        Integer currSessionId = sessionDao.getCurrentSession().getId();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("timeSettingTypeId" , typeId),
                        equal("sessionId", currSessionId)
                )
        );
        QueryResults<TAMSTimeSettings> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTimeSettings.class, criteria.build());
        return qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }

    @Override
    public List<TAMSTimeSettings> selectAll() {
        return KradDataServiceLocator.getDataObjectService().findAll(TAMSTimeSettings.class).getResults();
    }
}
