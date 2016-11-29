package cn.edu.cqu.ngtl.service.common.impl;

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

        // 改了encoding也没有用
        HttpServletRequest httpServletRequest=form.getRequest();
        try {
            System.out.println(httpServletRequest.getCharacterEncoding());
            httpServletRequest.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MultipartHttpServletRequest request = (MultipartHttpServletRequest) form.getRequest();


//        try {
//            System.out.println(request.getCharacterEncoding());
//            request.setCharacterEncoding("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        final String collectionId = request.getParameter(UifParameters.UPDATE_COMPONENT_ID);
        final String bindingPath = request.getParameter(UifConstants.PostMetadata.BINDING_PATH);

        Class<?> collectionObjectClass = (Class<?>) form.getViewPostMetadata().getComponentPostData(collectionId,
                UifConstants.PostMetadata.COLL_OBJECT_CLASS);

        // // FIXME: 2016/11/29 这里获取到的已经是乱码形式
        Iterator<String> fileNamesItr = request.getFileNames();

        while (fileNamesItr.hasNext()) {
            String propertyPath = fileNamesItr.next();

            MultipartFile uploadedFile = request.getFile(propertyPath);
            System.out.println(uploadedFile.getName());
            System.out.println(uploadedFile.getOriginalFilename());

            final FileMeta fileObject = (FileMeta) KRADUtils.createNewObjectFromClass(collectionObjectClass);
            try {
                fileObject.init(uploadedFile);
            } catch (Exception e) {
                throw new RuntimeException("Unable to initialize new file object", e);
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
