<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Controller.Bean.Utente" %>
<%
    Utente u = (Utente) session.getAttribute("utenteLoggato");
    String ruolo = (String) session.getAttribute("ruolo");

    if (u == null || ruolo == null || !"CLIENTE".equalsIgnoreCase(ruolo)) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Area Cliente - GugaRistò</title>

    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            color: #f5f5f5;
            overflow: hidden;
            position: relative;
        }

        body::before {
            content: "";
            position: fixed;
            inset: 0;
            background-image:
                linear-gradient(120deg, rgba(0,0,0,0.7), rgba(0,0,0,0.3)),
                url("Images/login-bg-bar.jpg");
            background-size: cover;
            background-position: center;
            filter: blur(2px);
            transform: scale(1.03);
            z-index: -2;
        }

        body::after {
            content: "";
            position: fixed;
            width: 130vmax;
            height: 130vmax;
            background:
                radial-gradient(circle at 20% 20%, rgba(255, 111, 145, 0.33) 0, transparent 55%),
                radial-gradient(circle at 80% 80%, rgba(255, 213, 79, 0.3) 0, transparent 55%),
                radial-gradient(circle at 50% 10%, rgba(0, 188, 212, 0.32) 0, transparent 55%);
            mix-blend-mode: screen;
            opacity: 0.85;
            animation: floatGlow 20s infinite alternate ease-in-out;
            z-index: -1;
        }

        @keyframes floatGlow {
            0%   { transform: translate3d(-8%, -4%, 0) rotate(0deg); }
            50%  { transform: translate3d(6%, 4%, 0) rotate(10deg); }
            100% { transform: translate3d(10%, -6%, 0) rotate(-12deg); }
        }

        .page-wrapper {
            width: 100%;
            max-width: 960px;
            padding: 24px;
        }

        .shell {
            background: rgba(10, 10, 25, 0.80);
            backdrop-filter: blur(18px);
            -webkit-backdrop-filter: blur(18px);
            border-radius: 28px;
            border: 1px solid rgba(255, 255, 255, 0.16);
            box-shadow:
                0 20px 50px rgba(0, 0, 0, 0.85),
                0 0 0 1px rgba(255, 255, 255, 0.04);
            padding: 26px 26px 22px;
            position: relative;
            overflow: hidden;
            animation: shellEnter 0.7s ease-out forwards;
            opacity: 0;
            transform: translateY(18px) scale(0.97);
        }

        @keyframes shellEnter {
            to {
                opacity: 1;
                transform: translateY(0) scale(1);
            }
        }

        .shell::before {
            content: "";
            position: absolute;
            inset: -40%;
            background:
                radial-gradient(circle at 0% 0%, rgba(255, 193, 7, 0.18) 0, transparent 60%),
                radial-gradient(circle at 100% 100%, rgba(255, 82, 82, 0.2) 0, transparent 60%);
            opacity: 0.7;
            mix-blend-mode: soft-light;
            z-index: -1;
        }

        .header {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            gap: 18px;
            margin-bottom: 22px;
        }

        .brand {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .brand-title {
            font-size: 26px;
            font-weight: 700;
            letter-spacing: 0.12em;
            text-transform: uppercase;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .brand-accent {
            background: linear-gradient(120deg, #ff8a65, #ffd54f);
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .brand-dot {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: radial-gradient(circle at 30% 30%, #fff, #ff8a65);
            box-shadow: 0 0 12px rgba(255, 171, 145, 0.9);
            animation: pulseDot 1.6s infinite ease-in-out;
        }

        @keyframes pulseDot {
            0%, 100% { transform: scale(1); opacity: 1; }
            50%      { transform: scale(1.3); opacity: 0.6; }
        }

        .brand-pill {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.22em;
            color: rgba(255, 255, 255, 0.7);
        }

        .welcome {
            font-size: 14px;
            color: rgba(255, 255, 255, 0.76);
        }

        .welcome strong {
            color: #ffd54f;
        }

        .status-pill {
            font-size: 12px;
            padding: 6px 11px;
            border-radius: 999px;
            background: rgba(56, 142, 60, 0.22);
            border: 1px solid rgba(129, 199, 132, 0.8);
            color: #c8e6c9;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .status-dot {
            width: 7px;
            height: 7px;
            border-radius: 50%;
            background: #81c784;
            box-shadow: 0 0 6px rgba(129, 199, 132, 0.9);
        }

        .logout-btn {
            border: none;
            background: transparent;
            color: rgba(255, 255, 255, 0.7);
            font-size: 12px;
            cursor: pointer;
            text-decoration: underline;
            text-underline-offset: 3px;
        }

        .choices {
            margin-top: 10px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 16px;
        }

        .choice-card {
            background: radial-gradient(circle at 0% 0%, rgba(255, 255, 255, 0.12) 0, transparent 55%),
                        rgba(8, 8, 20, 0.75);
            border-radius: 18px;
            border: 1px solid rgba(255, 255, 255, 0.12);
            padding: 16px 16px 14px;
            position: relative;
            overflow: hidden;
            cursor: pointer;
            transition:
                transform 0.16s ease,
                box-shadow 0.18s ease,
                border-color 0.18s ease,
                background 0.2s ease;
        }

        .choice-card::after {
            content: "";
            position: absolute;
            inset: 0;
            background: radial-gradient(circle at 120% 0%, rgba(255, 193, 7, 0.18) 0, transparent 55%);
            opacity: 0;
            transition: opacity 0.2s ease;
        }

        .choice-card:hover {
            transform: translateY(-4px);
            box-shadow:
                0 16px 30px rgba(0, 0, 0, 0.9),
                0 0 16px rgba(255, 213, 79, 0.3);
            border-color: rgba(255, 213, 79, 0.7);
            background: rgba(6, 6, 18, 0.95);
        }

        .choice-card:hover::after {
            opacity: 1;
        }

        .choice-content {
            position: relative;
            z-index: 1;
        }

        .choice-title {
            font-size: 15px;
            font-weight: 600;
            margin-bottom: 6px;
        }

        .choice-badge {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.16em;
            color: rgba(255, 255, 255, 0.6);
            margin-bottom: 4px;
        }

        .choice-desc {
            font-size: 13px;
            color: rgba(255, 255, 255, 0.74);
        }

        .choice-pill {
            margin-top: 10px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            font-size: 11px;
            padding: 4px 9px;
            border-radius: 999px;
            background: rgba(0, 0, 0, 0.35);
            border: 1px solid rgba(255, 255, 255, 0.28);
        }

        .choice-dot {
            width: 6px;
            height: 6px;
            border-radius: 50%;
        }

        .dot-gold   { background: #ffd54f; box-shadow: 0 0 7px rgba(255, 213, 79, 0.8); }
        .dot-pink   { background: #ff8a65; box-shadow: 0 0 7px rgba(255, 138, 101, 0.8); }
        .dot-cyan   { background: #80deea; box-shadow: 0 0 7px rgba(128, 222, 234, 0.8); }

        .choices a {
            text-decoration: none;
            color: inherit;
        }

        .footer {
            margin-top: 18px;
            font-size: 11px;
            color: rgba(255, 255, 255, 0.6);
            display: flex;
            justify-content: space-between;
            gap: 10px;
            flex-wrap: wrap;
        }

        .footer span {
            color: rgba(255, 213, 79, 0.9);
        }

        @media (max-width: 720px) {
            .shell {
                padding: 22px 18px 18px;
            }
        }
        /* --- UNIFORMITÀ DELLE CARD --- */
        .choice-card {
            min-height: 185px;             /* altezza uniforme */
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .choice-card .choice-title {
            min-height: 42px;              /* titoli allineati */
        }

        .choice-card .choice-desc {
            min-height: 60px;              /* descrizioni allineate */
        }

    </style>
</head>
<body>

<div class="page-wrapper">
    <div class="shell">
        <div class="header">
            <div class="brand">
                <div class="brand-title">
                    <span class="brand-accent">GugaRistò</span>
                    <span class="brand-dot"></span>
                </div>
                <div class="brand-pill">Area cliente</div>
                <p class="welcome">
                    Accesso riuscito,
                    <strong><%= u.getNome() != null ? u.getNome() : u.getUsername() %></strong>!<br>
                    Scegli cosa desideri fare:
                </p>
            </div>
            <div style="text-align: right;">
                <div class="status-pill">
                    <span class="status-dot"></span>
                    <span>Cliente autenticato</span>
                </div>
                <form action="logout" method="post" style="margin-top: 8px;">
                    <button type="submit" class="logout-btn">Esci</button>
                </form>
            </div>
        </div>

        <div class="choices">
            <!-- SOLO PRENOTAZIONE TAVOLO -->
            <a href="prenotaTavolo">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">Sala & Tavoli</div>
                        <div class="choice-title">Prenota un tavolo</div>
                        <p class="choice-desc">
                            Scegli data, orario e numero di persone.
                            Arrivi e trovi il tavolo pronto.
                        </p>
                        <div class="choice-pill">
                            <span class="choice-dot dot-gold"></span>
                            <span>Prenotazione semplice</span>
                        </div>
                    </div>
                </div>
            </a>

            <!-- PRENOTA TAVOLO + ORDINA IN ANTICIPO -->
            <a href="ordinaSmart">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">Ordini al ristorante</div>
                        <div class="choice-title">Prenota & ordina in anticipo</div>
                        <p class="choice-desc">
                            Prenoti il tavolo e scegli già cosa mangiare.
                            Quando arrivi è tutto pronto.
                        </p>
                        <div class="choice-pill">
                            <span class="choice-dot dot-pink"></span>
                            <span>Esperienza completa</span>
                        </div>
                    </div>
                </div>
            </a>

            <!-- GESTIONE PRENOTAZIONI -->
            <a href="prenotazioni">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">Le tue prenotazioni</div>
                        <div class="choice-title">Visualizza prenotazioni</div>
                        <p class="choice-desc">
                            Controlla le prenotazioni attive, cancellale
                            oppure aggiungine di nuove.
                        </p>
                        <div class="choice-pill">
                            <span class="choice-dot dot-gold"></span>
                            <span>Gestione prenotazioni</span>
                        </div>
                    </div>
                </div>
            </a>

            <!-- SOLO ORDINE (DOMICILIO / ASPORTO) -->
            <a href="ordinaDaCasa">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">Delivery & Asporto</div>
                        <div class="choice-title">Ordina da casa</div>
                        <p class="choice-desc">
                            Scegli i piatti e ricevili a domicilio
                            oppure ritirali al locale.
                        </p>
                        <div class="choice-pill">
                            <span class="choice-dot dot-cyan"></span>
                            <span>Domicio / Asporto</span>
                        </div>
                    </div>
                </div>
            </a>

            <!-- STORICO ORDINI -->
            <a href="storicoOrdini">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">I tuoi ordini</div>
                        <div class="choice-title">Storico ordini</div>
                        <p class="choice-desc">
                            Rivedi gli ordini passati, controlla i dettagli
                            e riordina velocemente i tuoi preferiti.
                        </p>
                        <div class="choice-pill pill-small">
                            <span class="choice-dot dot-gold"></span>
                            <span>Ordini passati</span>
                        </div>
                    </div>
                </div>
            </a>



            <!-- LASCIA UNA RECENSIONE -->
            <a href="lasciaRecensione">
                <div class="choice-card">
                    <div class="choice-content">
                        <div class="choice-badge">Feedback</div>
                        <div class="choice-title">Lascia una recensione</div>
                        <p class="choice-desc">
                            Valuta la tua esperienza e aiuta altri utenti.
                        </p>
                        <div class="choice-pill pill-small">
                            <span class="choice-dot dot-pink"></span>
                            <span>Lascia un’opinione</span>
                        </div>
                    </div>
                </div>
            </a>

        </div>

        <div class="footer">
            <div>
                Accesso come <span>Cliente</span>.
                Qui puoi gestire <span>prenotazioni</span>, <span>ordini</span> e il tuo <span>feedback</span>.
            </div>
            <div>
                GugaRistò &mdash; esperienze guidate tra <span>tavoli</span>, <span>ordini smart</span> e <span>recensioni</span>.
            </div>
        </div>
    </div>
</div>

</body>
</html>
