<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Prenota & Ordina in anticipo - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #0f172a;
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
            max-width: 440px;
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
        label {
            display: block;
            font-size: 13px;
            margin: 10px 0 4px;
        }
        input, textarea {
            width: 100%;
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #4b5563;
            background: #020617;
            color: #e5e7eb;
            font-size: 13px;
        }
        textarea {
            resize: vertical;
            min-height: 60px;
        }
        .btn {
            margin-top: 16px;
            width: 100%;
            padding: 10px 14px;
            border-radius: 999px;
            border: none;
            background: linear-gradient(135deg, #f97316, #facc15);
            color: #111827;
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
    </style>
</head>
<body>
<div class="card">
    <h1>Prenota & Ordina in anticipo</h1>
    <div class="subtitle">
        Prenoti il tavolo, poi scegli i piatti dal menù.
        Quando arrivi è già tutto pronto.
    </div>

    <%
        String errore = (String) request.getAttribute("errore");
        if (errore != null) {
    %>
    <div class="errore"><%= errore %></div>
    <%
        }
    %>

    <form action="prenotaETOrdina" method="post">
        <label for="data">Data</label>
        <input type="date" id="data" name="data" required>

        <label for="ora">Ora</label>
        <input type="time" id="ora" name="ora" required>

        <label for="persone">Numero di persone</label>
        <input type="number" id="persone" name="persone" min="1" required>

        <label for="note">Note aggiuntive (opzionale)</label>
        <textarea id="note" name="note" placeholder="Intolleranze, ricorrenze, richieste speciali..."></textarea>

        <button type="submit" class="btn">Vai al menù e scegli i piatti</button>
    </form>
</div>
</body>
</html>
