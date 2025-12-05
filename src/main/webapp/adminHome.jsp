<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Controller.Bean.Utente" %>
<%
    Utente u = (Utente) session.getAttribute("utenteLoggato");
    if (u == null || !"ADMIN".equals(u.getRuolo())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Area Admin</title>
</head>
<body>
<h2>Ciao Admin <%= u.getNome() %>!</h2>
<p>Qui metterai gestione piatti, bevande, utenti, ecc.</p>
</body>
</html>
