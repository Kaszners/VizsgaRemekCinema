# Vizsgaremek Cinema Backend

Spring Boot alapú mozijegy-foglaló backend rendszer JWT alapú hitelesítéssel, szerepkörökkel, emailes foglalás-megerősítéssel és automatikus foglalás-lejárat kezeléssel.

## Tartalom
- [Fő funkciók](#fő-funkciók)
- [Technológiai stack](#technológiai-stack)
- [Gyors indulás](#gyors-indulás)
- [Konfiguráció](#konfiguráció)
- [ER diagram](#er-diagram)
- [API dokumentáció](#api-dokumentáció)
- [Projekt szerkezet](#projekt-szerkezet)

## Fő funkciók
- Regisztráció és bejelentkezés JWT tokennel.
- Nyilvános listázás: filmek, termek, vetítések.
- Székhelyek elérhetőségének lekérdezése vetítésenként.
- Foglalás több székre egy kérésben.
- Emailes foglalás-megerősítés tokennel (`/cinema/booking/confirm`).
- Foglalás lemondása saját felhasználóhoz kötve.
- Szerepköralapú admin funkciók (USER / STAFF / ADMIN).
- Automatikus lejáratás: a 10 percnél régebbi `PENDING` foglalások `EXPIRED` állapotba kerülnek.

## Technológiai stack
- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security + JWT
- Spring Validation
- H2 / PostgreSQL
- Spring Mail + Thymeleaf (HTML email)
- springdoc OpenAPI / Swagger UI

## Gyors indulás

### 1) Build
```bash
./mvnw clean verify
```

### 2) Futtatás
```bash
./mvnw spring-boot:run
```

Alapértelmezett base URL (lokálisan):
- `http://localhost:8080`

### 3) Swagger
- UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Konfiguráció
A projektben nincs verziózott `application.properties`, ezért a környezeti beállításokat futtatáskor kell megadni (pl. env változóval vagy saját config fájllal).

### Kötelező / erősen ajánlott kulcsok

#### JWT
- `jwt.secret` – Base64 kódolt titok (legalább 256 bites kulcs).
- `jwt.expiration` – token lejárat milliszekundumban.

#### Alap admin seed
- `app.default-admin.username`
- `app.default-admin.password`
- `app.default-admin.email`

#### Email küldés
- `spring.mail.username`
- SMTP kapcsolódó kulcsok (`spring.mail.host`, `spring.mail.port`, stb.)

#### Adatbázis
- H2 vagy PostgreSQL datasource beállítások.

Példa minimális lokális konfiguráció (`application-local.properties`):
```properties
server.port=8080

spring.datasource.url=jdbc:h2:mem:cinema;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

jwt.secret=<BASE64_SECRET>
jwt.expiration=3600000

app.default-admin.username=admin
app.default-admin.password=Admin1234!
app.default-admin.email=admin@cinema.local

spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=no-reply@cinema.local
spring.mail.password=
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false
```

## ER diagram

```mermaid
erDiagram
    USER ||--|| USER_CREDENTIALS : has
    USER ||--o{ BOOKING : creates
    MOVIE ||--o{ SHOWTIME : scheduled_as
    THEATER ||--o{ SHOWTIME : hosts
    THEATER ||--o{ SEAT : contains
    SHOWTIME ||--o{ BOOKING : reserved_for
    SEAT ||--o{ BOOKING : selected_in

    USER {
        bigint id PK
        string role
        string email_address UK
        string full_name
    }

    USER_CREDENTIALS {
        bigint id PK
        bigint user_id FK UK
        string username UK
        string password
    }

    MOVIE {
        bigint id PK
        string movie_title
        int movie_duration_minutes
        string poster_url
        string trailer_url
    }

    THEATER {
        bigint id PK
        string name UK
        string theater_size
    }

    SEAT {
        bigint id PK
        bigint theater_id FK
        int row_number
        int seat_number
    }

    SHOWTIME {
        bigint id PK
        bigint movie_id FK
        bigint theater_id FK
        datetime show_start_time
    }

    BOOKING {
        bigint id PK
        bigint user_id FK
        bigint showtime_id FK
        bigint seat_id FK
        string booking_status
        datetime created
        string booking_confirmation_token
    }
```

> Megjegyzés: a foglalási ütközés kezelését adatbázis unique constraint + tranzakciós ellenőrzés kombináció biztosítja (`seat_id`, `showtime_id`).

## API dokumentáció
Rövid áttekintés itt található: [`docs/API_REFERENCE.md`](docs/API_REFERENCE.md)

Részletes működési és üzleti szabály dokumentáció: [`docs/SYSTEM_DOCUMENTATION.md`](docs/SYSTEM_DOCUMENTATION.md)

## Projekt szerkezet
```text
src/main/java/hu/nyirszikszi/vizsgaremek/cinema
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── scheduler
├── security
├── seeder
├── service
└── util
```
