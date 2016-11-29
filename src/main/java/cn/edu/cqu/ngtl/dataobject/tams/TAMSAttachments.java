package cn.edu.cqu.ngtl.dataobject.tams;

import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ATTACHMENTS")
public class TAMSAttachments extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
    @GeneratedValue(generator="tamsAttachments")
    @SequenceGenerator(name="tamsAttachments",sequenceName="TAMS_ATTACHMENTS_S",allocationSize=1)
    private String id;

    @Column(name = "CONTAINER_ID")
    private String containerId;

    @Column(name = "CONTAINER_TYPE")
    private String containerType;

    @Column(name = "File_NAME")
    private String fileName;

    @Column(name = "DISK_FILE_NAME")
    private String diskFileName;

    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "DIGEST")
    private String digest;

    @Column(name = "DOWNLOAD_TIMES")
    private String downloadTimes;

    @Column(name = "AUTHOR_ID")
    private String authorId;

    @Column(name = "CREATE_TIME")
    @Convert(converter = StringDateConverter.class)
    private String createTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DISK_DIRECTORY")
    private String diskDirectory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDiskFileName() {
        return diskFileName;
    }

    public void setDiskFileName(String diskFileName) {
        this.diskFileName = diskFileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(String downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiskDirectory() {
        return diskDirectory;
    }

    public void setDiskDirectory(String diskDirectory) {
        this.diskDirectory = diskDirectory;
    }
}
