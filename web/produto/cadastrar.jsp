<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastrar Produto</title>
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

<h1>Cadastrar Produto</h1>
<form action="ProdutoController" method="POST">
    <br>
    Nome.........: <input type="text" name="txNome"> <br>
    Descrição......: <input type="text" name="txDescricao"> <br>
    Ingredientes...: <input type="text" name="txIngrediente"> <br>
    Valor........: R$ <input type="number" step="0.01" name="txValor"> <br>
    Estoque..: <input type="number" name="txEstoque"> <br>

    Tipo do produto..:
    <input type="radio" id="produto" name="produto_modo" value="produto" checked>
    <label for="produto">Bebida</label>
    <input type="radio" id="pizza" name="produto_modo" value="pizza">
    <label for="pizza">Pizza</label><br>

    <br>
    <br>
    <input type="submit" name="action" value="Cadastrar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>