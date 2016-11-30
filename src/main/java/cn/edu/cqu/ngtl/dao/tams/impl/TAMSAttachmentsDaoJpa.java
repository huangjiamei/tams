package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSAttachmentsDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-29.
 */
@Repository
@Component("TAMSAttachmentsDaoJpa")
public class TAMSAttachmentsDaoJpa implements TAMSAttachmentsDao {

    private static final String MODULE_CALENDAR = "CalendarFiles";

    @Override
    public boolean insertOneByEntity(TAMSAttachments attachment) {
        String id = KradDataServiceLocator.getDataObjectService().save(attachment).getId();
        return id != null;
    }

    @Override
    public List<TAMSAttachments> selectCalendarFilesByCalendarId(String calendarId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("containerId", calendarId),
                        equal("containerType", MODULE_CALENDAR)
                )
        );
        QueryResults<TAMSAttachments> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSAttachments.class,
                criteria.build()
        );
        return null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSAttachments attachment) {
        try {
            KradDataServiceLocator.getDataObjectService().delete(attachment);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
