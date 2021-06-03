<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<%
    if(Autenticador.isLogged(session)) {
        response.sendRedirect("admin.jsp");
        return;
    }
%>

<fieldset>
    <legend>Login</legend>
    <br>
    <%
        String erro = (String) request.getAttribute("erro");
        if (erro != null) {
    %>
    <%=erro%>
    <br>
    <br>
    <%
        }
    %>

    <form name="FEntr" method="post" action="LoginController">
        Email: <input type="email" name="txEmail" required/><br/> <br/>
        Senha: <input type="password" name="txSenha" required/><br/> <br/>
        <br/>
        <input type="Submit" value="Entrar"/>
    </form>
</fieldset>
</body>
</html>