package com.zn.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class FileData implements Serializable {
    private String uuFileName;
    private Long size;
    private String type;
    private String originFileName;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    private String fileDirectory;
    private String encode;

    public FileData() {
    }

    public FileData(String uuFileName, Long size, String type, String originFileName, Date createTime, String fileDirectory, String encode) {
        this.uuFileName = uuFileName;
        this.size = size;
        this.type = type;
        this.originFileName = originFileName;
        this.createTime = createTime;
        this.fileDirectory = fileDirectory;
        this.encode = encode;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "uuFileName='" + uuFileName + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", originFileName='" + originFileName + '\'' +
                ", createTime=" + createTime +
                ", fileDirectory='" + fileDirectory + '\'' +
                ", encode='" + encode + '\'' +
                '}';
    }

    public String getUuFileName() {
        return uuFileName;
    }

    public void setUuFileName(String uuFileName) {
        this.uuFileName = uuFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
