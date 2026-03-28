package it.is.servlet;

import it.is.servlet.configurer.FilterConfigurer;
import it.is.servlet.configurer.ServletConfigurer;
import it.is.servlet.http.TestServlet;
import it.is.servlet.http.filter.TestFilter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Initilizer {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(5012);
        tomcat.setHostname("localhost");
        tomcat.getHost().setAppBase(".");

        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        ServletConfigurer.configure(context, TestServlet.class, "/api/v1/*");
        FilterConfigurer.configureFilter(TestFilter.class, context, "/api/v1/*");

        Connector connector = tomcat.getConnector();
        connector.setPort(8080);

        tomcat.start();
        tomcat.getServer().await();
        tomcat.getService().addConnector(connector);
    }

}
