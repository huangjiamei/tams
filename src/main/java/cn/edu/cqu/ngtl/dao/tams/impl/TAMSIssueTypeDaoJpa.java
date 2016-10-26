package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSIssueTypeDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-10-26.
 */
@Repository
@Component("TAMSIssueTypeDaoJpa")
public class TAMSIssueTypeDaoJpa implements TAMSIssueTypeDao{

    @Override
    public List<TAMSIssueType> selectAll() {

        return KradDataServiceLocator.getDataObjectService().findAll(TAMSIssueType.class).getResults();

    }

    @Override
    public boolean insertOneByEntity(TAMSIssueType issueType) {

        return KRADServiceLocator.getDataObjectService().save(issueType) != null;

    }

    @Override
    public TAMSIssueType selectOneByTypeName(String typeName) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("typeName" , typeName));
        QueryResults<TAMSIssueType> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSIssueType.class,
                criteria.build()
        );

        return qr.getResults().isEmpty()?null:qr.getResults().get(0);
    }
}
