<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.produto.Produto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exibir Produto</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }

    Produto produto = (Produto) session.getAttribute("produto_pesquisa");

    if(produto == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Produto</h1>

<form action="ProdutoController" method="POST">
    <br>
    Id.........: <%=produto.getId()%> <br>
    Nome.......: <%=produto.getNome()%> <br>
    Descrição........: <%=produto.getDescricao()%> <br>
    Ingredientes......: <%=produto.getIngredientes()%> <br>
    Preço......: R$<%=produto.getValor()%> <br>
    Estoque......: <%=produto.getEstoque()%> <br>
    Tipo......: <%=produto.getTipo()%> <br>

    <br>
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
