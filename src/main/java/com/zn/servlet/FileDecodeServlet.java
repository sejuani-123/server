package com.zn.servlet;

import com.zn.service.FileService;
import com.zn.service.FileServiceImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDecodeServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String uuFileName = request.getParameter("uuFileName");
        String privateKey = request.getParameter("PrivateKey");
        //掉用文件解密service
        FileService fs = new FileServiceImpl();
        File file = fs.fileDecode(uuFileName, privateKey, request.getServletContext().getRealPath("/fileSaveLocation"));
        request.getSession().setAttribute("downLoadFile",file);
    }
}
