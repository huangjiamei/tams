package cn.edu.cqu.ngtl.dao.UT.impl;

import cn.edu.cqu.ngtl.dao.UT.UTClassDao;
import cn.edu.cqu.ngtl.dataobject.UT.UTClass;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.DataObjectService;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
@Repository
@Component("UTClassDaoJpa")
public class UTClassDaoJpa implements UTClassDao {

    @Override
    public List<UTClass> selectAllMappedByDepartment() {


        DataObjectService s = KradDataServiceLocator.getDataObjectService();
        UTClass qr1 = s.find(UTClass.class, new Integer(290739));
        QueryResults<UTClass> qr = s.findAll(UTClass.class);

        return qr.getResults();

    }

}
