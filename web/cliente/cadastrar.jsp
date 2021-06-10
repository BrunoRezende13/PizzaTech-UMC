<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastrar Cliente</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<a href="../login.jsp">Voltar</a>
<%
        return;
    }
%>

<h1>Cadastrar Cliente</h1>
<form action="ClienteController" method="POST">
    <br>
    Nome.......: <input type="text" name="txNome"> <br>
    Cpf........: <input type="text" name="txCpf"> <br>
    Endereço......: <input type="text" name="txEndereco"> <br>
    Telefone......: <input type="text" name="txTelefone"> <br>

    <br>
    <br>
    <input type="submit" name="action" value="Cadastrar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
