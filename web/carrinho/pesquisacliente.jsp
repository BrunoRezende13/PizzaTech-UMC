<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Iniciar Carrinho</title>
</head>
<body>

<%
    if (!Autenticador.isLogged(session)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }
%>

<h1>Pesquisar Cliente</h1>
<form action="CarrinhoClienteController" method="POST">
    <br>
    Digite o nome, número ou cpf do cliente
    <input type="text" name="txCampo"> <br>

    <br>
    <input type="submit" name="action" value="Pesquisar Cliente">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
