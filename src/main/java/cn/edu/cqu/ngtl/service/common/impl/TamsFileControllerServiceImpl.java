package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.tams.impl.TAMSAttachmentsDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSAttachments;
import cn.edu.cqu.ngtl.service.common.TamsFileControllerService;
import cn.edu.cqu.ngtl.tools.utils.FileUtils;
import cn.edu.cqu.ngtl.tools.utils.MD5Encryption;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
import org.kuali.rice.krad.file.FileMeta;
import org.kuali.rice.krad.file.FileMetaBlob;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.FileControllerServiceImpl;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
        try {
            String _Module_Name = "CalendarFiles";
            String _Calendar_Folder_Name = MD5Encryption.MD5Encode(classId+calendarId, "utf-8", false);
            //创建文件夹
            calendarRootPath = OPERATION_SYSTEM_USER_HOME+File.separator+PROJECT_CONTEXT_PATH+
                    File.separator+_Module_Name+File.separator+_Calendar_Folder_Name;
            FileUtils.createFolder(calendarRootPath);
            for (FileViewObject file : fileList) {
                TAMSAttachments attachment = new TAMSAttachments();
                //基本信息
                attachment.setAuthorId(uId);
                attachment.setContainerId(calendarId);
                attachment.setContainerType(_Module_Name);
                //文件信息
                attachment.setCreateTime(file.getDateUploadedFormatted());
                String fileName = file.getName();
                attachment.setFileName(fileName);
                attachment.setDownloadTimes("0");
                Long size = file.getSize();
                attachment.setFileSize(size.toString());
                Blob blob = file.getBlob();
                String absoluteFilePath = calendarRootPath+File.separator+fileName;
                //写入磁盘
                FileUtils.saveFile(absoluteFilePath, blob.getBinaryStream());
                /** 暂不保存磁盘路径到数据库 **/
                //attachment.setDiskDirectory(calendarRootPath);
                //attachment.setDiskFileName(absoluteFilePath);
                //写入数据库信息
                new TAMSAttachmentsDaoJpa().insertOneByEntity(attachment);
            }
            return true;
        } catch (SQLException e) {

        } catch (IOException e) {

        }
        // catch了异常
        return false;
    }

    @Override
    public boolean deleteOneAttachment(String classId, TAMSAttachments attachment) {
        String calendarRootPath;
        try {
            String _Module_Name = attachment.getContainerType(); //保存时候的_Module_Name
            String _Container_Id = attachment.getContainerId();  //保存时候的_Container_Id
            String _Attachment_Folder_Name = MD5Encryption.MD5Encode(classId+_Container_Id, "utf-8", false);
            //合成文件夹路径
            calendarRootPath = OPERATION_SYSTEM_USER_HOME+File.separator+PROJECT_CONTEXT_PATH+
                    File.separator+_Module_Name+File.separator+_Attachment_Folder_Name;

            String fileName = attachment.getFileName();
            String absoluteFilePath = calendarRootPath+File.separator+fileName;

            //从数据库删除
            boolean result = new TAMSAttachmentsDaoJpa().deleteOneByEntity(attachment);

            if (result) {
                //从磁盘删除
                return FileUtils.removeFile(absoluteFilePath);
            }

            return false;
        } catch (IOException e) {

        }
        // catch了异常
        return false;
    }


    @Override
    public List<TAMSAttachments> getAllCalendarAttachments(String calendarId) {
        return new TAMSAttachmentsDaoJpa().selectCalendarFilesByCalendarId(calendarId);
    }

    @Override
    public void downloadCalendarFile(String classId, String calendarId, String attachmentId, HttpServletResponse response) {
        String calendarRootPath;
        try {
            String _Module_Name = "CalendarFiles";
            String _Calendar_Folder_Name = MD5Encryption.MD5Encode(classId+calendarId, "utf-8", false);
            //合成文件夹路径
            calendarRootPath = OPERATION_SYSTEM_USER_HOME+File.separator+PROJECT_CONTEXT_PATH+
                    File.separator+_Module_Name+File.separator+_Calendar_Folder_Name;

            TAMSAttachments attachments = new TAMSAttachmentsDaoJpa().selectById(attachmentId);
            Integer downloadTimes;
            try {
                downloadTimes = Integer.parseInt(attachments.getDownloadTimes());
            } catch (NumberFormatException e) {
                downloadTimes = 0;
            }
            downloadTimes++;
            attachments.setDownloadTimes(downloadTimes.toString());
            new TAMSAttachmentsDaoJpa().updateByEntity(attachments);
            String fileName = attachments.getFileName();
            String absoluteFilePath = calendarRootPath+File.separator+fileName;
            File file = new File(absoluteFilePath);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=utf-8");
            if (!file.exists()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            InputStream is = new FileInputStream(file);
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);

            FileCopyUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
            response.getOutputStream().close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public ModelAndView addFileUploadLine(final UifFormBase form) {
        form.setAjaxReturnType(UifConstants.AjaxReturnTypes.UPDATECOMPONENT.getKey());
        form.setAjaxRequest(true);

        MultipartHttpServletRequest request = (MultipartHttpServletRequest) form.getRequest();

        final String collectionId = request.getParameter(UifParameters.UPDATE_COMPONENT_ID);
        final String bindingPath = request.getParameter(UifConstants.PostMetadata.BINDING_PATH);

        Class<?> collectionObjectClass = (Class<?>) form.getViewPostMetadata().getComponentPostData(collectionId,
                UifConstants.PostMetadata.COLL_OBJECT_CLASS);


        // 2016/11/29 这里获取到的已经是乱码形式,需要在下面通过uploadedFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8")来转换编码
        Iterator<String> fileNamesItr = request.getFileNames();

        while (fileNamesItr.hasNext()) {
            String propertyPath = fileNamesItr.next();

            MultipartFile uploadedFile = request.getFile(propertyPath);

            final FileMeta fileObject = (FileMeta) KRADUtils.createNewObjectFromClass(collectionObjectClass);
            try {
                fileObject.init(uploadedFile);
            } catch (Exception e) {
                throw new RuntimeException("Unable to initialize new file object", e);
            }
            try {
                // 只能通过这种转换编码的方式，其他的request.setCharacterEncoding()之类的都没有效果
                fileObject.setName(new String(uploadedFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String id = UUID.randomUUID().toString()+"_"+uploadedFile.getName();
            fileObject.setId(id);

            fileObject.setDateUploaded(new Date());

            fileObject.setUrl("?methodToCall=getFileFromLine&formKey="
                    +form.getFormKey()
                    +"&fileName="
                    +fileObject.getName()
                    +"&propertyPath="
                    +propertyPath);

            ViewLifecycle.encapsulateLifecycle(form.getView(), form, form.getViewPostMetadata(), null, request,
                    new Runnable() {
                        @Override
                        public void run() {
                            ViewLifecycle.getHelper().processAndAddLineObject(form, fileObject, collectionId,
                                    bindingPath);
                        }
                    });
        }

        return getModelAndViewService().getModelAndView(form);
    }

    @Override
    protected void sendFileFromLineResponse(UifFormBase form, HttpServletResponse response, List<FileMeta> collection,
                                            FileMeta fileLine) {

        if (!(fileLine instanceof FileMetaBlob)) {
            return;
        }
        try {
            InputStream is = ((FileMetaBlob) fileLine).getBlob().getBinaryStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=utf-8");

            // URLEncoder.encode()很重要，没有的话会导致中文变成'-'符号
            // 根据浏览器进行encoding，不同浏览器编码不同
            // 参考资料：http://blog.csdn.net/likeabook/article/details/13021923
//            final String userAgent = response.getHeader("USER-AGENT");
//
//            String fileName = fileLine.getName();
//            if(StringUtils.contains(userAgent, "MSIE")){            //IE浏览器
//                fileName = URLEncoder.encode(fileName,"UTF8");
//            }else if(StringUtils.contains(userAgent, "Mozilla")){    //Chrome,Firefox浏览器
//                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
//            }else{                                                    //其他浏览器
//                fileName = URLEncoder.encode(fileName,"UTF8");
//            }

            // 经测试，下面这句话可以适应ie、Firefox、chrome，上面那一大段反而不行
            String fileName = new String(fileLine.getName().getBytes("UTF-8"), "ISO8859-1");

            response.setHeader("Content-Disposition", "attachment; filename="+fileName);

            // copy it to response's OutputStream
            FileCopyUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get file contents", e);
        }
    }
}
