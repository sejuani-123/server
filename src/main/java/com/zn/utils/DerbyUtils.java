package com.zn.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DerbyUtils {
    private static final ThreadLocal<Connection> tol = new ThreadLocal<Connection>();

    //获得连接的方法
    public static Connection getConnection() {
        //获取线程局部变量中的值
        Connection con = tol.get();
        String protocol = "jdbc:derby:";
        String dbName = "fileDB;create=true";
        if (con == null) {

            try {
                //当前线程中没有con,创建连接
                con = DriverManager.getConnection(protocol + dbName);
                //把con存入线程局部变量中
                tol.set(con);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    //关闭连接
    public static void release(ResultSet rs, Statement stm, Connection con) {

        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) {
                con.close();
                //移除线程局部变量中的con
                tol.remove();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
