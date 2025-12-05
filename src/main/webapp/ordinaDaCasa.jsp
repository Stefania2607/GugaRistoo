<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Ordina da casa - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #020617;
            color: #e5e7eb;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            background: rgba(15,23,42,0.96);
            padding: 24px 26px;
            border-radius: 18px;
            max-width: 460px;
            width: 100%;
            box-shadow: 0 20px 40px rgba(0,0,0,0.85);
        }
        h1 {
            margin: 0 0 4px;
            font-size: 21px;
        }
        .subtitle {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 18px;
        }
        label {
            display: block;
            font-size: 13px;
            margin: 10px 0 4px;
        }
        input, textarea, select {
            width: 100%;
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #4b5563;
            background: #020617;
            color: #e5e7eb;
            font-size: 13px;
        }
        .btn {
            margin-top: 16px;
            width: 100%;
            padding: 10px 14px;
            border-radius: 999px;
            border: none;
            background: linear-gradient(135deg, #22c55e, #a3e635);
            color: #052e16;
            font-weight: 600;
            cursor: pointer;
        }
        .errore {
            margin-bottom: 10px;
            padding: 8px;
            border-radius: 10px;
            background: rgba(239,68,68,0.15);
            border: 1px solid rgba(239,68,68,0.7);
            font-size: 13px;
        }
        small {
            font-size: 11px;
            color: #9ca3af;
        }
    </style>
</head>
<body>
<div class="card">
    <h1>Ordina da casa</h1>
    <div class="subtitle">
        Scegli se ricevere i piatti a domicilio o ritirarli al ristorante.
    </div>

    <%
        String errore = (String) request.getAttribute("errore");
        if (errore != null) {
    %>
    <div class="errore"><%= errore %></div>
    <%
        }
    %>

    <form action="ordinaDaCasa" method="post">
        <label for="modalita">Modalità di servizio</label>
        <select name="modalita" id="modalita" required>
            <option value="">Seleziona...</option>
            <option value="DOMICILIO">Consegna a domicilio</option>
            <option value="ASPORTO">Ritiro al ristorante</option>
        </select>

        <label for="indirizzo">Indirizzo (solo per domicilio)</label>
        <input type="text" id="indirizzo" name="indirizzo" placeholder="Via, numero, città">

        <label for="orario">Orario desiderato (opzionale)</label>
        <input type="time" id="orario" name="orario">

        <small>
            Dopo aver confermato, potrai scegliere i piatti dal menù
            e completare l'ordine dal tuo carrello.
        </small>

        <button type="submit" class="btn">Vai al menù</button>
    </form>
</div>
</body>
</html>
