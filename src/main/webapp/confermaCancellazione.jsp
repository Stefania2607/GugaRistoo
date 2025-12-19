<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Conferma cancellazione prenotazione</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            background: #020617;
            color: #e5e7eb;
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: rgba(15, 23, 42, 0.9);
            border-radius: 16px;
            padding: 24px;
        }
        .buttons {
            margin-top: 20px;
            display: flex;
            gap: 12px;
        }
        .btn {
            padding: 8px 16px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-danger {
            background: rgba(239, 68, 68, 0.9);
            color: #fef2f2;
        }
        .btn-secondary {
            background: rgba(148, 163, 184, 0.3);
            color: #e5e7eb;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Conferma cancellazione</h1>

    <p>Vuoi davvero cancellare questa prenotazione?</p>

    <%
        String idPrenotazione = (String) request.getAttribute("idPrenotazione");
        if (idPrenotazione == null) {
            idPrenotazione = request.getParameter("idPrenotazione");
        }
    %>

    <div class="buttons">
        <!-- CONFERMA: POST CHE ESEGUE LA DELETE -->
        <form method="post"
              action="<%= request.getContextPath() %>/cancellaPrenotazione">
            <input type="hidden" name="idPrenotazione" value="<%= idPrenotazione %>">
            <button type="submit" class="btn btn-danger">Conferma cancellazione</button>
        </form>

        <!-- ANNULLA: TORNA ALLA LISTA -->
        <form method="get"
              action="<%= request.getContextPath() %>/prenotazioni">
            <button type="submit" class="btn btn-secondary">Annulla</button>
        </form>


    </div>
</div>

</body>
</html>
