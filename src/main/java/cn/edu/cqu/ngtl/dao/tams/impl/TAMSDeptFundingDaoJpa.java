package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSDeptFundingDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangjing on 16-11-6.
 */
@Repository
@Component("TAMSDeptFundingDaoJpa")
public class TAMSDeptFundingDaoJpa implements TAMSDeptFundingDao {

    @Override
    public List<TAMSDeptFunding> selectAll() {

        return KRADServiceLocator.getDataObjectService().findAll(TAMSDeptFunding.class).getResults();

    }
}
