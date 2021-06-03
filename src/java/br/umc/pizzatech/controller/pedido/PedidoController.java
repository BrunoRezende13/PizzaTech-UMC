package br.umc.pizzatech.controller.pedido;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pedido.PedidoDAO;
import br.umc.pizzatech.dao.produto.ProdutoDAO;
import br.umc.pizzatech.model.pedido.Pedido;
import br.umc.pizzatech.model.pedido.PedidoStatus;
import br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho;
import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.model.produto.ProdutoFactory;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PedidoController", value = "/PedidoController")
public class PedidoController extends RollbackServlet {

    public PedidoController() {
        new ProdutoDAO().criarTabela();
        new PedidoDAO().criarTabela();
    }

    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action.equals("Finalizar")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                PedidoDAO dao = new PedidoDAO();

                Pedido pedido = new Pedido();
                pedido.setId(Long.parseLong(txId));
                pedido.setStatus(PedidoStatus.FINALIZADO);
                dao.alterarStatus(pedido);
                sendSuccessMessage("Pedido finalizado com sucesso!", request, response);
            }
            else if (action.equals("Remover")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                PedidoDAO dao = new PedidoDAO();

                Pedido pedido = new Pedido();
                pedido.setId(Long.parseLong(txId));

                int deletar = dao.deletar(pedido);
                if(deletar == 0){
                    sendErrorMessage("Pedido não encontrado.", request, response);
                } else {
                    sendSuccessMessage("Pedido removido com sucesso!", request, response);
                }
            } else if (action.equals("Alterar")) {
                Produto pesquisa = (Produto) request.getSession().getAttribute("pedido_pesquisa");
                if(pesquisa == null){
                    sendErrorMessage("Produto inválido", request, response);
                    return;
                }

                String nome = request.getParameter("txNome");
                String descricao = request.getParameter("txDescricao");
                String ingrediente = request.getParameter("txIngrediente");
                double valor = Validar.isValor(request.getParameter("txValor"));
                String produtoModo = request.getParameter("produto_modo");

                int estoque = Validar.isEstoque(request.getParameter("txEstoque"));
                if (estoque == -1) {
                    sendErrorMessage("Esse estoque é inválido.", request, response);
                    return;
                }
                if (valor == -1) {
                    sendErrorMessage("Esse valor é inválido.", request, response);
                    return;
                }

                Produto produto = ProdutoFactory.criarProduto(nome, descricao, ingrediente, valor, estoque, produtoModo);
                produto.setId(pesquisa.getId());

                ProdutoDAO dao = new ProdutoDAO();

                dao.alterar(produto);
                sendSuccessMessage("Produto alterado com sucesso!", request, response);
            } else if (action.equals("Pesquisar")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                PedidoDAO dao = new PedidoDAO();

                Pedido produto = new Pedido();
                produto.setId(Long.parseLong(txId));

                Pedido resultado = dao.buscarPorId(produto);

                if(resultado == null){
                    sendErrorMessage("Pedido não encontrado.", request, response);
                } else {
                    List<ProdutoCarrinho> produtoCarrinhos = dao.buscarTodos(resultado);
                    resultado.setItens(produtoCarrinhos);

                    request.getSession().setAttribute("pedido_pesquisa", resultado);

                    String dispatcher = (String) request.getSession().getAttribute("dispatcher");
                    request.getRequestDispatcher(dispatcher).forward(request, response);
                }
            } else if (action.equals("Filtrar")) {
                PedidoStatus status = PedidoStatus.valueOf(request.getParameter("selStatus"));
                request.setAttribute("listar_status", status);
                request.getRequestDispatcher("pedidos/listar.jsp").forward(request, response);

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

}
