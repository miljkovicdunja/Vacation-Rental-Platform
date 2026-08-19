# Vacation-Rental-Platform

This is a student project built for a university course: a full-stack web application for booking vacation rental houses, supporting multiple user roles: tourist, owner, and administrator.

## Tech Stack

- **Frontend:** Angular 18
- **Backend:** Spring Boot 3.5
- **Database:** MySQL

## Features

- User registration and login (tourist / owner / administrator)
- Browsing and searching vacation rentals
- Creating and managing reservations
- Image upload for rental listings
- Admin approval/deactivation of user accounts

## Prerequisites

Make sure you have the following installed before running the project:

- **Java 17+** (required by Spring Boot 3.5)
- **Node.js** (LTS version) — required to run the Angular frontend. Includes `npm`.
- **MySQL Server** (or run one via Docker)
- 
  !Note: You do not need Maven installed separately — the project includes a Maven Wrapper (`mvnw` / `mvnw.cmd`), which downloads the correct Maven version automatically on first run.

## Running the Project Locally

### 1. Set Up the Database

Create the database:

```sql
CREATE DATABASE vikendice;
```

Import the schema from `database/mySQLPiaProjekat.sql`:

```bash
mysql -u root -p vikendice < database/mySQLPiaProjekat.sql
```

### 2. Configure the Backend

Database connection settings are defined in:
`backend/src/main/java/com/example/demo/db/DB.java`

```java
ds.setUrl("jdbc:mysql://localhost:3306/vikendice");
ds.setUsername("root");
ds.setPassword("");
```

Update the username/password there to match your local MySQL setup before running the backend.

### 3. Run the Backend

From the `backend` folder:

```bash
cd backend
./mvnw.cmd spring-boot:run
```

(On the first run, the Maven Wrapper will automatically download the required Maven version — this may take a bit longer than usual.)

The backend will run on `http://localhost:8080`.

### 4. Run the Frontend

Open a **separate terminal** (keep the backend running in the first one). From the `frontend` folder:

```bash
cd frontend
npm install
npm start
```

`npm install` only needs to be run once (or whenever `package.json` changes). `npm start` is equivalent to `ng serve`.

The frontend will be available at `http://localhost:4200`.

## Troubleshooting

- **`mvn: command not found`** — use the included wrapper instead (`./mvnw.cmd spring-boot:run`), no separate Maven installation needed.
- **`npm: command not found`** — Node.js is not installed. Download the LTS version from [nodejs.org](https://nodejs.org), install it, then close and reopen your terminal before trying again.
- **`npm warn deprecated ...`** — these are harmless warnings from third-party dependencies, not errors. Safe to ignore.
- **Backend fails to connect to the database** — double check that MySQL is running, the database name matches (`vikendice`), and the username/password in `DB.java` match your local MySQL setup.
