<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="Controller.Bean.Utente" %>

<%
    String contextPath = request.getContextPath();

    Utente u = (Utente) session.getAttribute("utenteLoggato");
    String nomeCliente = (u != null && u.getNome() != null) ? u.getNome() : "Cliente";

    String messaggio = (String) request.getAttribute("messaggio");

    String dataPren = (String) request.getAttribute("data");
    String oraPren  = (String) request.getAttribute("ora");
    Object personeObj = request.getAttribute("persone");
    String personePren = (personeObj != null) ? personeObj.toString() : null;

    String notePren = (String) request.getAttribute("note");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Conferma prenotazione - GugaRistò</title>

    <link href="https://fonts.googleapis.com/css2?family=Cinzel:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        * { box-sizing: border-box; }

        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
        }

        body {
            font-family: "Cinzel", serif;
            background: radial-gradient(circle at top, #111827, #020617);
            color: #f9fafb;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .shell {
            max-width: 640px;
            width: 100%;
            padding: 26px 28px 24px;
            border-radius: 24px;
            background: radial-gradient(circle at top left,
                    rgba(249,115,22,0.25),
                    rgba(15,23,42,0.98));
            box-shadow:
                    0 22px 45px rgba(0,0,0,0.85),
                    0 0 0 1px rgba(148,163,184,0.4);
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 14px;
        }

        .logo-title {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .logo-circle {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: linear-gradient(135deg, #f59e0b, #facc15);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 18px;
            color: #111827;
            box-shadow: 0 0 14px rgba(250,204,21,0.7);
        }

        .title-text h1 {
            margin: 0;
            font-size: 20px;
            letter-spacing: 0.06em;
            text-transform: uppercase;
        }

        .subtitle {
            font-size: 11px;
            color: #d1d5db;
            letter-spacing: 0.18em;
            text-transform: uppercase;
        }

        .badge-ok {
            padding: 4px 10px;
            border-radius: 999px;
            border: 1px solid rgba(34,197,94,0.8);
            background: rgba(22,163,74,0.2);
            font-size: 11px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .badge-dot {
            width: 7px;
            height: 7px;
            border-radius: 50%;
            background: #22c55e;
            box-shadow: 0 0 6px rgba(34,197,94,0.9);
        }

        .content-main {
            margin-top: 6px;
            margin-bottom: 16px;
        }

        .hello {
            font-size: 13px;
            color: #e5e7eb;
        }

        .hello span {
            font-weight: 600;
            color: #fbbf24;
        }

        .msg {
            margin-top: 10px;
            font-size: 14px;
            line-height: 1.5;
        }

        .summary {
            margin-top: 14px;
            padding: 10px 12px;
            border-radius: 14px;
            background: rgba(15,23,42,0.9);
            border: 1px solid rgba(148,163,184,0.6);
            font-size: 13px;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 4px;
        }

        .summary-label {
            color: #9ca3af;
            font-size: 12px;
        }

        .summary-value {
            font-weight: 500;
        }

        .summary-note {
            margin-top: 6px;
            font-size: 12px;
            color: #d1d5db;
        }

        .actions {
            margin-top: 18px;
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

        .btn-main,
        .btn-ghost {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 9px 16px;
            border-radius: 999px;
            font-size: 13px;
            font-weight: 500;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.18s ease-out;
        }

        .btn-main {
            border: none;
            background: linear-gradient(135deg, #f97316, #facc15);
            color: #111827;
        }

        .btn-main:hover {
            box-shadow: 0 0 16px rgba(250,204,21,0.65);
            transform: translateY(-1px);
        }

        .btn-ghost {
            border: 1px solid rgba(148,163,184,0.8);
            background: rgba(15,23,42,0.9);
            color: #e5e7eb;
        }

        .btn-ghost:hover {
            border-color: rgba(249,115,22,0.9);
            box-shadow: 0 0 14px rgba(249,115,22,0.45);
            transform: translateY(-1px);
        }

        .footer {
            margin-top: 14px;
            font-size: 11px;
            color: #9ca3af;
        }

        @media (max-width: 600px) {
            .shell {
                margin: 12px;
                padding: 20px 18px;
            }
            .header {
                flex-direction: column;
                align-items: flex-start;
                gap: 8px;
            }
        }
    </style>
</head>
<body>

<div class="shell">
    <div class="header">
        <div class="logo-title">
            <div class="logo-circle">G</div>
            <div class="title-text">
                <h1>GugaRistò</h1>
                <div class="subtitle">Conferma prenotazione</div>
            </div>
        </div>
        <div class="badge-ok">
            <span class="badge-dot"></span>
            Prenotazione registrata
        </div>
    </div>

    <div class="content-main">
        <div class="hello">
            Grazie <span><%= nomeCliente %></span>,
        </div>

        <div class="msg">
            <%
                if (messaggio != null) {
            %>
                <%= messaggio %>
            <%
                } else {
            %>
                la tua prenotazione è stata registrata correttamente nei nostri sistemi.
            <%
                }
            %>
        </div>

        <%
            // Mostro riepilogo solo se ho i dati
            if (dataPren != null || oraPren != null || personePren != null) {
        %>
        <div class="summary">
            <div class="summary-row">
                <div class="summary-label">Data</div>
                <div class="summary-value"><%= dataPren != null ? dataPren : "-" %></div>
            </div>
            <div class="summary-row">
                <div class="summary-label">Ora</div>
                <div class="summary-value"><%= oraPren != null ? oraPren : "-" %></div>
            </div>
            <div class="summary-row">
                <div class="summary-label">Persone</div>
                <div class="summary-value"><%= personePren != null ? personePren : "-" %></div>
            </div>

            <%
                if (notePren != null && !notePren.isBlank()) {
            %>
            <div class="summary-note">
                <strong>Note:</strong> <%= notePren %>
            </div>
            <%
                }
            %>
        </div>
        <%
            }
        %>

        <div class="actions">
            <!-- Torna all'area riservata cliente -->
            <a href="clienteHome.jsp" class="btn-main">
                Torna alla tua area riservata
            </a>

            <!-- Vai al menù: solo visualizza o, se vuoi, in futuro anche ordini -->
            <a href="menu" class="btn-ghost">
                Sfoglia il menù
            </a>
        </div>

        <div class="footer">
            In qualsiasi momento potrai rivedere o modificare le tue prenotazioni
            dall'area cliente, quando questa funzionalità sarà attiva.
        </div>
    </div>
</div>

</body>
</html>
