<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login - GugaRistò</title>

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

        /* SFONDO: immagine + overlay animato */
        body::before {
            content: "";
            position: fixed;
            inset: 0;
            background-image:
                linear-gradient(120deg, rgba(0,0,0,0.65), rgba(0,0,0,0.25)),
                url("Images/login-bg-bar.jpg"); /* METTI QUI LA TUA IMMAGINE */
            background-size: cover;
            background-position: center;
            filter: blur(2px);
            transform: scale(1.03);
            z-index: -2;
        }

        /* luci colorate che si muovono sullo sfondo */
        body::after {
            content: "";
            position: fixed;
            width: 120vmax;
            height: 120vmax;
            background:
                radial-gradient(circle at 20% 30%, rgba(255, 111, 145, 0.35) 0, transparent 55%),
                radial-gradient(circle at 80% 70%, rgba(255, 193, 7, 0.3) 0, transparent 55%),
                radial-gradient(circle at 50% 10%, rgba(0, 188, 212, 0.35) 0, transparent 55%);
            mix-blend-mode: screen;
            opacity: 0.9;
            animation: floatGlow 18s infinite alternate ease-in-out;
            z-index: -1;
        }

        @keyframes floatGlow {
            0% {
                transform: translate3d(-10%, -5%, 0) rotate(0deg);
            }
            50% {
                transform: translate3d(5%, 5%, 0) rotate(15deg);
            }
            100% {
                transform: translate3d(10%, -5%, 0) rotate(-10deg);
            }
        }

        .login-wrapper {
            width: 100%;
            max-width: 420px;
            padding: 24px;
        }

        .card {
            background: rgba(10, 10, 20, 0.78);
            backdrop-filter: blur(18px);
            -webkit-backdrop-filter: blur(18px);
            border-radius: 24px;
            border: 1px solid rgba(255, 255, 255, 0.18);
            box-shadow:
                0 18px 40px rgba(0, 0, 0, 0.7),
                0 0 0 1px rgba(255, 255, 255, 0.05);
            padding: 32px 30px 28px;
            position: relative;
            overflow: hidden;
            animation: cardEnter 0.7s ease-out forwards;
            transform: translateY(10px);
            opacity: 0;
        }

        @keyframes cardEnter {
            from {
                opacity: 0;
                transform: translateY(20px) scale(0.97);
            }
            to {
                opacity: 1;
                transform: translateY(0) scale(1);
            }
        }

        /* piccolo bagliore dietro la card */
        .card::before {
            content: "";
            position: absolute;
            inset: -40%;
            background:
                radial-gradient(circle at 0% 0%, rgba(255, 193, 7, 0.16) 0, transparent 55%),
                radial-gradient(circle at 100% 100%, rgba(255, 82, 82, 0.18) 0, transparent 55%);
            opacity: 0.7;
            mix-blend-mode: soft-light;
            z-index: -1;
        }

        .logo {
            display: flex;
            flex-direction: column;
            gap: 4px;
            margin-bottom: 26px;
        }

        .logo-title {
            font-size: 26px;
            letter-spacing: 0.12em;
            text-transform: uppercase;
            font-weight: 700;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .logo-pill {
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.22em;
            color: rgba(255, 255, 255, 0.65);
        }

        .logo-accent {
            background: linear-gradient(120deg, #ff8a65, #ffd54f);
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .logo-dot {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: radial-gradient(circle at 30% 30%, #fff, #ff8a65);
            box-shadow: 0 0 12px rgba(255, 171, 145, 0.9);
            animation: pulseDot 1.6s infinite ease-in-out;
        }

        @keyframes pulseDot {
            0%, 100% {
                transform: scale(1);
                opacity: 1;
            }
            50% {
                transform: scale(1.3);
                opacity: 0.6;
            }
        }

        .subtitle {
            font-size: 13px;
            color: rgba(255, 255, 255, 0.7);
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-size: 13px;
            margin-bottom: 6px;
            color: rgba(255, 255, 255, 0.8);
        }

        .input-wrapper {
            position: relative;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 12px 11px;
            border-radius: 12px;
            border: 1px solid rgba(255, 255, 255, 0.18);
            background: rgba(10, 10, 25, 0.7);
            color: #f5f5f5;
            font-size: 14px;
            outline: none;
            transition:
                border-color 0.2s ease,
                box-shadow 0.2s ease,
                background 0.2s ease,
                transform 0.08s ease;
        }

        input[type="text"]::placeholder,
        input[type="password"]::placeholder {
            color: rgba(255, 255, 255, 0.35);
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: rgba(255, 193, 7, 0.85);
            box-shadow: 0 0 0 1px rgba(255, 193, 7, 0.4),
                        0 0 24px rgba(255, 193, 7, 0.28);
            background: rgba(5, 5, 15, 0.95);
            transform: translateY(-1px);
        }

        .helper-row {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin: 4px 0 18px;
            font-size: 12px;
            color: rgba(255, 255, 255, 0.6);
        }

        .remember {
            display: flex;
            align-items: center;
            gap: 6px;
        }

        .link {
            color: rgba(255, 193, 7, 0.9);
            text-decoration: none;
            position: relative;
        }

        .link::after {
            content: "";
            position: absolute;
            left: 0;
            bottom: -1px;
            width: 0;
            height: 1px;
            background: rgba(255, 193, 7, 0.9);
            transition: width 0.2s ease;
        }

        .link:hover::after {
            width: 100%;
        }

        .btn-primary {
            width: 100%;
            border: none;
            border-radius: 999px;
            padding: 12px 18px;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 0.08em;
            text-transform: uppercase;
            cursor: pointer;
            background-image: linear-gradient(120deg, #ff8a65, #ffb74d, #ffd54f);
            color: #1a1305;
            box-shadow:
                0 10px 25px rgba(0, 0, 0, 0.8),
                0 0 12px rgba(255, 193, 7, 0.55);
            position: relative;
            overflow: hidden;
            transition:
                transform 0.12s ease,
                box-shadow 0.12s ease,
                filter 0.18s ease;
        }

        .btn-primary::before {
            content: "";
            position: absolute;
            top: 0;
            left: -40%;
            width: 40%;
            height: 100%;
            background: linear-gradient(120deg, rgba(255,255,255,0.7), transparent);
            transform: skewX(-20deg) translateX(-100%);
            opacity: 0;
        }

        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow:
                0 14px 30px rgba(0, 0, 0, 0.9),
                0 0 18px rgba(255, 213, 79, 0.9);
            filter: brightness(1.02);
        }

        .btn-primary:hover::before {
            opacity: 1;
            animation: swipe 0.7s ease-out;
        }

        @keyframes swipe {
            from {
                transform: skewX(-20deg) translateX(-120%);
            }
            to {
                transform: skewX(-20deg) translateX(260%);
            }
        }

        .btn-primary:active {
            transform: translateY(0);
            box-shadow:
                0 8px 18px rgba(0, 0, 0, 0.9),
                0 0 10px rgba(255, 213, 79, 0.8);
        }

        .error-message {
            margin-bottom: 14px;
            padding: 9px 11px;
            border-radius: 10px;
            background: rgba(244, 67, 54, 0.16);
            border: 1px solid rgba(244, 67, 54, 0.6);
            color: #ffb3b3;
            font-size: 13px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .error-badge {
            width: 18px;
            height: 18px;
            border-radius: 999px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            background: rgba(244, 67, 54, 0.85);
            font-size: 11px;
            font-weight: 700;
        }

        .footer-text {
            margin-top: 18px;
            font-size: 11px;
            text-align: center;
            color: rgba(255, 255, 255, 0.6);
        }

        .footer-text span {
            color: rgba(255, 213, 79, 0.88);
        }

        @media (max-width: 520px) {
            .card {
                padding: 26px 20px 22px;
            }
        }
    </style>
</head>

<body>

<div class="login-wrapper">
    <div class="card">
        <div class="logo">
            <div class="logo-title">
                <span class="logo-accent">GugaRistò</span>
                <span class="logo-dot"></span>
            </div>
            <div class="logo-pill">Area riservata</div>
            <p class="subtitle">Accedi per gestire ordini, prenotazioni e magia di sala.</p>
        </div>

        <% String errore = (String) request.getAttribute("erroreLogin"); %>
        <% if (errore != null) { %>
            <div class="error-message">
                <div class="error-badge">!</div>
                <span><%= errore %></span>
            </div>
        <% } %>

        <form action="login" method="post">

            <div class="form-group">
                <label for="username">Username</label>
                <div class="input-wrapper">
                    <input type="text"
                           id="username"
                           name="username"
                           placeholder="es. cameriere1, admin1, cliente1"
                           required>
                </div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <div class="input-wrapper">
                    <input type="password"
                           id="password"
                           name="password"
                           placeholder="Inserisci la tua password"
                           required>
                </div>
            </div>

            <div class="helper-row">
                <div class="remember">
                    <input type="checkbox" id="remember" style="accent-color:#ffd54f;">
                    <label for="remember" style="margin:0;font-size:12px;">Ricordami</label>
                </div>
                <a href="#" class="link">Password dimenticata?</a>
            </div>

            <button type="submit" class="btn-primary">Accedi</button>

        </form>

        <p class="footer-text">
            Accesso per <span>Clienti</span>, <span>Camerieri</span> e <span>Admin</span>.
        </p>
    </div>
</div>

</body>
</html>
