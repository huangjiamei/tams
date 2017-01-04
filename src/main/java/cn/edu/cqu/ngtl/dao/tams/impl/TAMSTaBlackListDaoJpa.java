package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaBlackListDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaBlackList;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016/12/29.
 */
@Repository
@Component("TAMSTaBlackListDaoJpa")
public class TAMSTaBlackListDaoJpa implements TAMSTaBlackListDao {

    @Override
    public List<TAMSTaBlackList> getAllBlackList(){
        List<TAMSTaBlackList> result = new ArrayList<>();
        result = KradDataServiceLocator.getDataObjectService().findAll(TAMSTaBlackList.class).getResults();
        return result;
    }

    @Override
    public boolean insertOneByEntity(TAMSTaBlackList tamsTaBlackList){
        return KradDataServiceLocator.getDataObjectService().save(tamsTaBlackList)!=null;
    }

    @Override
    public TAMSTaBlackList getBlackListByStuId(String stuId){
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("taId" , stuId));
        QueryResults<TAMSTaBlackList> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTaBlackList.class,
                criteria.build()
        );
        return qr.getResults().isEmpty() ? null : qr.getResults().get(0);
    }

    @Override
    public void deleteFromBlackList(TAMSTaBlackList tamsTaBlackList){
         KradDataServiceLocator.getDataObjectService().delete(tamsTaBlackList);
    }
}
