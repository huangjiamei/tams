package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassTaApplicationDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassTaApplication;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-26.
 */
@Repository
@Component("TAMSClassTaApplicationDaoJpa")
public class TAMSClassTaApplicationDaoJpa implements TAMSClassTaApplicationDao {

    @Autowired
    private UTSessionDao sessionDao;

    @Override
    public TAMSClassTaApplication selectByInstructorIdAndClassId(String instructorId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("applicantId", instructorId),
                        equal("applicationClassId", classId),
                        equal("sessionId", sessionDao.getCurrentSession().getId())
                )
        );
        QueryResults<TAMSClassTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassTaApplication.class,
                criteria.build()
        );
        return  qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }

    @Override
    public TAMSClassTaApplication insertOneByEntity(TAMSClassTaApplication classTaApplication) {
        TAMSClassTaApplication result = KradDataServiceLocator.getDataObjectService().save(classTaApplication);
        return result;
    }

    @Override
    public TAMSClassTaApplication selectByClassId(String classId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("applicationClassId", classId),
                        equal("sessionId", sessionDao.getCurrentSession().getId())
                )
        );
        QueryResults<TAMSClassTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassTaApplication.class,
                criteria.build()
        );
        return  qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }

    @Override
    public Map getAllClassAndHourAndFundsAndTaCounts(){
        Map result = new HashMap();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("sessionId", sessionDao.getCurrentSession().getId())
                )
        );
        QueryResults<TAMSClassTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSClassTaApplication.class,
                criteria.build()
        );
        List<TAMSClassTaApplication> tamsClassTaApplications = qr.getResults();
        if(tamsClassTaApplications!=null){
            for(TAMSClassTaApplication tamsClassTaApplication:tamsClassTaApplications){
                result.put(tamsClassTaApplication.getApplicationClassId(),
                        tamsClassTaApplication.getApplicationFunds() +
                        "," +
                        tamsClassTaApplication.getWorkHour() +
                        "," +
                        tamsClassTaApplication.getTaNumber()
                );
            }
            return result;
        }
        return null;
    }
}
