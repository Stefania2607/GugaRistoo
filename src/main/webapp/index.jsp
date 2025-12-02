<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>GugaRisto – Benvenuto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Font -->
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

            /* 👉 tua immagine qui */
            background: url('Images/chat.png') center/cover no-repeat fixed;

            position: relative;
        }

        /* sfocatura elegante */
        body::before {
            content: "";
            position: absolute;
            inset: 0;
            backdrop-filter: blur(2px);
            background: rgba(0, 0, 0, 0.45);
            z-index: 1;
        }

        .card {
            position: relative;
            z-index: 2;
            width: 90%;
            max-width: 500px;
            padding: 35px 40px;
            text-align: center;
            border-radius: 20px;

            /* effetto vetro */
            background: rgba(255, 255, 255, 0.10);
            backdrop-filter: blur(2px);
            border: 1px solid rgba(255, 255, 255, 0.25);
            box-shadow: 0 20px 40px rgba(0,0,0,0.3);
        }

        h1 {
            font-size: 36px;
            font-weight: 600;
            margin-bottom: 10px;
            color: #fff;
            letter-spacing: 1px;
        }

        p {
            font-size: 22px;
            color: #f3f4f6;
            margin-bottom: 28px;
            line-height: 1.5;
        }

        a.cta {
            display: inline-block;
            padding: 12px 28px;
            background: #2b2b2b; /* grigio scuro */
            color: #fff;
            border-radius: 50px;
            font-size: 18px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.2s ease;
            box-shadow: 0 4px 15px rgba(0,0,0,0.30);
        }

        a.cta:hover {
            background: #1f1f1f; /* grigio più scuro */
            transform: translateY(-3px);
            box-shadow: 0 10px 22px rgba(0,0,0,0.40);
        }

    </style>
</head>
<body>

<div class="card">

    <h1>Benvenuto in GugaRisto'</h1>

    <p>
        Tra luci calde e profumi intensi, <br>
        <strong>il tuo tavolo ti aspetta.</strong>
    </p>

    <a href="home.jsp" class="cta">Entra nell'app</a>


</div>

</body>
</html>