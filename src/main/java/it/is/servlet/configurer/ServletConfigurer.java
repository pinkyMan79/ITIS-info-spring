package it.is.servlet.configurer;

import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class ServletConfigurer {
    public static void configure(Context context, Class<? extends HttpServlet> servletClass, String url) {
        Tomcat.addServlet(context, servletClass.getSimpleName(), servletClass.getName());
        context.addServletMappingDecoded(url, servletClass.getSimpleName());
    }
}
