<%@page import="java.text.DecimalFormat"%>
<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.pedido.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exibir Pedido</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }

    Pedido pedido = (Pedido) session.getAttribute("pedido_pesquisa");

    if(pedido == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Pedido</h1>

<form action="PedidoController" method="POST">
    <br>
    Id.........: <%=pedido.getId()%> <br>
    Cliente......: <%=pedido.getCliente().getNome()%> <br>
    Funcionário...: <%=pedido.getFuncionario().getNome()%> <br>
    Status......: <%=pedido.getStatus().getNome()%> <br>
    Anotação......: <%=pedido.getAnotacao()%> <br>
    Valor Total....: R$ <%=new DecimalFormat("00.00").format(pedido.getTotal())%> <br>

    <br>
    <br>

    <table border="1">
        <tr><th>Produto</th><th>Quantidade</th><th>Valor</th></tr>

        <%
            request.getSession().setAttribute("dispatcher", "pedidos/alterar.jsp");

            List<ProdutoCarrinho> list = pedido.getItens();

            for (ProdutoCarrinho produto : list) {
        %>

        <tr>
            <td><%= produto.getProduto().getNome()%></td>
            <td><%= produto.getQuantidade()%></td>
            <td>R$ <%= new DecimalFormat("00.00").format(produto.getTotal())%></td>
        </tr>

        <% }%>
    </table>

    <br>
    <br>
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
