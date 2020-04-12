package com.zn.dao;

import com.zn.entity.FileData;
import com.zn.utils.DerbyUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FileDataDaoImpl implements FileDataDao{

    //插入数据
    @Override
    public void insertOneRecord(FileData fileData) {
        //1 调用工具类获得连接
        Connection con = DerbyUtils.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //2.创建statement
            String sql = "insert into file_Info values(?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(sql);
            //3.为？赋值
            pstm.setString(1,fileData.getUuFileName());
            pstm.setLong(2,fileData.getSize());
            pstm.setString(3,fileData.getType());
            pstm.setString(4,fileData.getOriginFileName());
            java.sql.Date sqldate = new java.sql.Date(fileData.getCreateTime().getTime());
            pstm.setDate(5,sqldate);
            pstm.setString(6,fileData.getFileDirectory());
            pstm.setString(7,fileData.getEncode());
            //4.执行sql
            pstm.executeUpdate();
            //5.处理查询结果
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 6.释放资源
            DerbyUtils.release(rs,pstm,null);
        }

    }

    //查询一条数据
    @Override
    public FileData selectOneRecord(String uuFileName) {
        //1 调用工具类获得连接
        Connection con = DerbyUtils.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        FileData fd = null;
        try {
            //2.创建statement
            String sql = "select * from FILE_INFO where UUFILENAME = ?";
            pstm = con.prepareStatement(sql);
            //3.为？赋值
            pstm.setString(1,uuFileName);
            //4.执行sql
            rs = pstm.executeQuery();
            //5.处理查询结果
            if (rs.next()){
                fd = new FileData(rs.getString(1),rs.getLong(2),rs.getString(3),
                                  rs.getString(4),rs.getDate(5),rs.getString(6),
                                  rs.getString(7));
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 6.释放资源
            DerbyUtils.release(rs,pstm,null);
        }
        return fd;
    }

    @Override
    public List<FileData> selectAllRecord() {
        //1 调用工具类获得连接
        Connection con = DerbyUtils.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<FileData> list = new ArrayList<FileData>();
        FileData fd = null;
        try {
            //2.创建statement
            String sql = "select * from FILE_INFO";
            pstm = con.prepareStatement(sql);
            //3.为？赋值
            //4.执行sql
            rs = pstm.executeQuery();
            //5.处理查询结果
            while (rs.next()){
                fd = new FileData(rs.getString(1),rs.getLong(2),rs.getString(3),
                        rs.getString(4),rs.getDate(5),rs.getString(6),
                        rs.getString(7));
                list.add(fd);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 6.释放资源
            DerbyUtils.release(rs,pstm,null);
        }
        return list;
    }
}
