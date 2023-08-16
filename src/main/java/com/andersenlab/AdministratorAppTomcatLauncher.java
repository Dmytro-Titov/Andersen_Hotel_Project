package com.andersenlab;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class AdministratorAppTomcatLauncher {
    public static final String WEBAPP_DIR = "src/main/webapp/";
    public static final String PORT = "8080";
    public static final String TARGET_CLASSES = "target/classes";
    public static final String WEB_INF_CLASSES = "/WEB-INF/classes";
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(Integer.valueOf(PORT));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(WEBAPP_DIR).getAbsolutePath());

        File additionWebInfClasses = new File(TARGET_CLASSES);
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, WEB_INF_CLASSES,
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
