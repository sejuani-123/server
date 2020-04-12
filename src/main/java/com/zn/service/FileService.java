package com.zn.service;

import com.zn.entity.FileData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    //文件上传接口
    void fileUpLoad(Part part, HttpServletRequest request,String filePuk) throws IOException;

    //查询所有文件元数据
    List<FileData> queryAllFile();

    //文件下载接口
    File fileDecode(String uuFileName, String privateKey, String path);
}
