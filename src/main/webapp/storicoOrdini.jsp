<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Ordine" %>

<%
    List<Ordine> lista = (List<Ordine>) request.getAttribute("listaOrdini");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Storico ordini - GugaRistò</title>
    <style>
        body {
            min-height: 100vh;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            color: #f5f5f5;
            background: radial-gradient(circle at 0% 0%, #1a237e, #000);
        }
        .wrapper {
            width: 100%;
            max-width: 960px;
            padding: 24px;
        }
        .panel {
            background: rgba(10, 10, 25, 0.95);
            border-radius: 24px;
            border: 1px solid rgba(255, 255, 255, 0.12);
            box-shadow: 0 20px 50px rgba(0,0,0,0.8);
            padding: 22px 22px 18px;
        }
        h1 {
            font-size: 22px;
            margin-bottom: 6px;
        }
        .subtitle {
            font-size: 13px;
            color: rgba(255,255,255,0.7);
            margin-bottom: 16px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
        }
        th, td {
            padding: 8px 10px;
            text-align: left;
            vertical-align: top;
        }
        th {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.14em;
            color: rgba(255,255,255,0.7);
            border-bottom: 1px solid rgba(255,255,255,0.2);
        }
        tr:nth-child(even) td {
            background: rgba(255,255,255,0.02);
        }

        .badge-stato {
            display: inline-flex;
            align-items: center;
            padding: 3px 8px;
            border-radius: 999px;
            font-size: 11px;
            gap: 6px;
        }
        .dot {
            width: 7px;
            height: 7px;
            border-radius: 50%;
        }
        .stato-preparazione {
            background: rgba(255, 214, 0, 0.12);
            border: 1px solid rgba(255, 235, 59, 0.7);
            color: #ffeb3b;
        }
        .stato-preparazione .dot { background: #ffeb3b; }

        .stato-consegna {
            background: rgba(3, 169, 244, 0.12);
            border: 1px solid rgba(3, 169, 244, 0.7);
            color: #4fc3f7;
        }
        .stato-consegna .dot { background: #4fc3f7; }

        .stato-consegnato {
            background: rgba(76, 175, 80, 0.12);
            border: 1px solid rgba(76, 175, 80, 0.7);
            color: #81c784;
        }
        .stato-consegnato .dot { background: #81c784; }

        .stato-annullato {
            background: rgba(244, 67, 54, 0.12);
            border: 1px solid rgba(244, 67, 54, 0.7);
            color: #e57373;
        }
        .stato-annullato .dot { background: #e57373; }

        .btn-annulla {
            border: none;
            border-radius: 999px;
            padding: 4px 10px;
            font-size: 11px;
            cursor: pointer;
            background: rgba(244, 67, 54, 0.85);
            color: #fff;
        }
        .btn-annulla:hover {
            background: rgba(211, 47, 47, 1);
        }

        .empty {
            font-size: 13px;
            color: rgba(255,255,255,0.7);
            margin-top: 8px;
        }
        .top-actions {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 10px;
            font-size: 12px;
        }
        .top-actions a {
            color: #ffeb3b;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class="panel">
        <div class="top-actions">
            <!-- se hai un controller grafico per clienteHome, meglio href="clienteHome" -->
            <a href="clienteHome.jsp">&larr; Torna all'area cliente</a>
        </div>
        <h1>Storico ordini</h1>
        <div class="subtitle">
            Qui trovi tutti i tuoi ordini: in preparazione, in consegna, consegnati e annullati.
        </div>

        <%
            if (lista == null || lista.isEmpty()) {
        %>
        <div class="empty">
            Non hai ancora effettuato ordini. Prova dalla sezione <strong>Ordina da casa</strong> o <strong>Ordina smart</strong>.
        </div>
        <%
            } else {
        %>
        <table>
            <thead>
            <tr>
                <th>Cliente</th>
                <th>Data</th>
                <th>Orario</th>
                <th>Tavolo</th>
                <th>Cosa hai ordinato</th>
                <th>Totale</th>
                <th>Stato</th>
                <th>Azioni</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Ordine o : lista) {

                    String statoLogico = o.getStatoLogico();
                    String cssStato;
                    if ("IN_PREPARAZIONE".equals(statoLogico)) {
                        cssStato = "stato-preparazione";
                    } else if ("IN_CONSEGNA".equals(statoLogico)) {
                        cssStato = "stato-consegna";
                    } else if ("CONSEGNATO".equals(statoLogico)) {
                        cssStato = "stato-consegnato";
                    } else {
                        cssStato = "stato-annullato";
                    }

                    String riepilogo = (o.getRiepilogoPiatti() != null)
                            ? o.getRiepilogoPiatti()
                            : "-";

                    String orario = (o.getOrarioPrenotazione() != null)
                            ? o.getOrarioPrenotazione()
                            : "-";

                    String tavolo = (o.getIdTavolo() != null)
                            ? "#" + o.getIdTavolo()
                            : "-";
            %>
            <tr>
                <td><%= o.getNomeCliente() %></td>
                <td><%= o.getDataOra() %></td>
                <td><%= orario %></td>
                <td><%= tavolo %></td>
                <td><%= riepilogo %></td>
                <td><%= o.getTotale() %> €</td>
                <td>
                    <span class="badge-stato <%= cssStato %>">
                        <span class="dot"></span>
                        <span><%= statoLogico.replace("_", " ") %></span>
                    </span>
                </td>
                <td>
                    <%
                        if ("IN_PREPARAZIONE".equals(statoLogico)) {
                    %>
                    <form action="storicoOrdini" method="post" style="display:inline;">
                        <input type="hidden" name="idOrdine" value="<%= o.getId() %>">
                        <button type="submit" class="btn-annulla">
                            Annulla ordine
                        </button>
                    </form>
                    <%
                        } else {
                    %>
                    —
                    <%
                        }
                    %>
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
</div>
</body>
</html>
