package cn.edu.cqu.ngtl.service.common.impl;

import org.apache.commons.lang.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by hasee on 2016/11/24.
 * 多文件上传之后，点击文件名(href)会调用getFileFromLine方法，该方法最终调用此service的sendFileFromLineResponse方法
 * 可以根据需求自己重写
 */
public class TamsFileControllerServiceImpl extends FileControllerServiceImpl {

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
            String id = UUID.randomUUID().toString() + "_" + uploadedFile.getName();
            fileObject.setId(id);

            fileObject.setDateUploaded(new Date());

            fileObject.setUrl("?methodToCall=getFileFromLine&formKey="
                    + form.getFormKey()
                    + "&fileName="
                    + fileObject.getName()
                    + "&propertyPath="
                    + propertyPath);

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

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // copy it to response's OutputStream
            FileCopyUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get file contents", e);
        }
    }
}
