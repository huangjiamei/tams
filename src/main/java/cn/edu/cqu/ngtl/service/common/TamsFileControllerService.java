package cn.edu.cqu.ngtl.service.common;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by tangjing on 16-11-29.
 */
public interface TamsFileControllerService {

    boolean saveCalendarAttachments(String uId, String classId, String calendarId, List<FileViewObject> fileList);

    boolean deleteOneAttachment(String uId, String classId, TAMSAttachments attachment);

    List<TAMSAttachments> getAllCalendarAttachments(String calendarId);

    void downloadCalendarFile(String classId, String calendarId, String fileName, HttpServletResponse response);
}
