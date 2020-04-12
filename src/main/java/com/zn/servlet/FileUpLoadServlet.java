package com.zn.servlet;

import com.alibaba.fastjson.JSON;
import com.zn.service.FileService;
import com.zn.service.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;

@MultipartConfig
public class FileUpLoadServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取part对象 参数为name属性的值
        Part part = request.getPart("upFile");
        String publicKey = request.getParameter("publicKey");
        FileService fuls = new FileServiceImpl();
        fuls.fileUpLoad(part, request, publicKey);
        String js = JSON.toJSONString("success");
        response.getWriter().print(js);
    }

}
