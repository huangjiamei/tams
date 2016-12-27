package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassEvaluationDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassEvaluation;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-26.
 */
@Repository
@Component("TAMSClassEvaluationDaoJpa")
public class TAMSClassEvaluationDaoJpa implements TAMSClassEvaluationDao {

    @Override
    public void deleteAllByClassId(String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("classId", classId)
                )
        );
        KradDataServiceLocator.getDataObjectService().deleteMatching(TAMSClassEvaluation.class, criteria.build());
    }

    @Override
    public boolean insertOneByEntity(TAMSClassEvaluation classEvaluation) {
        try {
            return KradDataServiceLocator.getDataObjectService().save(classEvaluation).getId()!=null;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<TAMSClassEvaluation> getAllByClassId(String classId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("classId", classId)
                )
        );
        QueryResults<TAMSClassEvaluation> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassEvaluation.class,
                criteria.build()
        );
        return qr.getResults()==null?null:(qr.getResults().size()==0?null:qr.getResults());

    }

    @Override
    public void deleteByEntity(TAMSClassEvaluation tamsClassEvaluation){
        KradDataServiceLocator.getDataObjectService().delete(tamsClassEvaluation);
    }
}
