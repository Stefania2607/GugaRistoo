<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Piatto" %>

<%
    List<Piatto> lista = (List<Piatto>) request.getAttribute("listaPiatti");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Menu - GugaRistoo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
        }
        header {
            background: #222;
            color: #fff;
            padding: 15px 30px;
        }
        header h1 {
            margin: 0;
        }
        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px 30px;
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 20px;
        }
        .card {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
            overflow: hidden;
            display: flex;
            flex-direction: column;
        }
        .card img {
            width: 100%;
            height: 160px;
            object-fit: cover;
        }
        .card-content {
            padding: 12px 16px 16px;
            flex: 1;
        }
        .card-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0 0 6px;
        }
        .card-ingredients {
            font-size: 13px;
            color: #555;
            margin-bottom: 10px;
        }
        .card-price {
            font-weight: bold;
            font-size: 16px;
            text-align: right;
        }
        .top-bar {
            margin: 10px 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .btn {
            display: inline-block;
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-size: 14px;
            background: #007bff;
            color: #fff;
        }
        .btn-secondary {
            background: #6c757d;
        }
    </style>
</head>
<body>

<header>
    <h1>GugaRistoo - Menu</h1>
</header>

<div class="container">

    <div class="top-bar">
        <h2>I nostri piatti</h2>
        <a href="home" class="btn btn-secondary">Torna alla home</a>
    </div>

    <%
        if (lista == null || lista.isEmpty()) {
    %>
        <p>Nessun piatto disponibile al momento.</p>
    <%
        } else {
    %>
    <div class="grid">
        <%
            for (Piatto p : lista) {
                String img = p.getImmagineUrl();
                if (img == null || img.trim().isEmpty()) {
                    img = "https://via.placeholder.com/400x200?text=Piatto"; // segnaposto
                }
        %>
        <div class="card">
            <img src="<%= img %>" alt="<%= p.getNome() %>">
            <div class="card-content">
                <div class="card-title"><%= p.getNome() %></div>
                <div class="card-ingredients">
                    <%= p.getIngredienti() != null ? p.getIngredienti() : "" %>
                </div>
                <div class="card-price">
                    <%= String.format("%.2f €", p.getPrezzo()) %>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
        }
    %>

</div>

</body>
</html>
