package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassApplyFeedbackDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassApplyFeedback;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016/12/15.
 */
@Repository
@Component("TAMSClassApplyFeedbackDaoJpa")
public class TAMSClassApplyFeedbackDaoJpa implements TAMSClassApplyFeedbackDao {

    @Override
    public List<TAMSClassApplyFeedback> getFbByClassId(String classId){

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                equal("classId", classId)
        );
        QueryResults<TAMSClassApplyFeedback> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassApplyFeedback.class,
                criteria.build()
        );
        return qr.getResults();
    }

    @Override
    public boolean saveFbByEntity(TAMSClassApplyFeedback tamsClassApplyFeedback){
        return KradDataServiceLocator.getDataObjectService().save(tamsClassApplyFeedback)==null;
    }

}
