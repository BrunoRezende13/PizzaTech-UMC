package br.umc.pizzatech.controller.pessoa.cliente;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClienteListarController", value = "/ClienteListarController")
public class ClienteListarController extends RollbackServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String txId = request.getParameter("txId");

            if (!Validar.isLong(txId)) {
                sendErrorMessage("Esse id é inválido.", request, response);
                return;
            }

            ClienteDAO dao = new ClienteDAO();

            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(txId));

            if (action.equals("Remover")) {
                dao.deletar(cliente);
                sendSuccessMessage("Cliente removido com sucesso!", request, response);
            } else if (action.equals("Alterar")) {
                Cliente resultado = dao.buscarPorId(cliente);

                if(resultado == null){
                    sendErrorMessage("Cliente não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("cliente_pesquisa", resultado);

                    request.getRequestDispatcher("cliente/alterar.jsp").forward(request, response);
                }

            } else {
                sendErrorMessage("Ação não encontrada.", request, response);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao executar a ação. " + exception.getMessage(), request, response);
        }

    }


}
