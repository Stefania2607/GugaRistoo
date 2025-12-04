<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Piatto" %>

<%
    String sotto = (String) request.getAttribute("sotto");
    if (sotto == null) sotto = "tutti";
    List<Piatto> bevande = (List<Piatto>) request.getAttribute("bevande");
%>

<html>
<head>
    <title>Vini</title>
    <style>
        body { background:#000; color:#fff; font-family:sans-serif; }
        .header { text-align:center; margin-top:30px; }
        .header h2 { letter-spacing:4px; font-size:26px; }

        .filtro {
            margin: 20px auto;
            text-align: center;
        }

        .bevande-container {
            display:flex;
            flex-wrap:wrap;
            justify-content:center;
            gap:20px;
            margin-top:20px;
        }

        .bevanda-card {
            width:260px;
            padding:14px;
            border-radius:10px;
            border:1px solid #444;
            background:#111;
        }

        .bevanda-nome { font-weight:bold; margin-bottom:4px; }
        .bevanda-prezzo { font-weight:bold; margin-top:6px; }
        .back-link { color:#f2b66d; text-decoration:none; }
    </style>
</head>
<body>

<div class="header">
    <h2>VINI</h2>
    <a href="bevande" class="back-link">&larr; Torna alle categorie</a>
</div>

<div class="filtro">
    <form method="get" action="bevande">
        <input type="hidden" name="tipo" value="vini">
        <label for="sotto">Filtra per tipo:</label>
        <select id="sotto" name="sotto" onchange="this.form.submit()">
            <option value="tutti"     <%= "tutti".equals(sotto) ? "selected" : "" %>>Tutti</option>
            <option value="rossi"     <%= "rossi".equals(sotto) ? "selected" : "" %>>Rossi</option>
            <option value="bianchi"   <%= "bianchi".equals(sotto) ? "selected" : "" %>>Bianchi</option>
            <option value="rose"      <%= "rose".equals(sotto) ? "selected" : "" %>>Rosé</option>
            <option value="bollicine" <%= "bollicine".equals(sotto) ? "selected" : "" %>>Bollicine</option>
        </select>
    </form>
</div>

<div class="bevande-container">
    <%
        if (bevande != null) {
            for (Piatto b : bevande) {
    %>
    <div class="bevanda-card">
        <div class="bevanda-nome"><%= b.getNome() %></div>
        <div class="bevanda-desc"><%= b.getDescrizione() %></div>
        <div class="bevanda-prezzo"><%= b.getPrezzo() %> €</div>
    </div>
    <%
            }
        } else {
    %>
    <p>Nessun vino trovato.</p>
    <%
        }
    %>
</div>

</body>
</html>
