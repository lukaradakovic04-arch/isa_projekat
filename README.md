# ISA Library - Spring MVC zavrsni projekat

Jednostavna aplikacija za upravljanje bibliotekom napravljena kao zavrsni projekat za predmet
**Internet softverske arhitekture**.

## Tehnologije

- Java 17
- Spring Boot / Spring MVC
- Spring Data JPA / Hibernate
- Spring Security
- Thymeleaf
- MySQL
- Maven
- Postman

## Glavne tabele

1. `users`
2. `roles`
3. `books`
4. `authors`
5. `categories`

Hibernate dodatno kreira spojne tabele `user_roles` i `book_authors`.

## Relacije

- OneToMany: `Category -> Book`
- ManyToMany: `Book <-> Author`
- ManyToMany: `User <-> Role`

## Demo nalozi

- ADMIN: `admin` / `admin123`
- USER: `user` / `user123`

ADMIN moze da radi CRUD. USER moze da vidi knjige, autore i kategorije.

## Pokretanje

1. Instalirati JDK 17+, IntelliJ IDEA, MySQL Server/Workbench, Maven i Postman.
2. U MySQL Workbench pokrenuti `database/create_database.sql`.
3. U `src/main/resources/application.properties` promeniti:
   `spring.datasource.password=CHANGE_ME`
   na svoju MySQL root lozinku.
4. Otvoriti projekat u IntelliJ IDEA kao Maven projekat.
5. Pokrenuti `IsaLibraryApplication`.
6. Otvoriti `http://localhost:8080`.
7. Ulogovati se kao `admin / admin123`.