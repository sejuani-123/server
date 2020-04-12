package com.zn.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileDownLoadServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File downLoadFile = (File) request.getSession().getAttribute("downLoadFile");
        if(downLoadFile.exists()){
            FileInputStream fis = new FileInputStream(downLoadFile);
            downLoadFile.delete();
            String filename= URLEncoder.encode(downLoadFile.getName(),"utf-8"); //解决中文文件名下载后乱码的问题
            filename =filename.replaceAll("\\+","%20");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition","attachment; filename="+filename);
            //获取响应输出流对象
            ServletOutputStream out =response.getOutputStream();
            //输出
            fis.close();
            out.write(b);
            out.flush();
            out.close();
            //下载完成后删除源文件
            downLoadFile.delete();
        }
    }
}
