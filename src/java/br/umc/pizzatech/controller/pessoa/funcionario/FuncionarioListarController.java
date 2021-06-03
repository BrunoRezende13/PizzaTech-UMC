package br.umc.pizzatech.controller.pessoa.funcionario;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso;
import br.umc.pizzatech.util.ValidaCpf;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FuncionarioListarController", value = "/FuncionarioListarController")
public class FuncionarioListarController extends RollbackServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String txId = request.getParameter("txId");

            if (!Validar.isLong(txId)) {
                sendErrorMessage("Esse id é inválido.", request, response);
                return;
            }

            FuncionarioDAO dao = new FuncionarioDAO();

            Funcionario funcionario = new Funcionario();
            funcionario.setId(Long.parseLong(txId));

            if (action.equals("Remover")) {
                dao.deletar(funcionario);
                sendSuccessMessage("Funcionário removido com sucesso!", request, response);
            } else if (action.equals("Alterar")) {
                Funcionario resultado = dao.buscarPorId(funcionario);

                if(resultado == null){
                    sendErrorMessage("Funcionário não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("funcionario_pesquisa", resultado);

                    request.getRequestDispatcher("funcionario/alterar.jsp").forward(request, response);
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
