<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.Funcionario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Alterar Funcionário</title>
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

    Funcionario funcionario = (Funcionario) session.getAttribute("funcionario_pesquisa");
    if(funcionario == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Cadastrar Funcionario</h1>
<form action="FuncionarioController" method="POST">
    <br>
    Id.......: <%=funcionario.getId()%> <br>
    Nome.......: <input type="text" name="txNome" value="<%=funcionario.getNome()%>"> <br>
    Cpf........: <input type="text" name="txCpf" value="<%=funcionario.getCpf()%>"> <br>
    Email......: <input type="email" name="txEmail" value="<%=funcionario.getEmail()%>"> <br>
    Senha......: <input type="password" name="txSenha"> <br>
    Level de Acesso: <select name="selLevel" value="<%=funcionario.getLevelAcesso().getId()%>">
    <%
        for (LevelAcesso cat : LevelAcesso.values()) {
    %>
    <option value="<%=cat.getId()%>"><%=cat.getName()%>
    </option>
    <%
        }
    %>
    </select>
    <br>
    <br>
    <input type="submit" name="action" value="Alterar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
