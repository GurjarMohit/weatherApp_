<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weather App</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Weather App</h1>
    <form action="weather" method="get">
        <input type="text" name="city" placeholder="Enter city name">
        <button type="submit">Get Weather</button>
    </form>
    <div id="weatherResult"></div>

    <script>
        document.querySelector('form').addEventListener('submit', async function(event) {
            event.preventDefault();
            const city = event.target.elements.city.value;
            const response = await fetch(`weather?city=${city}`);
            const data = await response.json();
            document.getElementById('weatherResult').innerHTML = `
                <h2>${data.name}</h2>
                <p>Temperature: ${data.main.temp}°C</p>
                <p>Humidity: ${data.main.humidity}%</p>
                <p>Wind Speed: ${data.wind.speed} m/s</p>
            `;
        });
    </script>
</body>
</html>

