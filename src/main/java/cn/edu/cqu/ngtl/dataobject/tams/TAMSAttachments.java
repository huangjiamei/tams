package cn.edu.cqu.ngtl.dataobject.tams;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.uif.UifConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liusijia on 2016/10/22.
 */
@Entity
@Table(name = "TAMS_ATTACHMENTS")
public class TAMSAttachments extends DataObjectBase implements Serializable{

    @Id
    @Column(name = "UNIQUEID")
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
    private String downliadTimes;

    @Column(name = "AUTHOR_ID")
    private String authorId;

    @Column(name = "CREATE_TIME")
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

    public String getDownliadTimes() {
        return downliadTimes;
    }

    public void setDownliadTimes(String downliadTimes) {
        this.downliadTimes = downliadTimes;
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
