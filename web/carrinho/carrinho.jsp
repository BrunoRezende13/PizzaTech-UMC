<%@page import="java.text.DecimalFormat"%>
<%@ page import="br.umc.pizzatech.model.pedido.Pedido" %>
<%@ page import="br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho" %>
<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Conteúdo do carrinho</title>
    <style>
        table,tr,td {border: solid black 1px; padding: 2px;}
    </style>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }
%>

<%
    Pedido carrinho = (Pedido) session.getAttribute("carrinho");

    if (carrinho == null) {
        response.sendRedirect("admin.jsp");
        return;
    }

%>
<table>
    <caption>Carrinho de Compras</caption>
    <tr>
        <td>Excluir</td><td>Item</td><td>Quantidade</td>
        <td>Preço Unitário</td><td>Total Item</td><td>+1</td>
    </tr>
    <%
        for(ProdutoCarrinho item : carrinho.getItens()){
    %>
    <tr>
        <td><a href="${pageContext.request.contextPath}/carrinho/CarrinhoController?action=ProdutoRemover&txId=<%=item.getProduto().getId()%>">X</a></td>
        <td><%=item.getProduto().getNome() %></td>   <td><%=item.getQuantidade()  %></td>   
        <td>R$ <%=new DecimalFormat("0.00").format(item.getProduto().getValor())%></td>
        <td>R$ <%=new DecimalFormat("0.00").format(item.getTotal())%></td>
        <td><a href="${pageContext.request.contextPath}/carrinho/CarrinhoController?action=ProdutoAdicionar&txId=<%=item.getProduto().getId()%>">+</a></td>
    </tr>
    <% } %>

</table>
<h1>Valor Total: R$ <%=new DecimalFormat("0.00").format(carrinho.getTotal())%></h1>
<br>

<form action="CarrinhoController" method="post">
    <br>
    <label for="txAnotacao">Anotação:</label>
    <textarea id="txAnotacao" name="txAnotacao" rows="4" cols="50"><%=carrinho.getAnotacao()%></textarea>
    <input type="submit" name="action" value="Salvar Anotacao">
    <br>
    <br>
    <input type="submit" name="action" value="Finalizar Compra">
    <input type="submit" name="action" value="Continuar Compra">
    <input type="submit" name="action" value="Cancelar Compra">
</form>
</body>
</html>