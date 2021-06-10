<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.produto.Produto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Alterar Produto</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.GERENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<a href="../login.jsp">Voltar</a>
<%
        return;
    }

    Produto produto = (Produto) session.getAttribute("produto_pesquisa");
    if(produto == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Alterar Produto</h1>
<form action="ProdutoController" method="POST">
    <br>

    Id.......: <%=produto.getId()%> <br>
    Nome.........: <input type="text" name="txNome" value="<%=produto.getNome()%>"> <br>
    Descrição......: <input type="text" name="txDescricao" value="<%=produto.getDescricao()%>"> <br>
    Ingredientes...: <input type="text" name="txIngrediente" value="<%=produto.getIngredientes()%>"> <br>
    Valor........: R$<input type="number" name="txValor" value="<%=produto.getValor()%>"> <br>
    Estoque..: <input type="number" name="txEstoque" value="<%=produto.getEstoque()%>"> <br>

    Tipo do produto..:
    <input type="radio" id="produto" name="produto_modo" value="produto" checked>
    <label for="produto">Normal</label>
    <input type="radio" id="pizza" name="produto_modo" value="pizza">
    <label for="pizza">Pizza</label><br>
    <br>
    <br>
    <input type="submit" name="action" value="Alterar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
