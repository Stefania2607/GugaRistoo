<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="controller.bean.Prenotazione" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Le tue prenotazioni - GugaRistò</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            background: #020617;
            color: #e5e7eb;
            margin: 0;
            padding: 24px;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
            background: rgba(15, 23, 42, 0.85);
            border-radius: 16px;
            padding: 24px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid rgba(148,163,184,0.4);
        }

        th {
            text-transform: uppercase;
            font-size: 12px;
            color: #9ca3af;
        }

        .btn-delete {
            background: rgba(239, 68, 68, 0.3);
            border: 1px solid rgba(248, 113, 113, 0.5);
            color: #fecaca;
            padding: 4px 10px;
            font-size: 12px;
            border-radius: 999px;
            cursor: pointer;
        }

        .empty {
            background: rgba(15,23,42,0.7);
            border: 1px dashed rgba(148,163,184,0.5);
            padding: 16px;
            border-radius: 12px;
            margin-top: 20px;
            font-size: 14px;
            color: #9ca3af;
        }

        a.link {
            color: #60a5fa;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="container">
    <%
        String notifica = (String) request.getAttribute("notificaPrenotazione");
        if (notifica != null) {
    %>
        <div style="
            margin-bottom: 16px;
            padding: 12px 16px;
            border-radius: 12px;
            background: rgba(34,197,94,0.15);
            border: 1px solid rgba(34,197,94,0.6);
            color: #bbf7d0;
            font-size: 14px;">
            <%= notifica %>
        </div>
    <%
        }
    %>

    <h1>Le tue prenotazioni</h1>
    <a class="link" href="<%= request.getContextPath() %>/clienteHome.jsp">
        &larr; Torna alla home
    </a>

    <%
        List<Prenotazione> lista = (List<Prenotazione>) request.getAttribute("listaPrenotazioni");
        if (lista == null || lista.isEmpty()) {
    %>

        <div class="empty">
            Non hai ancora nessuna prenotazione registrata.
        </div>

    <%
        } else {
    %>

        <table>
            <thead>
            <tr>
                <th>Data / Ora</th>
                <th>Tavolo</th>
                <th>Persone</th>
                <th>Tipo</th>
                <th>Stato</th>
                <th>Note</th>
                <th>Azioni</th>
            </tr>
            </thead>

            <tbody>
            <%
                for (Prenotazione p : lista) {
            %>
            <tr>
                <td>
                    <%= p.getDataPrenotazione() %><br>
                    <span style="font-size:12px; color:#9ca3af;">
                        <%= p.getOraPrenotazione() %>
                    </span>
                </td>

                <td>
                    <%
                        if (p.getTavoloNumero() != null) {
                    %>
                        Tavolo <%= p.getTavoloNumero() %>
                    <%
                        } else {
                    %>
                        <span style="color:#9ca3af; font-size:12px;">Non assegnato</span>
                    <%
                        }
                    %>
                </td>

                <td><%= p.getNumPersone() %></td>
                <td><%= p.getTipo() %></td>
                <td><%= (p.getStato() != null ? p.getStato() : "—") %></td>

                <td>
                    <%= (p.getNote() != null && !p.getNote().isEmpty())
                            ? p.getNote()
                            : "<span style='color:#9ca3af; font-size:12px;'>Nessuna nota</span>" %>
                </td>

                <td>
                    <!-- FORM CORRETTO -->
                    <form method="post" action="<%= request.getContextPath() %>/prenotazioni">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="idPrenotazione" value="<%= p.getId() %>">
                        <button class="btn-delete" type="submit">Cancella</button>
                    </form>
                </td>
            </tr>
            <%
                } // fine for
            %>
            </tbody>
        </table>

    <%
        } // fine else lista non vuota
    %>

</div>

</body>
</html>
