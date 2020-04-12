package com.zn.servlet;

import com.alibaba.fastjson.JSON;
import com.zn.entity.FileData;
import com.zn.service.FileService;
import com.zn.service.FileServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class queryAllFileServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        FileService fs = new FileServiceImpl();
        List<FileData> fileData = fs.queryAllFile();
        String data = JSON.toJSONString(fileData);
        response.getWriter().print(data);
    }
}
