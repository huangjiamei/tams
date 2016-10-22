package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

}
