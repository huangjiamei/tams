package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMProgramDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * Created by 金祖增 on 2016/10/21.
 */

@Repository
@Component("CMProgramDaoJpa")
public class CMProgramDaoJpa implements CMProgramDao{

    @Override
    public CMProgram getCMProgramById(Integer id)
    {
        return KRADServiceLocator.getDataObjectService().find(CMProgram.class, id);
    }
}
