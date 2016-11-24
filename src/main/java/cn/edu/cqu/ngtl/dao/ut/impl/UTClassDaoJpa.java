package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by tangjing on 16-10-15.
 */
@Repository
@Component("UTClassDaoJpa")
public class UTClassDaoJpa implements UTClassDao {

    @Override
    public UTClass selectByClassId(Integer id) {
        return KradDataServiceLocator.getDataObjectService().find(UTClass.class, id);
    }
}
