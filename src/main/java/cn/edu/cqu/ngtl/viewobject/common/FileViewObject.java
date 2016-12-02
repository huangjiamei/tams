package cn.edu.cqu.ngtl.viewobject.common;

import org.kuali.rice.krad.file.FileMetaBlob;

import java.text.SimpleDateFormat;

/**
 * Created by hp on 2016/11/15.
 */
public class FileViewObject extends FileMetaBlob {

    private String sizeFormatted;
    private String uploadDate;
    private String uploaderName;
    private String downloadTimes;

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

    public String getSizeFormatted() {
        long size = this.getSize() != null ? this.getSize() : 0;
        if(size < 0x0400) //1 kB
            return size + "Byte";
        else if(size < 0x0100000) //1 mB
            return size / 0x0400 + "KByte";
        else if(size < 0x040000000)
            return size / 0x0100000 + "MByte";
        else
            return size / 0x040000000 + "GByte";
    }

    public void setSizeFormatted(String sizeFormatted) {
        this.sizeFormatted = sizeFormatted;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(String downloadTimes) {
        this.downloadTimes = downloadTimes;
    }
}
