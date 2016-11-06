package cn.edu.cqu.ngtl.dao.cm.impl;

import cn.edu.cqu.ngtl.dao.cm.CMProgramDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;


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

    @Override
    public List<CMProgram> selectByDepartmentId(String departmentId) {
        if(departmentId == null)
            return null;
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("departmentId" , departmentId));
        QueryResults<CMProgram> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                CMProgram.class,
                criteria.build()
        );
        return qr.getResults();
    }
}
