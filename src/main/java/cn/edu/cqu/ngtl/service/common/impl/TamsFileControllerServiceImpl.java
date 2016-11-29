package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSAttachmentsDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;
import cn.edu.cqu.ngtl.service.common.TamsFileControllerService;
import cn.edu.cqu.ngtl.tools.utils.FileUtils;
import cn.edu.cqu.ngtl.tools.utils.MD5Encryption;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
import org.kuali.rice.krad.file.FileMeta;
import org.kuali.rice.krad.file.FileMetaBlob;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.FileControllerServiceImpl;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hasee on 2016/11/24.
 * 多文件上传之后，点击文件名(href)会调用getFileFromLine方法，该方法最终调用此service的sendFileFromLineResponse方法
 * 可以根据需求自己重写
 */
public class TamsFileControllerServiceImpl extends FileControllerServiceImpl implements TamsFileControllerService {

    private static final String PROJECT_CONTEXT_PATH = "tams";
    private static final String OPERATION_SYSTEM_USER_HOME = System.getProperties().getProperty("user.home");

    @Override
    public boolean saveCalendarAttachments(String uId, String classId, String calendarId, List<FileViewObject> fileList) {
        String calendarRootPath;
        try{
            String _Module_Name = "CalendarFiles";
            String _Calendar_Folder_Name = MD5Encryption.MD5Encode(uId + classId + calendarId, "utf-8", false);
            //创建文件夹
            calendarRootPath = OPERATION_SYSTEM_USER_HOME + File.separator + PROJECT_CONTEXT_PATH +
                    File.separator + _Module_Name + File.separator + _Calendar_Folder_Name;
            FileUtils.createFolder(calendarRootPath);
            for(FileViewObject file : fileList) {
                TAMSAttachments attachment = new TAMSAttachments();
                //基本信息
                attachment.setAuthorId(uId);
                attachment.setContainerId(calendarId);
                attachment.setContainerType(_Module_Name);
                //文件信息
                attachment.setCreateTime(file.getDateUploadedFormatted());
                String fileName = file.getName();
                attachment.setFileName(fileName);
                Long size = file.getSize();
                attachment.setFileSize(size.toString());
                Blob blob = file.getBlob();
                String absoluteFilePath = calendarRootPath + File.separator + fileName;
                //写入磁盘
                FileUtils.saveFile(absoluteFilePath, blob.getBinaryStream());
                attachment.setDiskDirectory(calendarRootPath);
                attachment.setDiskFileName(absoluteFilePath);
                //写入数据库信息
                new TAMSAttachmentsDaoJpa().insertOneByEntity(attachment);
            }
            return true;
        }
        catch (SQLException e) {

        }
        catch (IOException e) {

        }
        // catch了异常
        return false;
    }

    /**
     *
     * 中的sendFileFromLineResponse()代码
     * @param form
     * @param response
     * @param collection
     * @param fileLine
     */
    @Override
    protected void sendFileFromLineResponse(UifFormBase form, HttpServletResponse response, List<FileMeta> collection,
                                            FileMeta fileLine) {
        if (!(fileLine instanceof FileMetaBlob)) {
            return;
        }
        try {
            InputStream is = ((FileMetaBlob) fileLine).getBlob().getBinaryStream();
            response.setContentType(fileLine.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileLine.getName());

            // copy it to response's OutputStream
            FileCopyUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get file contents", e);
        }
    }
}
