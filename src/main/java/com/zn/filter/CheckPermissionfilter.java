package com.zn.filter;

import com.zn.utils.RsaUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckPermissionfilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        HttpSession session = request.getSession();
        String sid = request.getHeader("X-SID");
        String signature = request.getHeader("X-Signature");
        signature = RsaUtil.decode(signature, session.getAttribute("privateKey").toString());
        //签名验证
        if(signature.equals(sid)){
            chain.doFilter(req, res);
        }else{
            response.sendError(403);
        }
}

    @Override
    public void destroy() {

    }
}
