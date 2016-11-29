package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSAttachmentsDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by tangjing on 16-11-29.
 */
@Repository
@Component("TAMSAttachmentsDaoJpa")
public class TAMSAttachmentsDaoJpa implements TAMSAttachmentsDao {

    @Override
    public boolean insertOneByEntity(TAMSAttachments attachment) {
        String id = KradDataServiceLocator.getDataObjectService().save(attachment).getId();
        return id != null;
    }
}
