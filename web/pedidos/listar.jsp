<%@page import="java.text.DecimalFormat"%>
<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.produto.Produto" %>
<%@ page import="br.umc.pizzatech.dao.produto.ProdutoDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="br.umc.pizzatech.model.pedido.PedidoStatus" %>
<%@ page import="br.umc.pizzatech.model.pedido.Pedido" %>
<%@ page import="br.umc.pizzatech.dao.pedido.PedidoDAO" %>
<%@ page import="br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listar Produtos</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para atendentes logados.</h1>
<%
        return;
    }

    PedidoStatus status = (PedidoStatus) request.getAttribute("listar_status");

    if(status == null)
        status = PedidoStatus.EM_ANDAMENTO;
%>
<h3>Filtrar por status</h3>

<form action="PedidoController" method="POST">

    <select name="selStatus">
<%
    for(PedidoStatus cat : PedidoStatus.values()){
        if(cat == PedidoStatus.ABERTO)
            continue;
%>

<option value="<%=cat.name()%>"> <%=cat.getNome()%></option>

<%
    }
%>
</select>

    <input type="submit" name="action" value="Filtrar">
</form>


<br>
<br>

<h1>Listar pedidos</h1>
<h2><%=status.getNome()%></h2>

<table border="1">
    <tr><th>Id</th><th>Cliente</th><th>Anotação</th><th>Valor Total</th><th>Funcionário</th><th>Detalhes</th>
        <%
            if(status == PedidoStatus.EM_ANDAMENTO)
        %>
            <th>Finalizar Pedido</th>

    </tr>

    <%
        request.getSession().setAttribute("dispatcher", "pedidos/exibir.jsp");

        List<Pedido> list = (List<Pedido>) request.getAttribute("lista");

        if(list == null){
            try {
                PedidoDAO pedidoDAO = new PedidoDAO();
                list = pedidoDAO.buscarTodos();

                for (Pedido pedido : list) {
                    List<ProdutoCarrinho> produtoCarrinhos = pedidoDAO.buscarTodos(pedido);
                    pedido.setItens(produtoCarrinhos);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                request.setAttribute("erro", "Ocorreu um erro ao listar.");
                request.setAttribute("rollback", "admin.jsp");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
                return;
            }
        }


        for (Pedido pedido : list) {
    if(pedido.getStatus() != status)
        continue;
    %>

    <tr>
        <td><%= pedido.getId()%></td>
        <td><%= pedido.getCliente().getNome()%></td>
        <td><%= pedido.getAnotacao()%></td>
        <td>R$ <%= new DecimalFormat("00.00").format(pedido.getTotal())%></td>
        <td><%= pedido.getFuncionario().getNome()%></td>
        <td>
            <a href="${pageContext.request.contextPath}/PedidoController?txId=<%=pedido.getId()%>&action=Pesquisar">
                <img src="img/detalhes.png" style="width:40px;height:40px;">
            </a>
        </td>
        <%
            if(status == PedidoStatus.EM_ANDAMENTO) {
        %>
        <td>
            <a href="${pageContext.request.contextPath}/PedidoController?txId=<%=pedido.getId()%>&action=Finalizar">
                <img src="img/finish.png" style="width:40px;height:40px;">
            </a>
        </td>
        <%
            }
        %>
    </tr>

    <% }%>
</table>
<br>
<br>
<form action="PedidoController" method="POST">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
