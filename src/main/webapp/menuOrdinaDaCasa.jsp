<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="controller.bean.Piatto" %>
<%@ page import="controller.bean.Carrello" %>

<%
    List<Piatto> listaPiatti = (List<Piatto>) request.getAttribute("listaPiatti");
    Carrello carrello = (Carrello) request.getAttribute("carrello");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Menù - Ordina da casa</title>
    <style>
        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background: #020617;
            color: #e5e7eb;
            margin: 0;
            padding: 24px;
        }

        .page {
            max-width: 1200px;
            margin: 0 auto 40px auto;
        }

        /* TOP BAR */

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }

        .top-title h1 {
            margin: 0 0 4px 0;
            font-size: 32px;
        }

        .top-title p {
            margin: 0;
            font-size: 14px;
            color: #9ca3af;
        }

        /* CARRELLO */

        .carrello-link {
            text-decoration: none;
            color: inherit;
            display: inline-block;
            cursor: pointer;
        }

        .carrello-pill {
            background: rgba(15,23,42,0.95);
            border-radius: 999px;
            padding: 8px 18px;
            border: 1px solid rgba(148,163,184,0.7);
            display: inline-flex;
            align-items: center;
            gap: 10px;
            font-size: 14px;
            box-shadow: 0 18px 40px rgba(15,23,42,0.7);
            transition: transform 0.12s ease;
        }

        .carrello-pill:hover {
            transform: scale(1.04);
        }

        .carrello-pill .empty {
            color: #9ca3af;
        }

        .carrello-pill .totale {
            font-weight: 600;
            color: #bbf7d0;
        }

        /* SEZIONI MENU */

        .sezione {
            margin-top: 32px;
        }

        .sezione-titolo {
            font-size: 20px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .sezione-sottotitolo {
            font-size: 13px;
            color: #9ca3af;
            margin-bottom: 16px;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 16px;
        }

        /* CARD PIATTO */

        .piatto-card {
            background: rgba(15,23,42,0.96);
            border-radius: 20px;
            padding: 16px;
            border: 1px solid rgba(31,41,55,0.9);
            box-shadow: 0 18px 40px rgba(15,23,42,0.9);
        }

        .piatto-img {
            width: 100%;
            height: 170px;
            object-fit: cover;
            border-radius: 14px;
            display: block;
            margin-bottom: 12px;
        }

        .piatto-nome {
            font-weight: 600;
            margin-bottom: 4px;
        }

        .piatto-prezzo {
            color: #a5b4fc;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .piatto-desc {
            font-size: 13px;
            color: #9ca3af;
            min-height: 48px;
            margin-bottom: 10px;
        }

        .btn-add {
            margin-top: 4px;
            padding: 7px 14px;
            border-radius: 999px;
            border: none;
            background: #22c55e;
            color: #022c22;
            font-weight: 600;
            cursor: pointer;
            font-size: 13px;
        }

        .btn-add:hover {
            filter: brightness(1.05);
        }

        .divider {
            margin: 32px 0 0 0;
            border: none;
            border-top: 1px solid rgba(30,64,175,0.5);
        }
    </style>
</head>
<body>
<div class="page">

    <!-- TOP BAR: titolo + carrello cliccabile -->
    <div class="top-bar">
        <div class="top-title">
            <h1>Ordina dal menù</h1>
            <p>Seleziona i piatti che vuoi aggiungere al tuo ordine.</p>
        </div>

        <a href="<%= request.getContextPath() %>/carrello" class="carrello-link">
            <div class="carrello-pill">
                <%
                    if (carrello == null || carrello.isVuoto()) {
                %>
                    <span class="empty">Carrello vuoto</span>
                <%
                    } else {
                %>
                    <span><%= carrello.getNumeroArticoli() %> articoli</span>
                    <span class="totale">Totale: € <%= carrello.getTotale() %></span>
                <%
                    }
                %>
            </div>
        </a>
    </div>

    <!-- ================== SEZIONE ANTIPASTI ================== -->
    <div class="sezione">
        <div class="sezione-titolo">Antipasti</div>
        <div class="sezione-sottotitolo">
            Piccoli piatti per iniziare nel modo giusto.
        </div>

        <div class="menu-grid">
            <%
                if (listaPiatti != null) {
                    for (Piatto p : listaPiatti) {
                        if (!"antipasto".equalsIgnoreCase(p.getCategoria())) continue;
            %>
            <div class="piatto-card">
                <img class="piatto-img"
                     src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>"
                     alt="<%= p.getNome() %>">

                <div class="piatto-nome"><%= p.getNome() %></div>
                <div class="piatto-prezzo">€ <%= p.getPrezzo() %></div>
                <div class="piatto-desc">
                    <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                </div>

                <form method="post"
                      action="<%= request.getContextPath() %>/aggiungiAlCarrello">
                    <input type="hidden" name="idPiatto" value="<%= p.getId() %>">
                    <button type="submit" class="btn-add">Aggiungi al carrello</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <hr class="divider"/>

    <!-- ================== SEZIONE PRIMI ================== -->
    <div class="sezione">
        <div class="sezione-titolo">Primi piatti</div>
        <div class="sezione-sottotitolo">
            Paste e risotti preparati al momento.
        </div>

        <div class="menu-grid">
            <%
                if (listaPiatti != null) {
                    for (Piatto p : listaPiatti) {
                        if (!"primo".equalsIgnoreCase(p.getCategoria())) continue;
            %>
            <div class="piatto-card">
                <img class="piatto-img"
                     src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>"
                     alt="<%= p.getNome() %>">

                <div class="piatto-nome"><%= p.getNome() %></div>
                <div class="piatto-prezzo">€ <%= p.getPrezzo() %></div>
                <div class="piatto-desc">
                    <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                </div>

                <form method="post"
                      action="<%= request.getContextPath() %>/aggiungiAlCarrello">
                    <input type="hidden" name="idPiatto" value="<%= p.getId() %>">
                    <button type="submit" class="btn-add">Aggiungi al carrello</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <hr class="divider"/>

    <!-- ================== SEZIONE SECONDI ================== -->
    <div class="sezione">
        <div class="sezione-titolo">Secondi piatti</div>
        <div class="sezione-sottotitolo">
            Carni, pesci e specialità dalla cucina.
        </div>

        <div class="menu-grid">
            <%
                if (listaPiatti != null) {
                    for (Piatto p : listaPiatti) {
                        if (!"secondo".equalsIgnoreCase(p.getCategoria())) continue;
            %>
            <div class="piatto-card">
                <img class="piatto-img"
                     src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>"
                     alt="<%= p.getNome() %>">

                <div class="piatto-nome"><%= p.getNome() %></div>
                <div class="piatto-prezzo">€ <%= p.getPrezzo() %></div>
                <div class="piatto-desc">
                    <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                </div>

                <form method="post"
                      action="<%= request.getContextPath() %>/aggiungiAlCarrello">
                    <input type="hidden" name="idPiatto" value="<%= p.getId() %>">
                    <button type="submit" class="btn-add">Aggiungi al carrello</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <hr class="divider"/>

    <!-- ================== SEZIONE DOLCI ================== -->
    <div class="sezione">
        <div class="sezione-titolo">Dolci</div>
        <div class="sezione-sottotitolo">
            Per chiudere il pasto con qualcosa di speciale.
        </div>

        <div class="menu-grid">
            <%
                if (listaPiatti != null) {
                    for (Piatto p : listaPiatti) {
                        if (!"dolce".equalsIgnoreCase(p.getCategoria())) continue;
            %>
            <div class="piatto-card">
                <img class="piatto-img"
                     src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>"
                     alt="<%= p.getNome() %>">

                <div class="piatto-nome"><%= p.getNome() %></div>
                <div class="piatto-prezzo">€ <%= p.getPrezzo() %></div>
                <div class="piatto-desc">
                    <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                </div>

                <form method="post"
                      action="<%= request.getContextPath() %>/aggiungiAlCarrello">
                    <input type="hidden" name="idPiatto" value="<%= p.getId() %>">
                    <button type="submit" class="btn-add">Aggiungi al carrello</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <hr class="divider"/>

    <!-- ================== SEZIONE BEVANDE ================== -->
    <div class="sezione">
        <div class="sezione-titolo">Bevande</div>
        <div class="sezione-sottotitolo">
            Acque, soft drink, vini e altre bevande.
        </div>

        <div class="menu-grid">
            <%
                if (listaPiatti != null) {
                    for (Piatto p : listaPiatti) {
                        if (!"bevanda".equalsIgnoreCase(p.getCategoria())) continue;
            %>
            <div class="piatto-card">
                <img class="piatto-img"
                     src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>"
                     alt="<%= p.getNome() %>">

                <div class="piatto-nome"><%= p.getNome() %></div>
                <div class="piatto-prezzo">€ <%= p.getPrezzo() %></div>
                <div class="piatto-desc">
                    <%= p.getDescrizione() != null ? p.getDescrizione() : "" %>
                </div>

                <form method="post"
                      action="<%= request.getContextPath() %>/aggiungiAlCarrello">
                    <input type="hidden" name="idPiatto" value="<%= p.getId() %>">
                    <button type="submit" class="btn-add">Aggiungi al carrello</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

</div>
</body>
</html>
