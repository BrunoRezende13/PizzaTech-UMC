<%@page import="java.text.DecimalFormat"%>
<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.produto.Produto" %>
<%@ page import="br.umc.pizzatech.dao.produto.ProdutoDAO" %>
<%@ page import="java.sql.SQLException" %>
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
<a href="../login.jsp">Voltar</a>
<%
        return;
    }
%>

<h1>Listar Produtos</h1>

<table border="1">
    <tr><th>Id</th><th>Nome</th><th>Descrição</th><th>Ingredientes</th><th>Valor</th><th>Estoque</th><th>Tipo</th><th>Alterar</th><th>Remover</th></tr>

    <%
        request.getSession().setAttribute("dispatcher", "produto/alterar.jsp");

        List<Produto> list = (List<Produto>) request.getAttribute("lista");

        if(list == null){
            try {
                list = new ProdutoDAO().buscarTodos();
            } catch (Exception exception) {
                exception.printStackTrace();
                request.setAttribute("erro", "Ocorreu um erro ao listar.");
                request.setAttribute("rollback", "admin.jsp");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
                return;
            }
        }


        for (Produto produto : list) {
    %>

    <tr>
        <td><%= produto.getId()%></td>
        <td><%= produto.getNome()%></td>
        <td><%= produto.getDescricao()%></td>
        <td><%= produto.getIngredientes()%></td>
        <td>R$ <%= new DecimalFormat("0.00").format(produto.getValor())%></td>
        <td><%= produto.getEstoque()%></td>
        <td><%= produto.getTipo()%></td>
        <td>
            <a href="${pageContext.request.contextPath}/ProdutoListarController?txId=<%=produto.getId()%>&action=Alterar">
                <img src="img/editar.png" style="width:40px;height:40px;">
            </a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/ProdutoListarController?txId=<%=produto.getId()%>&action=Remover">
                <img src="img/deletar.png" style="width:40px;height:40px;">
            </a>
        </td>
    </tr>

    <% }%>
</table>
<br>
<br>
<form action="FuncionarioController" method="POST">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
