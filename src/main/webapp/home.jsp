<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>GugaRisto – Area Utente</title>

    <link href="https://fonts.googleapis.com/css2?family=Cinzel:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: "Cinzel", serif;
background: url('Images/chat.png') center/cover no-repeat fixed;
            position: relative;
        }

        body::before {
            content: "";
            position: absolute;
            inset: 0;
            backdrop-filter: blur(3px);
            background: rgba(0, 0, 0, 0.45);
            z-index: 1;
        }

        .card {
            position: relative;
            z-index: 2;
            width: 90%;
            max-width: 550px;
            padding: 40px 45px;
            text-align: center;
            border-radius: 20px;
            background: rgba(255, 255, 255, 0.12);
            backdrop-filter: blur(2px);
            border: 1px solid rgba(255, 255, 255, 0.25);
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.35);
            color: #fff;
        }

        h1 {
            font-size: 34px;
            margin-bottom: 10px;
        }

        p {
            font-size: 22px;
            opacity: 0.9;
            margin-bottom: 30px;
        }

        a.btn {
            display: inline-block;
            padding: 12px 26px;
            background: #2b2b2b;
            color: #fff;
            border-radius: 50px;
            text-decoration: none;
            font-weight: bold;
            font-size: 17px;
            transition: 0.2s ease;
            box-shadow: 0 4px 15px rgba(0,0,0,0.30);
        }

        a.btn:hover {
            background: #1a1a1a;
            transform: translateY(-3px);
            box-shadow: 0 10px 22px rgba(0,0,0,0.40);
        }

    </style>
</head>
<body>

<div class="card">
    <h1>Benvenuto nell'area GugaRisto'</h1>

    <p>
        Accesso effettuato con successo.<br>
        Scegli cosa desideri fare:
    </p>

    <a href="/menu" class="btn">Visualizza Menu</a><br><br>
    <a href="/prenotazioni" class="btn">Prenota un tavolo</a><br><br>
    <a href="/ordini" class="btn">Ordini effetuati</a><br><br>
    <a href="/logout" class="btn">Esci</a>
</div>

</body>
</html>
