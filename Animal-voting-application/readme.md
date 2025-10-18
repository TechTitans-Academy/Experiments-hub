# 🐾 Voting Application 🗳️

This is a simple web application where users can vote for their favorite animal — Elephant, Lion, Cat, or Dog. The app is built with Flask, uses Redis for caching votes, and PostgreSQL for persistent storage. It runs seamlessly using Docker Compose.

### 📂 Project Structure

```
animal-voting-app/
├── app/
│   ├── app.py
│   ├── db.py
│   ├── redis_client.py
│   ├── requirements.txt
│   └── templates/
│       └── index.html
├── Dockerfile
├── docker-compose.yml
└── README.md
```

⚙️ How It Works

1. <b>User Interface:</b>
The front end shows a list of animals. Users click on the animal they want to vote for.

5. <b>Vote Handling:</b>
When a user votes, the Flask backend receives the request and increments the vote count for that animal in Redis. Redis provides fast in-memory storage for real-time vote tracking.

3. <b>Vote Persistence:</b>
After updating Redis, the backend also updates the persistent vote count in the PostgreSQL database. This ensures votes are saved permanently and can survive service restarts.

4. <b>Displaying Results:</b>
On every page load, the backend fetches the current vote counts from Redis and passes them to the frontend, where the vote counts are displayed next to each animal.

5. <b>Docker Compose Orchestration:</b> Docker Compose manages the lifecycle and networking of these containers and the application runs three services:

- <b>web</b>: Flask app serving the frontend and backend logic. <br>
- <b>redis</b>: Caching layer for quick vote increments. <br>
- <b>db</b>: PostgreSQL database for durable storage.<br>

6. <b>Startup Resilience:</b>
    The Flask app includes retry logic to wait for PostgreSQL to be ready before connecting, preventing connection errors during startup.

    
### 🛠️ Features:

1. Vote for 1 of 4 animals.
2. Redis stores votes in real-time.
3. Votes are persisted to PostgreSQL.
4. Fully containerized using Docker.
5. Auto-retry logic for DB connection during startup.


### 🧪 Example Redis Keys

```
vote:elephant -> 10
vote:lion     -> 5
vote:cat      -> 7
vote:dog      -> 3
```

### ▶️ Run the App

```
docker-compose up --build
```

Open `http://localhost:5000` on the browser.
