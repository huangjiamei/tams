package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
@Repository
@Component("TAMSWorkflowStatusDaoJpa")
public class TAMSWorkflowStatusDaoJpa implements TAMSWorkflowStatusDao {

    EntityManager em = KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Override
    public List<TAMSWorkflowStatus> selectAll() {
        List<TAMSWorkflowStatus> result = KradDataServiceLocator.getDataObjectService().findAll(TAMSWorkflowStatus.class).getResults();

        return result;
    }

    @Override
    public void unlock() {
        em.refresh(TAMSWorkflowStatus.class);
    }
}
