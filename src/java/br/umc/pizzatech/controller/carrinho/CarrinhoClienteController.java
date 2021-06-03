package br.umc.pizzatech.controller.carrinho;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CarrinhoClienteController", value = "/CarrinhoClienteController")
public class CarrinhoClienteController extends RollbackServlet {

    public CarrinhoClienteController() {
        new ClienteDAO().criarTabela();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action.equals("Pesquisar Cliente")) {
                String campo = request.getParameter("txCampo");

                ClienteDAO dao = new ClienteDAO();
                List<Cliente> clientes = dao.buscarPorNomeCpfTelefone(campo);

                request.setAttribute("lista", clientes);
                request.getRequestDispatcher("cliente/listar.jsp").forward(request, response);

            } else if (action.equals("Voltar")) {
                response.sendRedirect("admin.jsp");
            } else {
                sendErrorMessage("Ação não encontrada.", request, response);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao executar a ação. " + exception.getMessage(), request, response);
        }

    }

}
