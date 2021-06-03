package br.umc.pizzatech.controller;

import br.umc.pizzatech.model.conta.Autenticador;
import br.umc.pizzatech.model.conta.Conta;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/LoginController")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("txEmail");
            String senha = request.getParameter("txSenha");

            if (!Validar.isEmail(email)) {
                erroMessage("Esse email é inválido.", request, response);
                return;
            }
            if (senha.length() < 4 || senha.length() > 30) {
                erroMessage("A senha deve ter entre 4-30 caracteres.", request, response);
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setEmail(email);
            funcionario.setSenha(senha);

            Conta conta = Autenticador.autentificar(funcionario);

            if (conta == null) {
                erroMessage("Email ou senha incorreto.", request, response);
            } else {
                Autenticador.logged(request.getSession(), conta);
//            request.getRequestDispatcher("admin.jsp").forward(request, response);

                response.sendRedirect("admin.jsp");
            }

        } catch (Exception exception){
            exception.printStackTrace();
            erroMessage("Ocorreu um erro.", request, response);
        }

    }


    private void erroMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("erro", message);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}
