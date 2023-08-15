package com.andersenlab.servlet;

import com.andersenlab.command.CommandProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Controller", urlPatterns = "/")
public class Controller extends HttpServlet {

    CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        commandProvider.processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        commandProvider.processRequest(req, resp);
    }
}