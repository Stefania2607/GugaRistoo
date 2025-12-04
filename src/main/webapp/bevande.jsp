<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Bean.Piatto" %>

<%
    List<Piatto> bevande = (List<Piatto>) request.getAttribute("bevande");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Bevande - GugaRistò</title>

    <!-- Font elegante come nel menu -->
    <link href="https://fonts.googleapis.com/css2?family=Cinzel:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
        }

        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
        }

        body {
            font-family: "Cinzel", serif;
            color: #f9fafb;
        }

        /* SFONDO BAR SFUOCATO */
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
            background-image: url('<%= contextPath %>/Images/bevande-bg.jpg');
            background-size: cover;
            background-position: center;
            filter: blur(4px);
            transform: scale(1.1);
        }

        .bg-wrap::after {
            content: "";
            position: absolute;
            inset: 0;
            background: radial-gradient(circle at top,
                            rgba(15, 23, 42, 0.25),
                            rgba(15, 23, 42, 0.88));
            mix-blend-mode: multiply;
        }

        .app-shell {
            position: relative;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        /* HEADER stile menu.jsp */
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

        .logo-title {
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
            padding: 110px 22px 30px; /* 80 header + 30 margine */
        }

        /* TITOLO SEZIONE */
        .category-header {
            text-align: center;
            margin-bottom: 18px;
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
            width: 140px;
            border-radius: 999px;
        }

        .category-subtitle {
            margin-top: 6px;
            font-size: 12px;
            color: #e5e7eb;
        }

        /* MENU CATEGORIE + SOTTOMENU */
        .bevande-menu {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 18px;
            margin-bottom: 22px;
            font-size: 13px;
            position: relative;
            z-index: 10;
        }

        .cat-item {
            position: relative;
            padding: 6px 12px;
            cursor: pointer;
            color: #e5e7eb;
            text-transform: uppercase;
            letter-spacing: 0.12em;
            border-radius: 999px;
            border: 1px solid transparent;
            background: rgba(15, 23, 42, 0.75);
            backdrop-filter: blur(6px);
        }

        .cat-item.simple a {
            color: inherit;
            text-decoration: none;
        }

        .cat-item:hover {
            border-color: rgba(250, 204, 21, 0.8);
        }

        .cat-item.active {
            background: linear-gradient(135deg, #f97316, #facc15);
            color: #0f172a;
            border-color: transparent;
        }

        .cat-item.active a {
            color: #0f172a;
        }

        /* TENDINE (VINI / BIRRE / COCKTAIL) */
        .submenu {
            display: none;
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translateX(-50%);

            min-width: 160px;
            background: rgba(15, 23, 42, 0.96);
            border-radius: 12px;
            border: 1px solid rgba(148,163,184,0.7);
            padding: 6px 0;
            z-index: 20;
            backdrop-filter: blur(10px);
            white-space: nowrap;
        }

        .cat-item:hover .submenu {
            display: block;
        }

        /* Forza visibilità delle voci nella tendina */
        .submenu a,
        .submenu a:link,
        .submenu a:visited,
        .submenu a:active {
            display: block;
            padding: 6px 12px;
            color: #f9fafb !important;
            opacity: 1 !important;
            text-shadow: none !important;
            text-decoration: none;
            font-size: 12px;
        }

        .submenu a:hover {
            background: rgba(250, 204, 21, 0.18) !important;
            color: #facc15 !important;
        }

        .submenu a.active {
            background: rgba(250, 204, 21, 0.25) !important;
            color: #facc15 !important;
        }

        /* GRIGLIA CARTE (BEVANDE) */
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

        @media (max-width: 640px) {
            .grid {
                grid-template-columns: 1fr;
            }
        }

        .card {
            position: relative;
            background: rgba(15, 23, 42, 0.88);
            border-radius: 18px;
            overflow: hidden;
            box-shadow:
                    0 10px 26px rgba(15, 23, 42, 0.9),
                    0 0 0 1px rgba(148, 163, 184, 0.35);
            display: flex;
            flex-direction: column;
            min-height: 240px;
            z-index: 1;
        }

        .card-badge {
            position: absolute;
            top: 8px;
            left: 8px;
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
        }

        .card-title-row {
            display: flex;
            align-items: baseline;
            justify-content: space-between;
            gap: 8px;
        }

        .card-title {
            font-size: 15px;
            font-weight: 600;
        }

        .card-price {
            font-weight: 600;
            font-size: 15px;
            color: #facc15;
            white-space: nowrap;
        }

        .card-desc {
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
                <span class="subtitle">Signature Drinks & Spirits</span>
            </div>
        </div>

        <div class="header-right">
            <a href="<%= contextPath %>/menu" class="btn">Torna al menu</a>
            <a href="<%= contextPath %>/home" class="btn">Home</a>
        </div>
    </header>

    <div class="page">

        <div class="category-header">
            <div class="category-title">BEVANDE</div>
            <div class="category-divider"></div>
            <div class="category-subtitle">
                Vini, birre, cocktail e distillati selezionati.
            </div>
        </div>

        <!-- MENU CATEGORIE -->
        <div class="bevande-menu" id="bevande-menu">
            <!-- TUTTE -->
            <div class="cat-item simple" data-macro="tutte">
                <a href="#" data-macro="tutte">Tutte</a>
            </div>

            <!-- VINI con sottovoci -->
            <div class="cat-item" data-macro="vini">
                <span>Vini</span>
                <div class="submenu">
                    <a href="#" data-macro="vini" data-sotto="rossi">Rossi</a>
                    <a href="#" data-macro="vini" data-sotto="bianchi">Bianchi</a>
                    <a href="#" data-macro="vini" data-sotto="rose">Rosé</a>
                    <a href="#" data-macro="vini" data-sotto="bollicine">Bollicine</a>
                </div>
            </div>

            <!-- BIRRE con sottovoci -->
            <div class="cat-item" data-macro="birre">
                <span>Birre</span>
                <div class="submenu">
                    <a href="#" data-macro="birre" data-sotto="bionde">Bionde</a>
                    <a href="#" data-macro="birre" data-sotto="rosse">Rosse</a>
                </div>
            </div>

            <!-- COCKTAIL con sottovoci -->
            <div class="cat-item" data-macro="cocktail">
                <span>Cocktail</span>
                <div class="submenu">
                    <a href="#" data-macro="cocktail" data-sotto="alcolici">Alcolici</a>
                    <a href="#" data-macro="cocktail" data-sotto="analcolici">Analcolici</a>
                </div>
            </div>

            <!-- ACQUA & SOFT DRINK -->
            <div class="cat-item simple" data-macro="acqua_soft">
                <a href="#" data-macro="acqua_soft">Acqua & Soft drink</a>
            </div>

            <!-- AMARI -->
            <div class="cat-item simple" data-macro="amari">
                <a href="#" data-macro="amari">Amari</a>
            </div>
        </div>

        <!-- GRIGLIA DELLE BEVANDE -->
        <div class="grid" id="grid-bevande">
            <%
                if (bevande != null) {
                    for (Piatto p : bevande) {

                        // Gestione immagine
                        String img = p.getImmagineUrl();
                        if (img == null || img.trim().isEmpty()) {
                            img = "https://via.placeholder.com/600x400?text=Bevanda";
                        } else if (!img.startsWith("http")) {
                            img = contextPath + "/" + img;
                        }

                        // Mapping tipo → macro/sotto
                        // ATTENZIONE: qui usa getTipoBevanda(), se tu stai usando "categoria" cambia in getCategoria()
                        String tipo = p.getTipoBevanda();
                        String macro = "";
                        String sotto = "";

                        if (tipo != null) {

                            // VINI
                            if (tipo.startsWith("vini_")) {
                                macro = "vini";
                                if ("vini_rossi".equals(tipo))           sotto = "rossi";
                                else if ("vini_bianchi".equals(tipo))    sotto = "bianchi";
                                else if ("vini_rose".equals(tipo))       sotto = "rose";
                                else if ("vini_bollicine".equals(tipo))  sotto = "bollicine";

                            // BIRRE (accetta sia birre_xxx che birra_xxx)
                            } else if (tipo.startsWith("birre_") || tipo.startsWith("birra_")) {
                                macro = "birre";

                                if ("birre_bionde".equals(tipo) || "birra_bionda".equals(tipo)) {
                                    sotto = "bionde";
                                } else if ("birre_rosse".equals(tipo) || "birra_rossa".equals(tipo)) {
                                    sotto = "rosse";
                                }

                            // COCKTAIL
                            } else if ("cocktail_alcolici".equals(tipo)) {
                                macro = "cocktail";
                                sotto = "alcolici";

                            } else if ("cocktail_analcolici".equals(tipo)) {
                                macro = "cocktail";
                                sotto = "analcolici";

                            // ACQUA & SOFT
                            } else if ("acqua_soft".equals(tipo)) {
                                macro = "acqua_soft";

                            // AMARI
                            } else if ("amari".equals(tipo)) {
                                macro = "amari";
                            }
                        }
            %>
            <div class="card"
                 data-macro="<%= macro %>"
                 data-sotto="<%= sotto %>">
                <div class="card-badge">Bevande</div>
                <img src="<%= img %>" alt="<%= p.getNome() %>">
                <div class="card-content">
                    <div class="card-title-row">
                        <div class="card-title"><%= p.getNome() %></div>
                        <div class="card-price"><%= String.format("%.2f €", p.getPrezzo()) %></div>
                    </div>
                    <div class="card-desc">
                        <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                    </div>
                    <div class="card-footer">
                        <span class="card-tag">GugaRistò · Signature drink</span>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>

    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const cards = document.querySelectorAll("#grid-bevande .card");
    const catItems = document.querySelectorAll(".bevande-menu .cat-item");
    const submenuLinks = document.querySelectorAll(".submenu a");
    const simpleLinks = document.querySelectorAll(".cat-item.simple a");
    const menu = document.getElementById("bevande-menu");

    let currentMacro = "tutte";
    let currentSotto = "tutti";

    function applyFilter() {
        cards.forEach(card => {
            const cm = card.getAttribute("data-macro");   // macro: vini, birre, cocktail...
            const cs = card.getAttribute("data-sotto");   // sotto: rossi, bionde, rosse, ...

            let visible = true;

            // 1) filtro per macro-categoria
            if (currentMacro !== "tutte" && cm !== currentMacro) {
                visible = false;
            }

            // 2) filtro per sottocategoria (vini / birre / cocktail)
            if (
                visible &&
                ["vini", "birre", "cocktail"].includes(currentMacro) &&
                currentSotto !== "tutti"
            ) {
                if (!cs || cs !== currentSotto) {
                    visible = false;
                }
            }

            card.style.display = visible ? "" : "none";
        });

        // evidenzia macro attiva
        catItems.forEach(ci => {
            const macro = ci.getAttribute("data-macro");
            ci.classList.toggle(
                "active",
                macro === currentMacro || (currentMacro === "tutte" && macro === "tutte")
            );
        });

        // evidenzia sottocategoria attiva
        submenuLinks.forEach(a => {
            const m = a.getAttribute("data-macro");
            const s = a.getAttribute("data-sotto");
            a.classList.toggle("active", m === currentMacro && s === currentSotto);
        });
    }

    // 🔥 EVENT DELEGATION: gestiamo tutti i click dal contenitore unico
    menu.addEventListener("click", function (e) {
        const link = e.target.closest("a");
        if (!link) return;          // non hai cliccato su un link, ignora

        e.preventDefault();

        const macro = link.getAttribute("data-macro");
        const sotto = link.getAttribute("data-sotto");

        // Se è una categoria semplice (Tutte, Acqua, Amari...)
        if (link.closest(".cat-item.simple")) {
            currentMacro = macro || "tutte";
            currentSotto = "tutti";
        } else {
            // È una sottocategoria di Vini / Birre / Cocktail
            currentMacro = macro;
            currentSotto = sotto || "tutti";
        }

        applyFilter();
    });

    // primo render
    applyFilter();
});
</script>


</body>
</html>
