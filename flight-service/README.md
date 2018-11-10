# Flight reservation service

Application to reserve flights.
Information about aircrafts, customers and routes is stored into the database.
Customer makes a flightReservation of the specific route as well as aircraft due to the route and aircraft are connected with each other by `One-to-Many` relationship.

## Technologies
* Java 8
* Spring Boot 2
* Apache Maven
* Swagger UI
* ModelMapper

## oAuth 2 authentication
This application uses oAuth 2 with JWT encryption.
To generate .jks file `keytool -genkeypair -alias jwt -keyalg RSA -keypass secretKey -keystore jwt.jks -storepass secretKey` command should be performed.
<br/>
`secretKey` is a password in this case.