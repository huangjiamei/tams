package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTStudentTimetableDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudentTimetable;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;


/**
 * Created by awake on 2016/12/12.
 */

@Repository
@Component("UTStudentTimetableDaoJpa")
public class UTStudentTimetableDaoJpa implements UTStudentTimetableDao {

    @Override
    public void insertOneByEntity(UTStudentTimetable utStudentTimetable){
        KradDataServiceLocator.getDataObjectService().save(utStudentTimetable);
    }

    @Override
    public void insertOneByEntityList(List<UTStudentTimetable> utStudentTimetables){
        int i = 0;
        for(UTStudentTimetable utStudentTimetable :utStudentTimetables) {
            KradDataServiceLocator.getDataObjectService().save(utStudentTimetable);
        }
    }

    @Override
    public List<UTStudentTimetable> getStudentTimetableByUid(String uId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("studentId", uId)
        );

        QueryResults<UTStudentTimetable> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                UTStudentTimetable.class,
                criteria.build()
        );
        return  qr.getResults().isEmpty() ? null : qr.getResults();
    }
}
