<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="controller.bean.Carrello" %>
<%@ page import="controller.bean.RigaCarrello" %>
<%@ page import="java.math.BigDecimal" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    if (carrello == null || carrello.isVuoto()) {
        response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
        return;
    }

    BigDecimal totale = BigDecimal.ZERO;
    for (java.util.Map.Entry<Integer, RigaCarrello> entry : carrello.getRighe().entrySet()) {
        RigaCarrello r = entry.getValue();
        totale = totale.add(r.getTotaleRiga());
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Checkout - GugaRistò</title>
    <style>
        body {
            background: #020617;
            color: #e5e7eb;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .checkout-card {
            background: radial-gradient(circle at top, #0f172a, #020617);
            border-radius: 24px;
            padding: 28px 32px;
            width: 420px;
            box-shadow: 0 30px 80px rgba(15, 23, 42, 0.9);
        }

        .title {
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 4px;
        }

        .subtitle {
            font-size: 14px;
            color: #9ca3af;
            margin-bottom: 20px;
        }

        .totale-box {
            background: #0b1120;
            border-radius: 16px;
            padding: 12px 14px;
            margin-bottom: 18px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .totale-label {
            font-size: 14px;
            color: #9ca3af;
        }

        .totale-val {
            font-size: 20px;
            font-weight: 700;
            color: #facc15;
        }

        .form-group {
            margin-bottom: 14px;
        }

        label {
            display: block;
            font-size: 13px;
            margin-bottom: 4px;
        }

        input, select {
            width: 100%;
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #1f2937;
            background: #020617;
            color: #e5e7eb;
            font-size: 14px;
            box-sizing: border-box;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #38bdf8;
            box-shadow: 0 0 0 1px #38bdf8;
        }

        .row {
            display: flex;
            gap: 10px;
        }

        .row .form-group {
            flex: 1;
        }

        .pay-button {
            margin-top: 16px;
            width: 100%;
            padding: 10px 0;
            border: none;
            border-radius: 999px;
            background: #22c55e;
            color: #022c22;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.18s ease-in-out;
        }

        .pay-button:hover {
            background: #16a34a;
            box-shadow: 0 16px 32px rgba(34, 197, 94, 0.35);
            transform: translateY(-1px);
        }

        .back-link {
            margin-top: 12px;
            text-align: center;
        }

        .back-link a {
            color: #38bdf8;
            text-decoration: none;
            font-size: 13px;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="checkout-card">
    <div class="title">Pagamento ordine</div>
    <div class="subtitle">Inserisci i dati di pagamento per completare l'ordine.</div>

    <div class="totale-box">
        <div>
            <div class="totale-label">Totale da pagare</div>
            <div style="font-size: 12px; color: #9ca3af;">(tasse e servizio inclusi)</div>
        </div>
        <div class="totale-val"><%= totale %> €</div>
    </div>

    <form action="<%= request.getContextPath() %>/checkout" method="post">
        <div class="form-group">
            <label for="nomeIntestatario">Nome intestatario</label>
            <input type="text" id="nomeIntestatario" name="nomeIntestatario" required>
        </div>

        <div class="form-group">
            <label for="email">Email di conferma</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="numeroCarta">Numero carta</label>
            <input type="text" id="numeroCarta" name="numeroCarta" placeholder="**** **** **** ****" required>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="scadenza">Scadenza</label>
                <input type="text" id="scadenza" name="scadenza" placeholder="MM/AA" required>
            </div>
            <div class="form-group">
                <label for="cvv">CVV</label>
                <input type="password" id="cvv" name="cvv" maxlength="4" required>
            </div>
        </div>

        <div class="form-group">
            <label for="metodo">Metodo di pagamento</label>
            <select id="metodo" name="metodo">
                <option value="carta">Carta di credito / debito</option>
                <option value="paypal" disabled>PayPal (demo)</option>
                <option value="satispay" disabled>Satispay (demo)</option>
            </select>
        </div>

        <button type="submit" class="pay-button">Paga ora</button>
    </form>

    <div class="back-link">
        <a href="<%= request.getContextPath() %>/carrello">Torna al carrello</a>
    </div>
</div>

</body>
</html>
