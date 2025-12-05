<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Ordina smart - GugaRistò</title>
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
        .shell {
            max-width: 520px;
            width: 100%;
            padding: 24px 26px;
            border-radius: 20px;
            background: rgba(15, 23, 42, 0.96);
            box-shadow: 0 20px 40px rgba(0,0,0,0.85);
        }
        h1 {
            margin: 0 0 6px;
            font-size: 22px;
        }
        .subtitle {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 18px;
        }
        .options {
            display: grid;
            gap: 12px;
        }
        .opt-card {
            border-radius: 14px;
            padding: 14px 16px;
            background: #020617;
            border: 1px solid #4b5563;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
        }
        .opt-card:hover {
            border-color: #fbbf24;
            box-shadow: 0 0 16px rgba(251,191,36,0.5);
        }
        .opt-title {
            font-weight: 600;
            margin-bottom: 4px;
        }
        .opt-desc {
            font-size: 13px;
            color: #9ca3af;
        }
    </style>
</head>
<body>
<div class="shell">
    <h1>Ordina smart</h1>
    <div class="subtitle">
        Scegli il tipo di esperienza che vuoi fare.
    </div>

    <div class="options">
        <!-- Prenotazione + ordine in anticipo -->
        <a href="prenotaETOrdina" class="opt-card">
            <div class="opt-title">Prenota tavolo + Ordina in anticipo</div>
            <div class="opt-desc">
                Prenoti il tavolo e scegli subito i piatti.
                Quando arrivi è tutto pronto.
            </div>
        </a>

        <!-- Per ora ti rimanda a una servlet per delivery/asporto (puoi riusare /ordinaDaCasa) -->
        <a href="ordinaDaCasa" class="opt-card">
            <div class="opt-title">Ordina a domicilio / asporto</div>
            <div class="opt-desc">
                Scegli cosa mangiare e ricevilo a casa
                oppure ritira tutto al ristorante.
            </div>
        </a>
    </div>
</div>
</body>
</html>
