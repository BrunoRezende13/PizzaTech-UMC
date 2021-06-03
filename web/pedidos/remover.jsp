<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Remover Pedido</title>
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

<h1>Remover Pedido</h1>
<form action="PedidoController" method="POST">
    <br>
    Id..: <input type="number" name="txId"> <br>

    <br>
    <input type="submit" name="action" value="Remover">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
