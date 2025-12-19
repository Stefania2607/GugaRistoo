<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String nome = (String) request.getAttribute("nomeIntestatario");
    String email = (String) request.getAttribute("email");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Ordine completato - GugaRistò</title>
    <style>
        body {
            background: #020617;
            color: #e5e7eb;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            background: radial-gradient(circle at top, #0f172a, #020617);
            border-radius: 24px;
            padding: 28px 32px;
            width: 420px;
            text-align: center;
            box-shadow: 0 30px 80px rgba(15, 23, 42, 0.9);
        }

        .title {
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .icon {
            font-size: 40px;
            margin-bottom: 10px;
        }

        .subtitle {
            font-size: 14px;
            color: #9ca3af;
            margin-bottom: 18px;
        }

        .highlight {
            color: #facc15;
            font-weight: 600;
        }

        a {
            color: #38bdf8;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="card">
    <div class="icon">✅</div>
    <div class="title">Ordine completato!</div>
    <div class="subtitle">
        Grazie<% if (nome != null && !nome.isEmpty()) { %> <span class="highlight"><%= nome %></span><% } %>.<br>
        Ti abbiamo inviato una conferma all'indirizzo
        <% if (email != null && !email.isEmpty()) { %>
            <span class="highlight"><%= email %></span>.
        <% } else { %>
            email indicata.
        <% } %>
    </div>
    <p>
        Puoi tornare al menu e iniziare un nuovo ordine.
    </p>
    <p>
        <a href="<%= request.getContextPath() %>/menuOrdinaDaCasa">Torna al menu</a>
    </p>
    <div style="margin-top: 25px; text-align: center;">
        <a href="clienteHome.jsp"
           style="
                display: inline-block;
                padding: 10px 18px;
                border-radius: 10px;
                background: rgba(255, 215, 64, 0.15);
                border: 1px solid rgba(255, 215, 64, 0.5);
                color: #ffd740;
                text-decoration: none;
                font-size: 14px;
                transition: 0.25s ease;
           ">
            ← Torna all'area cliente
        </a>
    </div>
</div>


</body>
</html>
