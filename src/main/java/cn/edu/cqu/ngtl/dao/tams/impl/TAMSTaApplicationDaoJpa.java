package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaApplicationDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-23.
 */
@Repository
@Component("TAMSTaApplicationDaoJpa")
public class TAMSTaApplicationDaoJpa implements TAMSTaApplicationDao {

    @Override
    public boolean insertOne(TAMSTaApplication taApplication) {

        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();

        taApplication.setSessionId(curSession.getId());

        //添加到相应的申请的classid的课程上
        //taApplication.setApplicationClassId(classid);

        KRADServiceLocator.getDataObjectService().save(taApplication);

        return true;
    }

    //根据课程找出相应的申请助教
    @Override
    public List<TAMSTaApplication> selectByClassIds(List<Object> classIds) {
        List<TAMSTaApplication> tas = new ArrayList<>();

        for(Object classId : classIds) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("applicationClassId", classId),
                            equal("sessionId", new UTSessionDaoJpa().getCurrentSession().getId())
                    )
            );
            QueryResults<TAMSTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTaApplication.class,
                    criteria.build()
            );
            tas.addAll(qr.getResults());
        }

        return tas.isEmpty()?null:tas;
    }

    //只需根据classid查询所有助教
    @Override
    public List<TAMSTaApplication> selectByClassId(String classId){
        List<TAMSTaApplication> tas = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClassId", classId),
                        equal("sessionId",curSession.getId())
                )
        );
        QueryResults<TAMSTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaApplication.class,
                criteria.build()
        );
        tas.addAll(qr.getResults());
        return tas.isEmpty()?null:tas;
    }

    @Override
    public TAMSTaApplication selectByStuIdAndClassId(String stuId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("applicationClassId", classId),
                        equal("applicationId", stuId),
                        equal("sessionId", new UTSessionDaoJpa().getCurrentSession().getId())
                )
        );
        QueryResults<TAMSTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaApplication.class,
                criteria.build()
        );
        return qr.getResults().get(0);
    }

    @Override
    public boolean deleteByEntity(TAMSTaApplication application) {
        KradDataServiceLocator.getDataObjectService().delete(application);
        return true;
    }

/*
    @Override
    public List<TAMSTaApplication> selectByClassId(String classId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("applicationClassId", classId)
                )
        );
        QueryResults<TAMSTaApplication> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaApplication.class,
                criteria.build()
        );
        return qr.getResults();
    }
    */
}
