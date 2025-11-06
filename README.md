# 📝 Secure Note Application

A secure note-taking application with friend system and note sharing capabilities.

## 🚀 Features
- 🔐 User Authentication with JWT
- 👥 Friend Management System
- 📝 Secure Note Creation and Sharing
- 🔒 Access Control
- 🌐 REST API



## 🛠 Tech Stack
- **Framework**: Spring Boot 3
- **Security**: Spring Security with JWT
- **Database**: MySQL (Docker)
- **Documentation**: OpenAPI (Swagger)
- **Build Tool**: Maven
- **Java Version**: 21

## 🏃‍♂️ Running the Application

### Prerequisites
- Java 21
- Docker
- Maven

### Steps
1. Clone the repository
2. Start the database:
```bash
docker-compose up -d
```
1. Run the application:
```bash
mvn spring-boot:run
```

## 📚 API Documentation

The API documentation is available via Swagger UI at: `http://localhost:8080/swagger-ui/index.html`

### Example Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/logout` - Logout user

## 📝 Note Management

Notes can be private or shared with friends.

### Example Endpoints
- `GET /api/notes` - Get user's notes
- `POST /api/notes` - Create new note
- `PUT /api/notes/{id}` - Update note
- `DELETE /api/notes/{id}` - Delete note
- `GET /api/notes/shared` - Get shared notes

## 👥 Friend System

Users can add friends and share notes with them.

### Example Endpoints
- `GET /api/friends` - Get user's friends
- `POST /api/friends/{username}` - Add friend
- `DELETE /api/friends/{username}` - Remove friend

### Swagger UI Screenshot
![Swagger UI](docs/images/swagger-ui.png)

## 🔒 Security Features
- Password encryption using BCrypt
- JWT-based authentication
- HTTP-only cookies
- CORS configuration
- Protected endpoints
- Input validation

## 🏗 Architecture

### Database Diagram
![DB Diagram](docs/images/db.png)

### Class Diagram
```mermaid
classDiagram
    class UserEntity {
        -Long id
        -String username
        -String password
        +getId()
        +getUsername()
        +getPassword()
    }

    class Note {
        -Long id
        -String title
        -String description
        -LocalDate date
        -String owner
        -boolean isShared
    }

    class Friend {
        -Long id
        -String user
        -String friend
    }

    class AuthRequest {
        <<record>>
        +String username
        +String password
    }

    class AuthResponse {
        <<record>>
        +String token
    }

    class UserRequest {
        <<record>>
        +String username
        +String password
    }

    class UserResponse {
        <<record>>
        +Long id
        +String username
    }

    class AuthService {
        +AuthResponse login(AuthRequest)
        +AuthResponse register(UserRequest)
    }

    class UserService {
        +UserEntity getByUsername(String)
        +boolean checkPassword(UserEntity, String)
        +boolean existsByUsername(String)
        +UserEntity save(UserEntity)
    }

    class NoteService {
        +List<Note> getNotesByOwner(String)
        +Note createNote(Note, String)
        +Note updateNote(Long, Note, String)
        +void deleteNote(Long, String)
        +List<Note> getSharedNotes()
        +Note toggleShareStatus(Long, Authentication)
    }

    class FriendService {
        +Friend addFriend(String, String)
        +void deleteFriend(String, String)
        +List<Friend> getMyFriends(String)
        +List<Friend> getUsersWhoAddedMe(String)
    }

    UserEntity --o AuthService
    UserEntity --o UserService
    Note --o NoteService
    Friend --o FriendService
    AuthService --> AuthResponse
    AuthService --> UserService
    NoteService --> FriendService
    AuthRequest --o AuthService
    UserRequest --o AuthService
```

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
