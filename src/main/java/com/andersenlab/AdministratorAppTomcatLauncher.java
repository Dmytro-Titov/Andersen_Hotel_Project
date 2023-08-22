package com.andersenlab;

import com.andersenlab.config.StartServlet;
import com.andersenlab.servlet.apartment.ApartmentByIdServlet;
import com.andersenlab.servlet.apartment.ApartmentChangeStatusByIdServlet;
import com.andersenlab.servlet.apartment.ApartmentsServlet;
import com.andersenlab.servlet.client.*;
import com.andersenlab.servlet.perk.PerkByIdServlet;
import com.andersenlab.servlet.perk.PerksServlet;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class AdministratorAppTomcatLauncher {

    public static void main(String[] args) throws Exception {
        StartServlet.getTomcat().start();
    }
}