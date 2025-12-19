<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            background: #020617;
            color: #e5e7eb;
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background: rgba(15, 23, 42, 0.9);
            border-radius: 16px;
            padding: 24px;
        }
        h1 {
            margin-top: 0;
            margin-bottom: 16px;
        }
        .field {
            margin-bottom: 12px;
        }
        label {
            display: block;
            font-size: 14px;
            margin-bottom: 4px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px 10px;
            border-radius: 8px;
            border: 1px solid #374151;
            background: #020617;
            color: #e5e7eb;
        }
        .btn {
            padding: 8px 16px;
            border-radius: 999px;
            border: none;
            background: #22c55e;
            color: #022c22;
            font-weight: 600;
            cursor: pointer;
        }
        .errore {
            background: rgba(239,68,68,0.15);
            border: 1px solid rgba(248,113,113,0.6);
            color: #fecaca;
            padding: 10px 14px;
            border-radius: 12px;
            margin-bottom: 16px;
            font-size: 14px;
        }
        a.link {
            color: #60a5fa;
            font-size: 14px;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Crea un nuovo account</h1>

    <%
        String errore = (String) request.getAttribute("erroreRegistrazione");
        if (errore != null) {
    %>
        <div class="errore"><%= errore %></div>
    <%
        }
        String username = (String) request.getAttribute("username");
        String nome = (String) request.getAttribute("nome");
        String cognome = (String) request.getAttribute("cognome");
    %>

    <form method="post" action="<%= request.getContextPath() %>/registrazione">

        <div class="field">
            <label for="username">Username *</label>
            <input type="text" id="username" name="username"
                   value="<%= (username != null ? username : "") %>">
        </div>

        <div class="field">
            <label for="password">Password *</label>
            <input type="password" id="password" name="password">
        </div>

        <div class="field">
            <label for="confermaPassword">Conferma password *</label>
            <input type="password" id="confermaPassword" name="confermaPassword">
        </div>

        <div class="field">
            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome"
                   value="<%= (nome != null ? nome : "") %>">
        </div>

        <div class="field">
            <label for="cognome">Cognome</label>
            <input type="text" id="cognome" name="cognome"
                   value="<%= (cognome != null ? cognome : "") %>">
        </div>

        <button type="submit" class="btn">Registrati</button>
    </form>

    <p style="margin-top:16px;">
        Hai già un account?
        <a class="link" href="<%= request.getContextPath() %>/login">Vai al login</a>
    </p>
</div>

</body>
</html>
