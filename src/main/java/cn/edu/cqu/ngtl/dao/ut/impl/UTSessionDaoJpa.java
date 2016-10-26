package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by 金祖增 on 2016/10/21.
 */
@Repository
@Component("UTSessionDaoJpa")
public class UTSessionDaoJpa implements UTSessionDao{

    @Override
    public UTSession getUTSessionById(Integer id)
    {
        return KRADServiceLocator.getDataObjectService().find(UTSession.class, id);
    }

    @Override
    public UTSession getCurrentSession(){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
                .setPredicates(equal("active" , "Y"));
        QueryResults<UTSession> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTSession.class, criteria.build());
        return qr.getResults().isEmpty()?null:qr.getResults().get(0);
    }



    @Override
    public UTSession setCurrentSession(UTSession utSession){
        if(utSession.getActive().equals("Y")){
            return utSession;
        }
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
                .setPredicates(equal("active" , "Y"));
        QueryResults<UTSession> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTSession.class, criteria.build());
        for(UTSession curUTSession: qr.getResults()){
            curUTSession.setActive("N");
            KradDataServiceLocator.getDataObjectService().save(curUTSession);
        }
        utSession.setActive("Y");
        return KradDataServiceLocator.getDataObjectService().save(utSession);
    }


}
