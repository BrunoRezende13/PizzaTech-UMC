package br.umc.pizzatech.controller;

import br.umc.pizzatech.dao.pedido.PedidoDAO;
import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.dao.produto.ProdutoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminController", value = "/AdminController")
public class AdminController extends RollbackServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (request.getParameter("action-e") != null) {
                actionExtra(request.getParameter("action-e"), request, response);
            }else if (request.getParameter("action-f") != null) {
                actionFuncionario(request.getParameter("action-f"), request, response);
            } else if (request.getParameter("action-p") != null) {
                actionProduto(request.getParameter("action-p"), request, response);
            } else if (request.getParameter("action-c") != null) {
                actionCliente(request.getParameter("action-c"), request, response);
            } else if (request.getParameter("action-i") != null) {
                actionInfo(request.getParameter("action-i"), request, response);
            } else if (request.getParameter("action-o") != null) {
                actionPedido(request.getParameter("action-o"), request, response);
            }  else {
                sendErrorMessage("Formulario não encontrado.", request, response);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao analisar sua solicitação", request, response);
        }
    }

    private void actionExtra(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Sair".equals(action)) {
            request.getSession().invalidate();

            response.sendRedirect("login.jsp");
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }

    private void actionFuncionario(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Cadastrar".equals(action)) {
            request.getRequestDispatcher("funcionario/cadastrar.jsp").forward(request, response);
        } else if ("Remover".equals(action)) {
            request.getRequestDispatcher("funcionario/remover.jsp").forward(request, response);
        } else if ("Alterar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "funcionario/alterar.jsp");
            request.getRequestDispatcher("funcionario/pesquisar.jsp").forward(request, response);
        } else if ("Pesquisar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "funcionario/exibir.jsp");
            request.getRequestDispatcher("funcionario/pesquisar.jsp").forward(request, response);
        } else if ("Listar todos".equals(action)) {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            request.setAttribute("lista", funcionarioDAO.buscarTodos());
            request.getRequestDispatcher("funcionario/listar.jsp").forward(request, response);
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }

    private void actionProduto(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Cadastrar".equals(action)) {
            request.getRequestDispatcher("produto/cadastrar.jsp").forward(request, response);
        } else if ("Remover".equals(action)) {
            request.getRequestDispatcher("produto/remover.jsp").forward(request, response);
        } else if ("Alterar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "produto/alterar.jsp");
            request.getRequestDispatcher("produto/pesquisar.jsp").forward(request, response);
        } else if ("Pesquisar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "produto/exibir.jsp");
            request.getRequestDispatcher("produto/pesquisar.jsp").forward(request, response);
        } else if ("Listar todos".equals(action)) {
            ProdutoDAO dao = new ProdutoDAO();
            request.setAttribute("lista", dao.buscarTodos());
            request.getRequestDispatcher("produto/listar.jsp").forward(request, response);
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }

    private void actionCliente(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Cadastrar".equals(action)) {
            request.getRequestDispatcher("cliente/cadastrar.jsp").forward(request, response);
        } else if ("Remover".equals(action)) {
            request.getRequestDispatcher("cliente/remover.jsp").forward(request, response);
        } else if ("Alterar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "cliente/alterar.jsp");
            request.getRequestDispatcher("cliente/pesquisar.jsp").forward(request, response);
        } else if ("Pesquisar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "cliente/exibir.jsp");
            request.getRequestDispatcher("cliente/pesquisar.jsp").forward(request, response);
        } else if ("Listar todos".equals(action)) {
            ClienteDAO clienteDAO = new ClienteDAO();
            request.setAttribute("lista", clienteDAO.buscarTodos());
            request.getRequestDispatcher("cliente/listar.jsp").forward(request, response);
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }

    private void actionPedido(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Iniciar Pedido".equals(action)) {
            request.getRequestDispatcher("carrinho/pesquisacliente.jsp").forward(request, response);
        } else if ("Remover".equals(action)) {
            request.getRequestDispatcher("pedidos/remover.jsp").forward(request, response);
        } else if ("Pesquisar".equals(action)) {
            request.getSession().setAttribute("dispatcher", "pedidos/exibir.jsp");
            request.getRequestDispatcher("pedidos/pesquisar.jsp").forward(request, response);
        } else if ("Listar todos".equals(action)) {
            request.getRequestDispatcher("pedidos/listar.jsp").forward(request, response);
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }

    private void actionInfo(String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("Informações".equals(action)) {
            request.getRequestDispatcher("analise/exibir.jsp").forward(request, response);
        } else {
            sendErrorMessage("Ação não encontrada.", request, response);
        }
    }


}
