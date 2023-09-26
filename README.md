# Transporto kontrolės įrenginio pozicijos stebėjimo platforma
---
Sukurta platforma skirtas transporto kontrolės įrenginio pozicijos stebėjimui.

## Beck-end
---
1. Java SE 17
2. Maven 4.0.0
3. Spring Boot 3.1.2
4. JPA
5. H2 Database
6. PostgreSQL
7. Flywaydb
8. Spring Security
9. Spring Boot Starter Mail
10. Lombok
11. Thymeleaf
---
## Klasės/Ryšiai
---
Programoje naudojamos 3 Entity klasės - Device, Position, User.
Taip pat Role, InfoMessage, PositionDTO

- Device ->  Position (OneToMany)
---
#Papildoma informacija

- Programą paleidžiant naudojant testProfile, naudojama H2 duomenų bazė;
- Programą paleidžiant naudojant default profilį, naudojama PostgreSQL duomenų bazė;
- Vartotojo slaptažodžiai duomenų bazėje šifruojami;
- Vartotojo autentifikacijai naudojamas mail servisas;
- Nuotraukos saugomos bazėje.
---
## Veikimas (REST)
---
### Vartotojo API:
- GET /api/positions/getLast/{deviceId} - gauti paskutinę įrandinio poziciją (deviceId);
  - Atsakymo pavyzdys, naudojant Postman:
    - {
        "deviceId": 1000,
        "date": "2023-08-23T15:06:20.783",
        "latitude": 50.9528066,
        "longitude": 4.597415,
        "speed": 48
    }
- 
- POST /api/positions/add - pridėti naują poziciją (deviceId, latitude, longitude, speed).
  - Atsakymo pavyzdys, naudojant Postman:
    - {
      "error": false,
      "message": "Position created successfully"
    }
---


