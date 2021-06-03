package br.umc.pizzatech.controller.produto;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.produto.ProdutoDAO;
import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.model.produto.ProdutoFactory;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProdutoController", value = "/ProdutoController")
public class ProdutoController extends RollbackServlet {

    public ProdutoController() {
        new ProdutoDAO().criarTabela();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action.equals("Cadastrar")) {
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

                String validar = validarProduto(produto);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                ProdutoDAO dao = new ProdutoDAO();

                dao.cadastrar(produto);

                sendSuccessMessage("Produto cadastrado com sucesso!", request, response);

            } else if (action.equals("Remover")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                ProdutoDAO dao = new ProdutoDAO();

                Produto produto = new Produto();
                produto.setId(Long.parseLong(txId));

                int deletar = dao.deletar(produto);
                if(deletar == 0){
                    sendErrorMessage("Produto não encontrado.", request, response);
                } else {
                    sendSuccessMessage("Produto removido com sucesso!", request, response);
                }
            } else if (action.equals("Alterar")) {
                Produto pesquisa = (Produto) request.getSession().getAttribute("produto_pesquisa");
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

                String validar = validarProduto(produto);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                ProdutoDAO dao = new ProdutoDAO();

                dao.alterar(produto);
                sendSuccessMessage("Produto alterado com sucesso!", request, response);
            } else if (action.equals("Pesquisar")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                ProdutoDAO dao = new ProdutoDAO();

                Produto produto = new Produto();
                produto.setId(Long.parseLong(txId));

                Produto resultado = dao.buscarPorId(produto);

                if(resultado == null){
                    sendErrorMessage("Produto não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("produto_pesquisa", resultado);

                    String dispatcher = (String) request.getSession().getAttribute("dispatcher");
                    request.getRequestDispatcher(dispatcher).forward(request, response);
                }

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

    private String validarProduto(Produto produto) {

        if (produto.getNome().length() < 3 || produto.getNome().length() > 30) {
            return "O nome deve ter entre 3-30 caracteres.";
        }

        if (produto.getDescricao().length() < 3 || produto.getDescricao().length() > 60) {
            return "A descrição deve ter entre 3-30 caracteres.";
        }

        if(produto.getIngredientes() == null)
            produto.setIngredientes("");

        return null;
    }


}
