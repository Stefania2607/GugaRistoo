<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    // Eventuale messaggio di errore / successo passato dal controller
    String errore = (String) request.getAttribute("errorePrenotazione");
    String messaggio = (String) request.getAttribute("messaggioPrenotazione");

    // Eventuale username preso dalla sessione (se il cliente è loggato)
    String username = (String) session.getAttribute("username");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Prenota un tavolo - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #0f172a;
            color: #e5e7eb;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            margin: 0;
        }
        .card {
            background: rgba(15, 23, 42, 0.96);
            padding: 24px 26px;
            border-radius: 18px;
            max-width: 480px;
            width: 100%;
            box-shadow: 0 18px 40px rgba(0,0,0,0.8);
        }
        h1 {
            font-size: 20px;
            margin-bottom: 4px;
        }
        .subtitle {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 18px;
        }
        .field {
            margin-top: 12px;
            display: flex;
            flex-direction: column;
            gap: 4px;
            font-size: 13px;
        }
        label {
            color: #e5e7eb;
        }
        input, select, textarea {
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #1f2937;
            background: #020617;
            color: #e5e7eb;
            font-size: 13px;
        }
        textarea {
            resize: vertical;
            min-height: 60px;
        }
        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #f97316;
            box-shadow: 0 0 0 1px rgba(249,115,22,0.6);
        }
        .row {
            display: flex;
            gap: 10px;
        }
        .row > .field {
            flex: 1;
        }
        button {
            margin-top: 18px;
            width: 100%;
            padding: 10px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            background: linear-gradient(90deg, #f97316, #fdba74);
            color: #111827;
            font-weight: 600;
            font-size: 14px;
        }
        .error {
            margin-top: 10px;
            padding: 8px 10px;
            border-radius: 10px;
            background: #7f1d1d;
            color: #fecaca;
            font-size: 13px;
        }
        .success {
            margin-top: 10px;
            padding: 8px 10px;
            border-radius: 10px;
            background: #14532d;
            color: #bbf7d0;
            font-size: 13px;
        }
    </style>
</head>
<body>

<div class="card">
    <h1>Prenota un tavolo</h1>
    <p class="subtitle">
        Scegli data, orario e numero di persone.
        All'arrivo troverai il tavolo pronto.
    </p>

    <% if (errore != null && !errore.isEmpty()) { %>
        <div class="error"><%= errore %></div>
    <% } %>

    <% if (messaggio != null && !messaggio.isEmpty()) { %>
        <div class="success"><%= messaggio %></div>
    <% } %>
    <% if (messaggio != null && !messaggio.isEmpty()) { %>
        <div style="margin-top: 10px; text-align: left;">
            <a href="clienteHome.jsp"
               style="
                   display: inline-block;
                   padding: 8px 14px;
                   margin-top: 6px;
                   border-radius: 8px;
                   background: #1e293b;
                   color: #f1f5f9;
                   text-decoration: none;
                   font-size: 13px;
                   border: 1px solid #334155;
                   transition: background 0.2s;
               "
               onmouseover="this.style.background='#334155'"
               onmouseout="this.style.background='#1e293b'">
                ⬅ Torna all’area riservata
            </a>
        </div>
    <% } %>


    <!-- QUI metti l'URL della tua servlet di prenotazione -->
    <!-- Per esempio: PrenotaTavoloController mappato su /prenotaTavolo -->
    <form action="prenotaTavolo" method="post">

        <!-- Nome di chi prenota (precompilato se hai username in sessione) -->
        <div class="field">
            <label for="nomePrenotante">Nome di chi prenota</label>
            <input
                id="nomePrenotante"
                name="nomePrenotante"
                type="text"
                value="<%= (username != null ? username : "") %>"
                required
            >
        </div>

        <!-- Data e ora -->
        <div class="row">
            <div class="field">
                <label for="data">Data</label>
                <input id="data" name="data" type="date" required>
            </div>
            <div class="field">
                <label for="ora">Orario</label>
                <input id="ora" name="ora" type="time" required>
            </div>
        </div>

        <!-- Numero di persone -->
        <div class="field">
            <label for="numeroPersone">Numero di persone</label>
            <input
                id="numeroPersone"
                name="numeroPersone"
                type="number"
                min="1"
                max="20"
                value="2"
                required
            >
        </div>

        <!-- Eventuali richieste particolari -->
        <div class="field">
            <label for="note">Richieste particolari (opzionale)</label>
            <textarea
                id="note"
                name="note"
                placeholder="Es. tavolo vicino alla finestra, intolleranze, compleanno..."
            ></textarea>
        </div>

        <!-- Se vuoi selezionare una sala o una zona -->
        <!--
        <div class="field">
            <label for="zona">Zona / Sala</label>
            <select id="zona" name="zona">
                <option value="standard">Sala principale</option>
                <option value="esterna">Esterno</option>
                <option value="privata">Sala privata</option>
            </select>
        </div>
        -->

        <button type="submit">Conferma prenotazione</button>
    </form>
</div>

</body>
</html>
