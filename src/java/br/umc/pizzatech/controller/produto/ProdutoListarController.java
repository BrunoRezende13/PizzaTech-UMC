package br.umc.pizzatech.controller.produto;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.dao.produto.ProdutoDAO;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProdutoListarController", value = "/ProdutoListarController")
public class ProdutoListarController extends RollbackServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String txId = request.getParameter("txId");

            if (!Validar.isLong(txId)) {
                sendErrorMessage("Esse id é inválido.", request, response);
                return;
            }

            ProdutoDAO dao = new ProdutoDAO();

            Produto produto = new Produto();
            produto.setId(Long.parseLong(txId));

            if (action.equals("Remover")) {
                dao.deletar(produto);
                sendSuccessMessage("Produto removido com sucesso!", request, response);
            } else if (action.equals("Alterar")) {
                Produto resultado = dao.buscarPorId(produto);

                if(resultado == null){
                    sendErrorMessage("Produto não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("produto_pesquisa", resultado);

                    request.getRequestDispatcher("produto/alterar.jsp").forward(request, response);
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
