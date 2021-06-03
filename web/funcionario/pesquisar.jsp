<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pesquisar Funcionário</title>
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

<h1>Pesquisar Funcionário</h1>
<form action="FuncionarioController" method="POST">
    <br>
    Id..: <input type="number" name="txId"> <br>

    <br>
    <input type="submit" name="action" value="Pesquisar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
