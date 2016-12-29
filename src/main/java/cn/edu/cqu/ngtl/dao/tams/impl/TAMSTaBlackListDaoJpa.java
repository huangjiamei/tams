package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaBlackListDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaBlackList;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}
