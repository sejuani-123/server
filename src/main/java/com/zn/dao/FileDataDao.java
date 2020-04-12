package com.zn.dao;

import com.zn.entity.FileData;

import java.util.List;

public interface FileDataDao {
    //向数据库插入一条数据
    void insertOneRecord(FileData fileData);

    //从数据库中根据uuid查询一条数据
    FileData selectOneRecord(String uuFileName);

    //查询数据库中所有的数据
    List<FileData> selectAllRecord();

}
