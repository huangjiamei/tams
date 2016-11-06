package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
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

    @Override
    public List<UTSession> selectAll() {

        return KRADServiceLocator.getDataObjectService().findAll(UTSession.class).getResults();

    }

    @Override
    public boolean insertOneByEntity(UTSession session) {

        return KRADServiceLocator.getDataObjectService().save(session) != null;

    }

    @Override
    public UTSession selectByYearAndTerm(String year, String term) {

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
                .setPredicates(
                        and(
                                equal("year" , year),
                                equal("term", term)
                        )
                );
        QueryResults<UTSession> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTSession.class, criteria.build());

        return qr.getResults().size() != 0 ? qr.getResults().get(0) : null;
    }

    @Override
    public boolean updateOneByEntity(UTSession session) {

        return KRADServiceLocator.getDataObjectService().save(session) != null;

    }

    @Override
    public boolean deleteOneByEntity(UTSession session) {

        KradDataServiceLocator.getDataObjectService().delete(session);

        return true;
    }

    @Override
    public List<UTSession> selectByCondition(String termName, String startTime, String endTime) throws ParseException{
        String year = null;
        String term = null;
        List<UTSession> utSessions = new ArrayList<UTSession>();
        try {
            year = termName.substring(0, termName.indexOf("年"));
            term = termName.substring(termName.indexOf("年") + 1, termName.indexOf("年") + 2);
        }
        catch (StringIndexOutOfBoundsException e) {
            UTSession error = new UTSession();
            error.setYear("1000");
            utSessions.add(error);
            return utSessions;
        }
        // TODO: 2016/11/5 参数可以模糊化，后期调整 
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create()
                .setPredicates(
                        and(
                                equal("term", term),
                                equal("year", year)
                        )
                );

        QueryResults<UTSession> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTSession.class, criteria.build());

        SimpleDateFormat inFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = inFormat.parse(startTime);
        Date end = inFormat.parse(endTime);

        Integer num = qr.getResults().size();

        for (int i = 0; i < num; i++) {
            UTSession session = qr.getResults().get(i);
            if (begin.before(outFormat.parse(session.getBeginDate())) && end.after(outFormat.parse(session.getEndDate()))) {
                utSessions.add(qr.getResults().get(i));
            }
        }

        return utSessions;

    }
}
