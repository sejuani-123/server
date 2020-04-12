package com.zn.service;

import com.zn.dao.FileDataDao;
import com.zn.dao.FileDataDaoImpl;
import com.zn.entity.FileData;
import com.zn.utils.AesUtil;
import com.zn.utils.DerbyUtils;
import com.zn.utils.RsaUtil;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    //声明dao方法
    FileDataDao fd = new FileDataDaoImpl();

    //实现文件上传接口
    @Override
    public void fileUpLoad(Part part, HttpServletRequest request, String filePuk) throws IOException {
        //定义连接局部变量
        Connection con = null;
        try {
            // 1.调用工具类获得连接
            con = DerbyUtils.getConnection();
            // 2.设置提交方式为手动提交
            con.setAutoCommit(false);
            //处理文件相关
            String fileName = part.getSubmittedFileName();
            //获取文件的后缀
            String name = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //设置你新的文件名
            String id = UUID.randomUUID().toString();
            String newFileName = id + name;
            //获取文件大小
            long size = part.getSize();
            //获取文件创建时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //获取文件存储文件夹名
            String dirName = sdf.format(date);
            //获取数据流,将文件存储在服务器中
            InputStream is = part.getInputStream();
            //获取web应用路径
            String path = request.getServletContext().getRealPath("/fileSaveLocation");
            File f = new File(path + "/" + dirName);
            if (!f.exists()) {
                f.mkdir();
            }
            File file = new File(f.getAbsolutePath() + "/" + newFileName);
            file.createNewFile();
            //获取输出流
            FileOutputStream fos = new FileOutputStream(file);
            //每次写入一个字节数组
            byte[] bys = new byte[1024];
            int len;
            while ((len = is.read(bys)) != -1) {
                fos.write(bys, 0, len);
            }
            is.close();
            fos.close();
            //将文件加密
            //获得密钥
            byte[] byteKey = null;
            try {
                //获得AES对称密钥
                byteKey = AesUtil.getKey();
                //使用AES将文件加密
                AesUtil.getAESEncode(byteKey, file.getAbsolutePath(), f.getAbsolutePath() + "\\" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //使用RSA将AES密钥加密
            String encode = RsaUtil.encode(Base64.encodeBase64URLSafeString(byteKey), filePuk);
            //封装用户上传的文件对象
            FileData fileData = new FileData(newFileName, size, name, fileName, date, dirName, encode);
            //调用dao将数据插入数据库
            fd.insertOneRecord(fileData);
            //3.没有异常，事务提交
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //3.有异常，事务回滚
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            //4.释放资源
            DerbyUtils.release(null, null, con);
        }
    }

    //实现查询文件元数据接口
    @Override
    public List<FileData> queryAllFile() {
        return fd.selectAllRecord();
    }


    //实现下载文件接口
    @Override
    public File fileDecode(String uuFileName, String privateKey,String path) {
        File f = null;
        FileData fileData = fd.selectOneRecord(uuFileName);
        //使用Rsa非对称密钥解密Aes对称密钥密文
        String decode = RsaUtil.decode(fileData.getEncode(), privateKey);
        String fileName = fileData.getUuFileName().substring(0, fileData.getUuFileName().lastIndexOf("."));
        String encodeFile = path+"/"+fileData.getFileDirectory()+"/"+fileName;
        String decodeFile = path+"/"+fileData.getFileDirectory()+"/"+fileData.getOriginFileName();
        //使用aes对文件进行解码
        try {
            AesUtil.getAESDecode(Base64.decodeBase64(decode),encodeFile,decodeFile);
            f = new File(decodeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
