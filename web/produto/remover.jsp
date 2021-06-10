<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Remover Produto</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.GERENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }
%>

<h1>Remover Produto</h1>
<form action="ProdutoController" method="POST">
    <br>
    Id..: <input type="number" name="txId"> <br>

    <br>
    <input type="submit" name="action" value="Remover">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
