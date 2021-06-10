<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastrar Funcionario</title>
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
%>

<h1>Cadastrar Funcionario</h1>
<form action="FuncionarioController" method="POST">
    <br>
    Nome.......: <input type="text" name="txNome"> <br>
    Cpf........: <input type="text" name="txCpf"> <br>
    Email......: <input type="email" name="txEmail"> <br>
    Senha......: <input type="password" name="txSenha"> <br>
    Level de Acesso: <select name="selLevel">
    <%
        for(LevelAcesso cat : LevelAcesso.values()){
    %>
    <option value="<%=cat.getId()%>"> <%=cat.getName()%></option>
    <%
        }
    %>
</select>
    <br>
    <br>
    <input type="submit" name="action" value="Cadastrar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
