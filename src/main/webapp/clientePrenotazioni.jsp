<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Prenotazione" %>
<%@ page import="Controller.Bean.Utente" %>

<%
    List<Prenotazione> lista = (List<Prenotazione>) request.getAttribute("listaPrenotazioni");
    Utente u = (Utente) session.getAttribute("utenteLoggato");
    String nome = (u != null && u.getNome() != null) ? u.getNome() : "Cliente";
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Le tue prenotazioni - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #020617;
            color: #e5e7eb;
            min-height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            padding: 30px 12px;
        }
        .shell {
            max-width: 820px;
            width: 100%;
            background: rgba(15,23,42,0.98);
            border-radius: 20px;
            padding: 20px 22px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.85);
            border: 1px solid rgba(148,163,184,0.4);
        }
        h1 {
            margin: 0 0 4px;
            font-size: 22px;
        }
        .subtitle {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 16px;
        }
        .top-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;
            gap: 8px;
        }
        .btn, .btn-ghost {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 8px 14px;
            border-radius: 999px;
            font-size: 13px;
            font-weight: 500;
            text-decoration: none;
            cursor: pointer;
            border: none;
        }
        .btn {
            background: linear-gradient(135deg, #f97316, #facc15);
            color: #111827;
        }
        .btn-ghost {
            background: transparent;
            color: #e5e7eb;
            border: 1px solid rgba(148,163,184,0.8);
        }
        .btn:hover {
            box-shadow: 0 0 14px rgba(250,204,21,0.7);
        }
        .btn-ghost:hover {
            border-color: #f97316;
            box-shadow: 0 0 12px rgba(249,115,22,0.7);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            font-size: 13px;
        }
        th, td {
            padding: 8px 6px;
            border-bottom: 1px solid rgba(55,65,81,0.8);
            text-align: left;
        }
        th {
            font-size: 12px;
            color: #9ca3af;
            text-transform: uppercase;
            letter-spacing: 0.08em;
        }
        tr:last-child td {
            border-bottom: none;
        }
        .col-azioni {
            text-align: right;
            white-space: nowrap;
        }
        .badge-tipo {
            font-size: 11px;
            padding: 2px 8px;
            border-radius: 999px;
            border: 1px solid rgba(148,163,184,0.8);
        }
        .badge-soletavolo { border-color: #fbbf24; color: #fbbf24; }
        .badge-tavoloordine { border-color: #22c55e; color: #22c55e; }
        .label-vuoto {
            margin-top: 14px;
            font-size: 14px;
            color: #9ca3af;
        }

        @media (max-width: 640px) {
            table, thead, tbody, th, td, tr {
                display: block;
            }
            thead { display: none; }
            tr {
                border-bottom: 1px solid rgba(55,65,81,0.8);
                margin-bottom: 8px;
                padding-bottom: 6px;
            }
            td {
                border: none;
                padding: 4px 0;
            }
            td::before {
                content: attr(data-label);
                display: block;
                font-size: 11px;
                color: #9ca3af;
                text-transform: uppercase;
                letter-spacing: 0.08em;
            }
            .col-azioni {
                text-align: left;
                margin-top: 4px;
            }
        }
    </style>
</head>
<body>
<div class="shell">
    <div class="top-actions">
        <div>
            <h1>Le tue prenotazioni</h1>
            <div class="subtitle">
                Ciao <strong><%= nome %></strong>, qui trovi tutte le prenotazioni registrate a tuo nome.
            </div>
        </div>
        <div>
            <a href="clienteHome.jsp" class="btn-ghost">Area riservata</a>
            <a href="prenotaTavolo" class="btn">+ Nuova prenotazione</a>
        </div>
    </div>

    <%
        if (lista == null || lista.isEmpty()) {
    %>
        <div class="label-vuoto">
            Non hai ancora nessuna prenotazione attiva.
            Puoi crearne una con il pulsante <strong>“Nuova prenotazione”</strong>.
        </div>
    <%
        } else {
    %>
    <table>
        <thead>
        <tr>
            <th>Data</th>
            <th>Ora</th>
            <th>Persone</th>
            <th>Tipo</th>
            <th>Note</th>
            <th class="col-azioni">Azioni</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Prenotazione p : lista) {
                String tipoLabel = p.getTipo();
                String badgeClass = "badge-tipo";
                if ("SOLO_TAVOLO".equalsIgnoreCase(tipoLabel)) {
                    badgeClass += " badge-soletavolo";
                } else if ("TAVOLO_E_ORDINE".equalsIgnoreCase(tipoLabel)) {
                    badgeClass += " badge-tavoloordine";
                }
        %>
        <tr>
            <td data-label="Data"><%= p.getDataPrenotazione() %></td>
            <td data-label="Ora"><%= p.getOraPrenotazione() %></td>
            <td data-label="Persone"><%= p.getNumPersone() %></td>
            <td data-label="Tipo">
                <span class="<%= badgeClass %>"><%= tipoLabel %></span>
            </td>
            <td data-label="Note"><%= p.getNote() != null ? p.getNote() : "" %></td>
            <td data-label="Azioni" class="col-azioni">
                <form action="prenotazioni" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="idPrenotazione" value="<%= p.getId() %>">
                    <button type="submit" class="btn-ghost"
                            onclick="return confirm('Vuoi davvero cancellare questa prenotazione?');">
                        Elimina
                    </button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>
</body>
</html>
