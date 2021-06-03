<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.Funcionario" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listar Funcionários</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.GERENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }

    Funcionario funcionario = (Funcionario) session.getAttribute("funcionario_pesquisa");

    if(funcionario == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Funcionário</h1>

<form action="FuncionarioController" method="POST">
    <br>
    Id.........: <%=funcionario.getId()%> <br>
    Nome.......: <%=funcionario.getNome()%> <br>
    Cpf........: <%=funcionario.getCpf()%> <br>
    Email......: <%=funcionario.getEmail()%> <br>
    Level de Acesso: <%=funcionario.getLevelAcesso().getName()%> <br>

    <br>
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
