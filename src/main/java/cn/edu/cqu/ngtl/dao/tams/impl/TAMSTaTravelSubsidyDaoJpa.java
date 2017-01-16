package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaTravelSubsidyDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaTravelSubsidy;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.opensaml.xml.encryption.Q;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016/12/3.
 */

@Repository
@Component("TAMSTaTravelSubsidyDaoJpa")
public class TAMSTaTravelSubsidyDaoJpa implements TAMSTaTravelSubsidyDao {

    @Override
    public List<TAMSTaTravelSubsidy> getTAMSTaTravelSubsidyByStuIdAndTaId(String taId, String classId){

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("tamsTa.taClassId", classId),
                        equal("tamsTa.taId",taId)
                )
        );

        List<TAMSTaTravelSubsidy> results = KRADServiceLocator.getDataObjectService().findMatching(TAMSTaTravelSubsidy.class,criteria.build()).getResults();

        return results;
    }


    @Override
    public boolean insertOneByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy){

        return KRADServiceLocator.getDataObjectService().save(tamsTaTravelSubsidy)!=null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSTaTravelSubsidy tamsTaTravelSubsidy){
        KradDataServiceLocator.getDataObjectService().delete(tamsTaTravelSubsidy);
        return true;
    }

    @Override
    public boolean setTravelSubsidyToCertainStatus(String ta_id,String workflowStatusId){
        if (ta_id==null || workflowStatusId==null){
            return false;
        }
        QueryByCriteria.Builder criteria=QueryByCriteria.Builder.create().setPredicates(
                equal("tamsTaId",ta_id)
        );
        QueryResults<TAMSTaTravelSubsidy> qr=KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaTravelSubsidy.class,
                criteria.build()
        );
        if (qr.getResults()==null || qr.getResults().size()==0){
            return false;
        }

        TAMSTa ta=KradDataServiceLocator.getDataObjectService().find(TAMSTa.class,ta_id);
        ta.setTravelSubsidyStatusID(workflowStatusId);
        ta.setTravelSubsidy(String.valueOf(qr.getResults().size()*10));
        KradDataServiceLocator.getDataObjectService().save(ta);
        return true;
    }
}
