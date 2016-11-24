package cn.edu.cqu.ngtl.viewobject.common;

import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.file.FileMetaBlob;

import java.text.SimpleDateFormat;

/**
 * Created by hp on 2016/11/15.
 */
public class FileViewObject extends FileMetaBlob {


    /**
     * 重写了获取日期的方法，
     * 从 '11/24/2016 10:38 下午'-> '2016-11-12 10:38:00'
     * @return 格式化日期
     */
    @Override
    public String getDateUploadedFormatted() {
        if (getDateUploaded() != null) {
            SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dd.format(getDateUploaded());
        } else {
            return "";
        }
    }
}
