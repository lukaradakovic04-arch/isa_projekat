# Projektna dokumentacija - ISA Library

## 1. Tema

ISA Library je jednostavna web aplikacija za administraciju biblioteke.

## 2. Izabrana arhitektura

Izabrana je **Spring MVC** varijanta.

Slojevi:

1. `controller` - obrada HTTP zahteva i izbor view-a
2. `service` - poslovna logika
3. `repository` - pristup bazi preko Spring Data JPA
4. `model` - JPA entiteti
5. `templates/static` - frontend

Tok zahteva:

`Browser -> Controller -> Service -> Repository -> MySQL`

Za jednostavne entitete (Author, Category, Role) Controller direktno koristi Repository.
Za Book i User postoji Service sloj jer imaju vise relacija i dodatnu logiku.

## 3. Baza podataka

Glavne tabele:

- users
- roles
- books
- authors
- categories

Spojne tabele:

- user_roles
- book_authors

### Relacije

- `Category 1 --- N Book` (OneToMany / ManyToOne)
- `Book N --- N Author` (ManyToMany)
- `User N --- N Role` (ManyToMany)

## 4. CRUD

CRUD je implementiran za svih pet glavnih entiteta:

- Books
- Authors
- Categories
- Users
- Roles

Korisnik sa rolom ADMIN moze da kreira, menja i briše podatke.
Običan USER ima samo pregled knjiga, autora i kategorija.

## 5. Autentifikacija i autorizacija

Koristi se Spring Security.

- forma za login: `/login`
- lozinke su BCrypt hashovane
- postoje role `ROLE_ADMIN` i `ROLE_USER`
- `/users/**` i `/roles/**` su dostupni samo ADMIN korisniku
- izmene knjiga/autora/kategorija su dozvoljene samo ADMIN korisniku

Demo korisnici:

- admin / admin123
- user / user123

## 6. Upravljanje sesijama

Ne koristi se JWT zato što je izabrana MVC varijanta.

Spring Security koristi HTTP sesiju (`JSESSIONID`).

Podešeno je:

- timeout sesije 30 minuta
- najviše 1 aktivna sesija po korisniku
- invalidacija sesije pri logout-u
- brisanje `JSESSIONID` cookie-ja pri logout-u
- preusmeravanje na login ako je sesija istekla

## 7. REST API za Postman dokumentaciju

Dodat je mali API za knjige:

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

API je dostupan ADMIN korisniku. U Postman-u se prvo radi login preko `/login`, nakon čega
Postman čuva session cookie.

Primer JSON tela:

```json
{
  "title": "Clean Code",
  "isbn": "9780132350884",
  "publicationYear": 2008,
  "categoryId": 2,
  "authorIds": [2]
}
```

## 8. Najvažnije klase

- `SecurityConfig`
- `DataInitializer`
- `BookController`
- `BookService`
- `Book`
- `Category`
- `Author`
- `AppUser`
- `Role`
- `BookRestController`

## 9. Kratak opis tehnologija

### Spring MVC
Obrađuje HTTP zahteve kroz kontrolere i mapiranja kao `@GetMapping` i `@PostMapping`.

### Spring Data JPA
Smanjuje boilerplate za pristup bazi kroz `JpaRepository`.

### Hibernate
ORM implementacija koja mapira Java entitete na MySQL tabele.

### Spring Security
Obezbeđuje login, role, autorizaciju, BCrypt i upravljanje sesijom.

### Thymeleaf
Server-side template engine za generisanje HTML-a.

### MySQL
Relaciona baza u kojoj se čuvaju podaci aplikacije.

### Postman
Koristi se za testiranje i dokumentovanje API zahteva.
