package cn.edu.cqu.ngtl.service.common;

import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;

import java.util.List;

/**
 * Created by tangjing on 16-11-29.
 */
public interface TamsFileControllerService {

    boolean saveCalendarAttachments(String uId, String classId, String calendarId, List<FileViewObject> fileList);

}
