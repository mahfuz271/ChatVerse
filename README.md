# ChatVerse

A desktop chat application built with **Java Swing** and **MySQL**, with a TCP socket server for login/registration. Built as a university assignment.

- Initial version: **June 2023**
- Server/login refactor: **December 2024**

---

## Features

- **User registration & login** — handled via a TCP socket server ([Server.java](src/chatverse/Server.java)) on port `4501`, with credentials stored in MySQL.
- **One-to-one chat** between any two registered users.
- **Chat list** — sidebar shows everyone you've previously chatted with.
- **"New Chat" dialog** — pick from a list of all registered users to start a conversation.
- **Auto-refresh** — chat list and message area refresh every 1.5 seconds via a Swing `Timer`, simulating realtime updates.
- **Auto-scroll** to the newest message.
- **Logout** — returns to the login screen.
- **Centered window** on any screen resolution.

---

## Tech Stack

| Layer       | Technology                          |
|-------------|-------------------------------------|
| UI          | Java Swing (NetBeans Form Editor)   |
| Networking  | Java Sockets (`java.net.Socket`)    |
| Database    | MySQL / MariaDB                     |
| JDBC Driver | `mysql-connector-j-8.0.33.jar`      |
| Build       | Apache Ant (NetBeans project)       |
| Java        | Source/target **15**                |

---

## Project Structure

```
chatverse/
├── src/chatverse/
│   ├── Account.java          # Login + Register window (main class)
│   ├── chat.java             # Chat window
│   ├── DB.java               # Direct JDBC helper (used by chat.java)
│   ├── Server.java           # TCP server for auth on port 4501
│   └── ConnectionManager.java# Client-side socket singleton
├── chatverse.sql             # Database schema + demo data
├── lib/                      # MySQL connector JAR
├── build.xml / nbproject/    # NetBeans Ant build files
└── ChatVerse Java Chat App.pdf
```

---

## Setup

### 1. Prerequisites

- **JDK 15+** (any later JDK should also work)
- **MySQL** or **MariaDB** running on `localhost:3306`
- **NetBeans IDE** (recommended — project is a NetBeans Ant project) or Apache Ant directly

### 2. Create the database

Import [chatverse.sql](chatverse.sql) into MySQL:

```bash
mysql -u root -p < chatverse.sql
```

This creates a `chatverse` database with two tables: `user` and `chat`, plus some demo accounts. Passwords are stored as SHA-256 hashes; the demo users all log in with the plain-text password `1234`.

### 3. Configure database credentials

DB credentials are currently **hardcoded** as `root` with an empty password in two places — update them if your MySQL setup is different:

- [DB.java:11](src/chatverse/DB.java#L11)
- [Server.java:36](src/chatverse/Server.java#L36)

### 4. Open in NetBeans

1. Open the project folder in NetBeans (it auto-detects the Ant project).
2. The `lib/mysql-connector-j-8.0.33.jar` is already on the classpath.
3. **Right-click `Server.java` → Run File** to start the auth server (listens on `4501`).
4. **Run the project** (main class is `chatverse.Account`) to launch the login window.
5. Register a new account, then log in to chat. Run a second instance to chat between two users.

### 5. Building from the command line

```bash
ant clean jar
java -jar dist/ChatVerse.jar
```

(Start `Server.java` separately first.)

---

## Demo Accounts (from `chatverse.sql`)

Passwords are stored as SHA-256 hashes; log in with the plain-text password `1234`.

| Username | Password |
|----------|----------|
| mahfuz   | 1234     |
| rahim    | 1234     |
| sohag    | 1234     |
| joy      | 1234     |

---

## License

Released under the [MIT License](LICENSE).
