<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    String errore = (String) request.getAttribute("errore");

    // Se arrivo da una validazione fallita, i parametri sono ancora nella request
    String dataVal     = request.getParameter("data")     != null ? request.getParameter("data")     : "";
    String oraVal      = request.getParameter("ora")      != null ? request.getParameter("ora")      : "";
    String personeVal  = request.getParameter("persone")  != null ? request.getParameter("persone")  : "2";
    String noteVal     = request.getParameter("note")     != null ? request.getParameter("note")     : "";
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Prenota e ordina - GugaRistò</title>
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
            max-width: 520px;
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
            margin-bottom: 12px;
        }
        .notice {
            font-size: 12px;
            color: #f97316;
            background: rgba(249,115,22,0.12);
            border: 1px solid rgba(249,115,22,0.4);
            padding: 8px 10px;
            border-radius: 10px;
            margin-bottom: 16px;
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
        input, textarea {
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
        input:focus, textarea:focus {
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
    </style>
</head>
<body>

<div class="card">
    <h1>Prenota e ordina</h1>
    <p class="subtitle">
        Prenota un tavolo e scegli in anticipo cosa mangiare.
        Quando arrivi al ristorante trovi già tutto pronto.
    </p>

    <div class="notice">
        Questa modalità è solo per il servizio <strong>al tavolo</strong>.<br>
        Non è disponibile la consegna a domicilio o l’asporto:
        la prenotazione crea un tavolo riservato e l’ordine è legato a quel tavolo.
    </div>

    <% if (errore != null && !errore.isEmpty()) { %>
        <div class="error"><%= errore %></div>
    <% } %>

    <!-- Il form parla con la servlet PrenotaETOrdinaController -->
    <form action="prenotaETOrdina" method="post">

        <div class="row">
            <div class="field">
                <label for="data">Data</label>
                <input id="data" name="data" type="date" value="<%= dataVal %>" required>
            </div>
            <div class="field">
                <label for="ora">Orario</label>
                <input id="ora" name="ora" type="time" value="<%= oraVal %>" required>
            </div>
        </div>

        <div class="field">
            <label for="persone">Numero di persone</label>
            <input
                id="persone"
                name="persone"
                type="number"
                min="1"
                max="20"
                value="<%= personeVal %>"
                required
            >
        </div>

        <div class="field">
            <label for="note">Richieste particolari (opzionale)</label>
            <textarea
                id="note"
                name="note"
                placeholder="Es. intolleranze, compleanno, tavolo lontano dalla porta..."
            ><%= noteVal %></textarea>
        </div>

        <button type="submit">Procedi al menù e ordina</button>
    </form>
</div>

</body>
</html>
