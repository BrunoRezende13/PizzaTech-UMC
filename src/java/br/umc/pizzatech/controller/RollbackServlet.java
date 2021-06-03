package br.umc.pizzatech.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class RollbackServlet extends HttpServlet {

    protected String erroPath = "erro.jsp";
    protected String sucessoPath = "sucesso.jsp";
    protected String rollbackPath = "admin.jsp";

    protected void sendErrorMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendErrorMessage(message, rollbackPath, request, response);
    }

    protected void sendErrorMessage(String message, String rollback, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("erro", message);
        request.setAttribute("rollback", rollback);
        request.getRequestDispatcher(erroPath).forward(request, response);
    }

    protected void sendSuccessMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendSuccessMessage(message, rollbackPath, request, response);
    }

    protected void sendSuccessMessage(String message, String rollback, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("rollback", rollback);
        request.getRequestDispatcher(sucessoPath).forward(request, response);
    }


}
