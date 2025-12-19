<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Controller.Bean.Carrello" %>
<%@ page import="Controller.Bean.RigaCarrello" %>
<%@ page import="java.math.BigDecimal" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");

    if (carrello == null || carrello.isVuoto()) {
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Carrello - GugaRistò</title>
    <style>
        body {
            background: #020617;
            color: #e5e7eb;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            min-height: 100vh;
            margin: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        a {
            color: #38bdf8;
            text-decoration: none;
            font-weight: 500;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Il tuo carrello è vuoto</h2>
    <p><a href="<%= request.getContextPath() %>/menuOrdinaDaCasa">Torna al menu</a></p>
</body>
</html>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Carrello - GugaRistò</title>
    <style>
        body {
            background: #020617;
            color: #e5e7eb;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            margin: 0;
            padding: 40px 0 60px 0;
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
        }
        .svuota-btn {
            background: #ef4444;
            border: none;
            border-radius: 999px;
            padding: 10px 20px;
            font-size: 14px;
            font-weight: 600;
            color: #fff;
            cursor: pointer;
            margin-right: 12px;
            transition: 0.18s ease-in-out;
        }

        .svuota-btn:hover {
            background: #dc2626;
            transform: translateY(-1px);
            box-shadow: 0 12px 24px rgba(239, 68, 68, 0.35);
        }


        .wrapper {
            width: 80%;
            margin: 0 auto;
            max-width: 1100px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #0f172a;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 20px 40px rgba(15, 23, 42, 0.8);
        }

        th, td {
            padding: 14px 16px;
            border-bottom: 1px solid #1e293b;
            text-align: left;
        }

        tr:last-child td {
            border-bottom: none;
        }

        th {
            background: #1e293b;
            font-weight: 600;
        }

        .img-piatto {
            width: 70px;
            height: 70px;
            border-radius: 8px;
            object-fit: cover;
        }

        .qty-form {
            display: inline-block;
            margin: 0;
        }

        .qty-btn {
            background: #1e293b;
            border: 1px solid #334155;
            color: #e5e7eb;
            border-radius: 999px;
            width: 28px;
            height: 28px;
            font-size: 16px;
            cursor: pointer;
        }

        .qty-btn:hover {
            background: #38bdf8;
            border-color: #38bdf8;
            color: #020617;
        }

        .qty-value {
            display: inline-block;
            min-width: 24px;
            text-align: center;
        }

        .totale-row {
            margin-top: 16px;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            gap: 24px;
        }

        .totale-label {
            font-size: 18px;
            font-weight: 500;
        }

        .totale-valore {
            font-size: 22px;
            font-weight: 700;
            color: #facc15;
        }

        .checkout-btn {
            background: #22c55e;
            border: none;
            border-radius: 999px;
            padding: 10px 26px;
            font-size: 16px;
            font-weight: 600;
            color: #022c22;
            cursor: pointer;
        }

        .checkout-btn:hover {
            background: #16a34a;
        }

        .back-link {
            margin-bottom: 18px;
            display: flex;
            justify-content: flex-start;
        }

        .back-link a {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            border-radius: 999px;
            background: rgba(15, 23, 42, 0.9);
            border: 1px solid #1f2937;
            color: #e5e7eb;
            font-weight: 500;
            text-decoration: none;
            font-size: 14px;
            box-shadow: 0 10px 25px rgba(15, 23, 42, 0.8);
            transition: all 0.18s ease-in-out;
        }

        .back-link a::before {
            content: "←";
            font-size: 16px;
        }

        .back-link a:hover {
            background: #38bdf8;
            border-color: #38bdf8;
            color: #020617;
            transform: translateY(-1px);
            box-shadow: 0 14px 30px rgba(56, 189, 248, 0.35);
        }

    </style>
</head>
<body>

<div class="wrapper">
    <h2>Il tuo carrello</h2>

    <div class="back-link">
        <a href="<%= request.getContextPath() %>/menuOrdinaDaCasa">Continua a ordinare</a>
    </div>


    <table>
        <tr>
            <th>Immagine</th>
            <th>Nome</th>
            <th style="width: 180px;">Quantità</th>
            <th>Prezzo unitario</th>
            <th>Subtotale</th>
        </tr>

        <%
            BigDecimal totale = BigDecimal.ZERO;

            for (java.util.Map.Entry<Integer, RigaCarrello> entry : carrello.getRighe().entrySet()) {

                Integer idPiatto = entry.getKey();
                RigaCarrello r = entry.getValue();

                BigDecimal sub = r.getPrezzoUnitario().multiply(BigDecimal.valueOf(r.getQuantita()));
                totale = totale.add(sub);
        %>

        <tr>
            <td><img src="<%= r.getImmagineUrl() %>" class="img-piatto" alt="piatto"></td>
            <td><%= r.getNome() %></td>

            <td>
                <!-- bottone - -->
                <form action="<%= request.getContextPath() %>/aggiornaCarrello"
                      method="post" class="qty-form">
                    <input type="hidden" name="idPiatto" value="<%= idPiatto %>">
                    <input type="hidden" name="azione" value="minus">
                    <button type="submit" class="qty-btn">-</button>
                </form>

                <span class="qty-value"><%= r.getQuantita() %></span>

                <!-- bottone + -->
                <form action="<%= request.getContextPath() %>/aggiornaCarrello"
                      method="post" class="qty-form">
                    <input type="hidden" name="idPiatto" value="<%= idPiatto %>">
                    <input type="hidden" name="azione" value="plus">
                    <button type="submit" class="qty-btn">+</button>
                </form>
            </td>

            <td><%= r.getPrezzoUnitario() %> €</td>
            <td><%= sub %> €</td>
        </tr>

        <%
            }
        %>
    </table>

    <div class="totale-row">
        <div class="totale-label">Totale provvisorio:</div>
        <div class="totale-valore"><%= totale %> €</div>

        <!-- SVUOTA TUTTO -->
        <form action="<%= request.getContextPath() %>/svuotaCarrello" method="post">
            <button type="submit" class="svuota-btn">Svuota tutto</button>
        </form>

        <!-- CHECKOUT -->
        <form action="<%= request.getContextPath() %>/checkout" method="get">
            <button type="submit" class="checkout-btn">Procedi al checkout</button>
        </form>
    </div>

</div>

</body>
</html>
