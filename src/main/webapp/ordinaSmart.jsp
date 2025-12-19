<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Tavolo" %>

<%
    String errore = (String) request.getAttribute("errore");
    List<Tavolo> tavoliLiberi = (List<Tavolo>) request.getAttribute("tavoliLiberi");
%>

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
            margin: 0;
        }
        .shell {
            max-width: 700px;
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
        .error {
            margin-bottom: 12px;
            padding: 8px 10px;
            border-radius: 10px;
            background: #7f1d1d;
            color: #fecaca;
            font-size: 13px;
        }
        .section-title {
            font-size: 14px;
            font-weight: 600;
            margin-top: 16px;
            margin-bottom: 8px;
        }
        .tavoli-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
            gap: 10px;
        }
        .tavolo-card {
            border-radius: 12px;
            padding: 10px 12px;
            background: #020617;
            border: 1px solid #4b5563;
            cursor: pointer;
            font-size: 13px;
        }
        .tavolo-card.selected {
            border-color: #fbbf24;
            box-shadow: 0 0 16px rgba(251,191,36,0.5);
        }
        .tavolo-num {
            font-weight: 600;
        }
        .tavolo-posti {
            font-size: 12px;
            color: #9ca3af;
        }
        .field {
            margin-top: 12px;
            display: flex;
            flex-direction: column;
            gap: 4px;
            font-size: 13px;
        }
        input, textarea {
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #1f2937;
            background: #020617;
            color: #e5e7eb;
            font-size: 13px;
        }
        input:focus, textarea:focus {
            outline: none;
            border-color: #f97316;
            box-shadow: 0 0 0 1px rgba(249,115,22,0.6);
        }
        textarea {
            resize: vertical;
            min-height: 60px;
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
        .tavolo-radio {
            display: none;
        }
    </style>
    <script>
        function selectTavolo(id) {
            // deseleziona tutte le card
            document.querySelectorAll('.tavolo-card').forEach(function(card) {
                card.classList.remove('selected');
            });
            // seleziona quella cliccata
            var card = document.getElementById('card-tavolo-' + id);
            if (card) card.classList.add('selected');

            // setta il radio associato
            var radio = document.getElementById('tavolo-' + id);
            if (radio) radio.checked = true;
        }
    </script>
</head>
<body>

<div class="shell">
    <h1>Ordina smart</h1>
    <p class="subtitle">
        Scegli il tavolo e l’orario; poi passerai al menù per scegliere i piatti.
        Al tuo arrivo sarà già tutto pronto.
    </p>

    <% if (errore != null && !errore.isEmpty()) { %>
        <div class="error"><%= errore %></div>
    <% } %>

    <form action="ordinaSmart" method="post">

        <!-- Sezione scelta tavolo -->
        <div class="section-title">1. Scegli un tavolo disponibile</div>

        <div class="tavoli-grid">
            <%
                if (tavoliLiberi != null && !tavoliLiberi.isEmpty()) {
                    for (Tavolo t : tavoliLiberi) {
            %>
            <label class="tavolo-card" id="card-tavolo-<%= t.getId() %>"
                   onclick="selectTavolo(<%= t.getId() %>)">
                <input class="tavolo-radio"
                       type="radio"
                       id="tavolo-<%= t.getId() %>"
                       name="tavoloId"
                       value="<%= t.getId() %>">
                <div class="tavolo-num">Tavolo <%= t.getNumero() %></div>
                <div class="tavolo-posti"><%= t.getPosti() %> posti</div>
            </label>
            <%
                    }
                } else {
            %>
            <div style="font-size:13px; color:#fca5a5;">
                Nessun tavolo disponibile in questo momento.
            </div>
            <%
                }
            %>
        </div>

        <!-- Sezione data/orario/persone -->
        <div class="section-title">2. Dettagli prenotazione</div>

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

        <div class="field">
            <label for="persone">Numero di persone</label>
            <input id="persone" name="persone" type="number" min="1" max="20" value="2" required>
        </div>

        <div class="field">
            <label for="note">Richieste particolari (opzionale)</label>
            <textarea id="note" name="note"
                      placeholder="Es. compleanno, intolleranze, tavolo vicino finestra..."></textarea>
        </div>

        <button type="submit">Procedi al menù</button>
    </form>
</div>

</body>
</html>
