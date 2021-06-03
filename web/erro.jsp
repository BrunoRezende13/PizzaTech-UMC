<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ocorreu um erro</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<fieldset>
    <legend>Ocorreu um erro</legend>
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

        String rollback = (String) request.getAttribute("rollback");
        if (rollback != null) {
    %>
    <a href="<%=rollback%>">Voltar</a>
    <%
        }
    %>

</fieldset>
</body>
</html>