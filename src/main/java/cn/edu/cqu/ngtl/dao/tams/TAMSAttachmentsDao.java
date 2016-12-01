package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;

import java.util.List;

/**
 * Created by tangjing on 16-11-29.
 */
public interface TAMSAttachmentsDao {
    boolean insertOneByEntity(TAMSAttachments attachment);

    List<TAMSAttachments> selectCalendarFilesByCalendarId(String calendarId);

    boolean deleteOneByEntity(TAMSAttachments attachment);

    TAMSAttachments selectById(String attachmentId);

    boolean updateByEntity(TAMSAttachments attachments);
}
