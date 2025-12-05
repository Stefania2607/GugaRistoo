<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Piatto" %>
<%@ page import="Controller.Bean.Utente" %>

<%
    List<Piatto> lista = (List<Piatto>) request.getAttribute("listaPiatti");
    String contextPath = request.getContextPath();

    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    String ruolo = (String) session.getAttribute("ruolo");
    boolean isCliente = (utenteLoggato != null && "CLIENTE".equalsIgnoreCase(ruolo));
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Menu - GugaRistò</title>

    <!-- Font elegante -->
    <link href="https://fonts.googleapis.com/css2?family=Cinzel:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
        }
        .dolci-link {
            color: #f28bb3;    /* esempio: rosa per dolci */
        }

        .bevande-link {
            color: #f2b66d;    /* arancio-dorato che sta bene vicino al rosa */
        }

        .bevande-link:hover {
            text-shadow: 0 0 6px #f2b66d;
        }

        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
        }

        body {
            font-family: "Cinzel", serif;
            color: #0f172a;
        }

        /* SFONDO FOTO SFUOCATA + VELINO */
        .bg-wrap {
            position: fixed;
            inset: 0;
            overflow: hidden;
            z-index: -2;
        }

        .bg-wrap::before {
            content: "";
            position: absolute;
            inset: 0;
            background-image: url('<%= contextPath %>/Images/menu-bg.jpg');
            background-size: cover;
            background-position: center;
        }

        .bg-wrap::after {
            content: "";
            position: absolute;
            inset: 0;
            background: radial-gradient(circle at top,
                        rgba(15, 23, 42, 0.25),
                        rgba(15, 23, 42, 0.75));
            mix-blend-mode: multiply;
        }

        /* LAYER CONTENUTO */
        .app-shell {
            position: relative;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        header {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 50;

            height: 80px;
            padding: 0 36px;

            background: rgba(15, 23, 42, 0.92);
            backdrop-filter: blur(14px);
            border-bottom: 1px solid rgba(148, 163, 184, 0.25);
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        header .logo-title {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .logo-circle {
            width: 34px;
            height: 34px;
            border-radius: 50%;
            background: linear-gradient(135deg, #f59e0b, #fbbf24);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 18px;
            color: #111827;
            box-shadow: 0 0 14px rgba(249, 115, 22, 0.7);
        }

        header h1 {
            margin: 0;
            font-size: 22px;
            letter-spacing: 0.06em;
            text-transform: uppercase;
            color: #e5e7eb;
        }

        header span.subtitle {
            display: block;
            font-size: 11px;
            font-weight: 300;
            color: #9ca3af;
            letter-spacing: 0.18em;
            text-transform: uppercase;
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 8px 16px;
            border-radius: 999px;
            border: 1px solid rgba(148, 163, 184, 0.6);
            background: radial-gradient(circle at top left,
                    rgba(249, 115, 22, 0.15),
                    rgba(15, 23, 42, 0.95));
            color: #e5e7eb;
            font-size: 13px;
            font-weight: 500;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.18s ease-out;
        }

        .btn:hover {
            border-color: rgba(251, 191, 36, 0.9);
            box-shadow: 0 0 14px rgba(251, 191, 36, 0.4);
            transform: translateY(-1px);
        }

        .page {
            max-width: 1100px;
            margin: 0 auto 40px;
            padding: 110px 22px 30px;   /* 80px header + 30px margine */
            color: #f9fafb;
        }

        .empty-message {
            margin-top: 40px;
            font-size: 15px;
            text-align: center;
        }

        .empty-message span {
            display: block;
            font-size: 12px;
            color: #cbd5f5;
            margin-top: 6px;
        }

        /* TABS CATEGORIE */
        .tabs-bar {
            margin-bottom: 24px;
            display: flex;
            justify-content: center;
            gap: 10px;
            flex-wrap: wrap;
        }

        .tab-btn {
            padding: 8px 18px;
            border-radius: 999px;
            border: 1px solid rgba(248, 250, 252, 0.35);
            background: rgba(15, 23, 42, 0.68);
            color: #f9fafb;
            font-size: 13px;
            letter-spacing: 0.10em;
            text-transform: uppercase;
            cursor: pointer;
            font-weight: 500;
            backdrop-filter: blur(8px);
            transition: all 0.18s ease-out;
        }

        .tab-btn:hover {
            border-color: rgba(249, 115, 22, 0.9);
            box-shadow: 0 0 14px rgba(249, 115, 22, 0.4);
            transform: translateY(-1px);
        }

        .tab-btn.active {
            background: linear-gradient(135deg, #f97316, #facc15);
            color: #111827;
            border-color: transparent;
            box-shadow: 0 0 18px rgba(250, 204, 21, 0.6);
        }

        /* SEZIONI CATEGORIA */
        .category-section {
            display: none;
        }

        .category-section.active {
            display: block;
        }

        .category-header {
            text-align: center;
            margin-bottom: 14px;
        }

        .category-title {
            font-size: 22px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.18em;
        }

        .category-divider {
            height: 2px;
            background: linear-gradient(90deg, rgba(249,115,22,0.9), transparent);
            margin: 8px auto 0;
            width: 120px;
            border-radius: 999px;
        }

        .category-subtitle {
            margin-top: 6px;
            font-size: 12px;
            color: #e5e7eb;
        }

        /* GRIGLIA 3xN */
        .grid {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 18px;
        }

        @media (max-width: 900px) {
            .grid {
                grid-template-columns: repeat(2, minmax(0, 1fr));
            }
        }

        @media (max-width: 600px) {
            .grid {
                grid-template-columns: 1fr;
            }
        }

        .card {
            position: relative;
            background: rgba(15, 23, 42, 0.85);
            border-radius: 18px;
            overflow: hidden;
            box-shadow:
                    0 10px 26px rgba(15, 23, 42, 0.85),
                    0 0 0 1px rgba(148, 163, 184, 0.35);
            display: flex;
            flex-direction: column;
            min-height: 250px;
        }

        .card-badge {
            position: absolute;
            top: 10px;
            left: 10px;
            padding: 3px 9px;
            border-radius: 999px;
            font-size: 9px;
            text-transform: uppercase;
            letter-spacing: 0.14em;
            background: rgba(15, 23, 42, 0.9);
            border: 1px solid rgba(248, 250, 252, 0.7);
            color: #e5e7eb;
        }

        .card img {
            width: 100%;
            height: 130px;
            object-fit: cover;
            border-bottom: 1px solid rgba(148, 163, 184, 0.5);
        }

        .card-content {
            padding: 12px 14px 14px;
            display: flex;
            flex-direction: column;
            gap: 6px;
            flex: 1;
            color: #f9fafb;
        }

        .card-title-row {
            display: flex;
            align-items: baseline;
            justify-content: space-between;
            gap: 8px;
        }

        .card-title {
            font-size: 16px;
            font-weight: 600;
            margin: 0;
        }

        .card-price {
            font-weight: 600;
            font-size: 15px;
            color: #facc15;
            white-space: nowrap;
        }

        .card-ingredients {
            font-size: 12px;
            color: #e5e7eb;
            line-height: 1.45;
        }

        .card-footer {
            display: flex;
            justify-content: flex-end;
            margin-top: auto;
        }

        .card-tag {
            font-size: 10px;
            padding: 3px 8px;
            border-radius: 999px;
            background: #111827;
            border: 1px solid rgba(148, 163, 184, 0.6);
            color: #e5e7eb;
        }

        @media (max-width: 640px) {
            header {
                padding: 12px 18px;
            }

            .page {
                padding: 110px 14px 24px;
            }
        }
    </style>
</head>
<body>

<div class="bg-wrap"></div>

<div class="app-shell">

    <header>
        <div class="logo-title">
            <div class="logo-circle">G</div>
            <div>
                <h1>GugaRistò</h1>
                <span class="subtitle">Contemporary Dining Experience</span>
            </div>
        </div>

        <div class="header-right">
            <% if (isCliente) { %>
                <a href="clienteHome.jsp" class="btn">Torna all'area riservata</a>
            <% } else { %>
                <a href="<%= contextPath %>/home" class="btn">Torna alla home</a>
            <% } %>
        </div>
    </header>

    <div class="page">

        <%
            if (lista == null || lista.isEmpty()) {
        %>
        <div class="empty-message">
            Nessun piatto disponibile al momento.
            <span>Aggiungi qualche piatto nel database per vedere il menu prendere vita.</span>
        </div>
        <%
            } else {
        %>

        <!-- TABS CLICCABILI -->
        <div class="tabs-bar">
            <button class="tab-btn active" data-target="antipasto">Antipasti</button>
            <button class="tab-btn" data-target="primo">Primi</button>
            <button class="tab-btn" data-target="secondo">Secondi</button>
            <button class="tab-btn" data-target="contorno">Contorni</button>
            <button class="tab-btn" data-target="dolce">Dolci</button>
            <a href="<%= contextPath %>/bevande" class="tab-btn bevande-link">Bevande</a>
        </div>

        <%
            String[][] sezioni = {
                    {"antipasto", "Antipasti"},
                    {"primo", "Primi"},
                    {"secondo", "Secondi"},
                    {"contorno", "Contorni"},
                    {"dolce", "Dolci"},
            };

            for (String[] sec : sezioni) {
                String catVal = sec[0];
                String catLabel = sec[1];

                boolean hasCategory = false;
                for (Piatto p : lista) {
                    if (p.getCategoria() != null &&
                            p.getCategoria().equalsIgnoreCase(catVal)) {
                        hasCategory = true;
                        break;
                    }
                }
                if (!hasCategory) continue;
        %>

        <section class="category-section" id="section-<%= catVal %>">
            <div class="category-header">
                <div class="category-title"><%= catLabel %></div>
                <div class="category-divider"></div>
                <div class="category-subtitle">
                    Selezione <%= catLabel.toLowerCase() %> curata dallo chef.
                </div>
            </div>

            <div class="grid">
                <%
                    for (Piatto p : lista) {
                        if (p.getCategoria() == null ||
                                !p.getCategoria().equalsIgnoreCase(catVal)) {
                            continue;
                        }

                        String img = p.getImmagineUrl();
                        if (img == null || img.trim().isEmpty()) {
                            img = "https://via.placeholder.com/600x400?text=Piatto";
                        } else if (!img.startsWith("http")) {
                            img = contextPath + "/" + img;
                        }
                %>
                <div class="card">
                    <div class="card-badge"><%= catLabel %></div>
                    <img src="<%= img %>" alt="<%= p.getNome() %>">
                    <div class="card-content">
                        <div class="card-title-row">
                            <div class="card-title"><%= p.getNome() %></div>
                            <div class="card-price">
                                <%= String.format("%.2f €", p.getPrezzo()) %>
                            </div>
                        </div>
                        <div class="card-ingredients">
                            <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                        </div>
                        <div class="card-footer">
                            <span class="card-tag">GugaRistò · Chef’s choice</span>
                        </div>
                    </div>
                </div>
                <%
                    } // fine for piatti
                %>
            </div>
        </section>

        <%
            } // fine for sezioni
          } // fine else lista non vuota
        %>

    </div>
</div>

<!-- JS per i tab -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const tabs = document.querySelectorAll(".tab-btn[data-target]");
        const sections = document.querySelectorAll(".category-section");

        function showCategory(cat) {
            sections.forEach(sec => {
                if (sec.id === "section-" + cat) {
                    sec.classList.add("active");
                } else {
                    sec.classList.remove("active");
                }
            });

            tabs.forEach(btn => {
                if (btn.getAttribute("data-target") === cat) {
                    btn.classList.add("active");
                } else {
                    btn.classList.remove("active");
                }
            });
        }

        // sezione iniziale
        showCategory("antipasto");

        tabs.forEach(btn => {
            btn.addEventListener("click", function () {
                const target = this.getAttribute("data-target");
                if (target) {
                    showCategory(target);
                }
            });
        });
    });
</script>

</body>
</html>
