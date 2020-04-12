package com.zn.servlet;

import com.alibaba.fastjson.JSON;
import com.zn.utils.RsaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GetPublicKeyServlet extends HttpServlet {

    //前台获取私钥的servlet
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //将公钥存储在session作用域中,私钥传回前端
        HttpSession session = request.getSession(true);
        Map<String, String> rsaMap = new HashMap<String, String>();
        rsaMap = RsaUtil.createRSAKeys();
        String publicKey = rsaMap.get("public");
        String privateKey = rsaMap.get("private");
        session.setAttribute("privateKey", privateKey);
        session.setAttribute("publicKey", publicKey);
        String jsonString = JSON.toJSONString(publicKey);
        PrintWriter writer = response.getWriter();
        //传回公钥
        writer.print(jsonString);
    }

}
