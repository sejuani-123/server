import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.InetSocketAddress;

public class main {
    private static String host;
    private static String port;

    public static void main(String[] args) throws Exception {
        host = "localhost";
        port = "9090";
        InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
        //新建web服务器
        Server server = new Server(address);

        // 支持JSP
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        // 用WebAppContext可以支持servlet
        WebAppContext webApp = new WebAppContext();
        webApp.setContextPath("/");
        //webApp.addServlet(TestServlet.class,"/myServlet");
        webApp.setResourceBase("./src/main/webapp");
        webApp.setDescriptor("Webapp/WEB-INF/web.xml");

        // 支持jstl和其他tag必须设置以下配置
        webApp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

        server.setHandler(webApp);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
