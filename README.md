# IP Info API

This project is a Dockerized Spring REST API that runs together with a MySQL database using Docker Compose. This project is not related with ipinfo.io but it does use its services.
<br/>
<br/>
You can access the live website at https://ipdetails.dcism.org/

## Requirements

- Docker
- Docker Compose

## Running the Project

This repository has been setup specifically for local testing. So all you should do is run:

```bash
docker compose up --build
```

from the root directory.
<br><br>
This command will:

- Build the Docker image for the API
- Start the API service
- Start the MySQL database service

Once running, the API will be accessible at:

```
http://localhost:20179
```

However if you encounter any trouble, particularly with this error during `docker compose up --build`:

```
=> ERROR [3/3] COPY target/*.jar app.jar
```

That's because the .yaml file is unable to find the .jar file that already has the built project. This shouldn't be a problem normally, since I also included the `/target` directory in the repository. To solve this issue you would need to rebuild the project using the command:

```
./mvnw clean package -DskipTests
```

Although, this would require you to have (at minimum) Java 17 and Maven installed in your local machine. If you have any issues, please feel free to leave an issue in my repository.

## Environment Variables

The application requires a `.env` file in the root directory for database configuration.

Create a file named `.env` and add the following:

```env
DB_URL=jdbc:mysql://mysql:3306/testdb
DB_USERNAME=jorj
DB_PASSWORD=2004george
```

These credentials are intended for local testing only.

## API Endpoints

### Auth Controller

#### POST `/api/login`

Logs in a user.

#### POST `/api/register`

Registers a new user.

**Request Body**

```json
{
  "email": "email@email.com",
  "password": "password"
}
```

---

### Health Controller

#### GET `/api/health`

Returns a string indicating the API is running.

**Response**

```
API is healthy
```

---

### IPInfo Controller

#### GET `/api/ipinfo`

Fetches information about the current user's IP address.

#### GET `/api/ipinfo/search/{ip_address}`

Fetches information about a specific IP address.

**Example**

```
/api/ipinfo/search/8.8.8.8
```

---

### History Controller

#### GET `/api/history`

Fetches the user's IP address search history.

#### DELETE `/api/history`

Deletes the user's IP address search history.

## Issues that you might encounter

- If in the frontend, you encounter any CORS-related issues, head on over to `src/main/java/com/georgesalise/apiRepo/config/CorsConfig.java` and add the origins used by the frontend. There's a comment I left there telling you where to put it
- When running the dockerized project with the frontend, you might see this error message:

```
not-null property references a null or transient value for entity com.georgesalise.apiRepo.api.model.IPInfo.city
```

- This happens because both the frontend and backend are running locally. The backend, running inside a Docker container, sees requests coming from the Docker bridge network IP (typically `172.18.0.1`), which is a <b>private IP address in the `172.16.0.0/12` range</b>. IP geolocation services cannot provide location data (city, country, etc.) for private IP addresses, resulting in `null` values that violate the database's `NOT NULL` constraint on the `city` field.

## Notes

- The API and MySQL database run as separate Docker services.
- The database hostname is `mysql`, as defined in `docker-compose.yml`.
- Regarding `compose.yaml`, there are notes that I left there that should help with running the project
- If you're done running the project, you can close it by running: `docker compose down`

## Java and Framework Information

This project is developed using **Java 17** and **Maven**, built on top of the **Spring Boot Framework**.

The API uses Spring Boot to provide a RESTful backend service, with support for database integration, authentication, and migrations.

---

## Technologies Used

- **Java 17**
- **Maven**
- **Spring Boot 4.0.2**
- **Spring Web (MVC + WebFlux)**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **Flyway Database Migration**
- **JWT Authentication (JJWT library)**
- **Lombok (for reducing boilerplate code)**

---

## Dependency Breakdown

The main dependencies included in this project are:

### Spring Boot Starters

- `spring-boot-starter-webmvc`
  Provides the core REST API features using Spring MVC (controllers, request mappings, JSON responses).

- `spring-boot-starter-webflux`
  Adds support for reactive programming and non-blocking web features.
  (Note: this is optional unless the project explicitly uses reactive endpoints.)

- `spring-boot-starter-security`
  Provides authentication and authorization features, used for securing API endpoints.

- `spring-boot-starter-data-jpa`
  Enables database access using JPA/Hibernate with repository support.

---

### Database and Migrations

- `mysql-connector-j`
  MySQL JDBC driver required to connect the Spring Boot application to the MySQL database.

- `spring-boot-starter-flyway` and `flyway-mysql`
  Used for database version control and automatic schema migrations.

Flyway ensures the database schema is automatically created and updated when the application starts, based on migration scripts located in:

```
src/main/resources/db/migration
```

---

### JWT Authentication

This project uses the **JJWT** library for JSON Web Token authentication:

- `jjwt-api`
- `jjwt-impl`
- `jjwt-jackson`

These dependencies allow the API to generate, sign, and validate JWT tokens for user authentication.

---

### Lombok

- `lombok`
  Used to reduce boilerplate code such as getters, setters, constructors, and builders.

Lombok is excluded from the final build output since it is only needed at compile-time.

---

### Testing Dependencies

The project also includes Spring Boot test starter packages for:

- JPA testing
- Flyway testing
- Spring Security testing
- Spring Web MVC testing

These dependencies support unit and integration testing using the Spring Boot testing framework.

## Maven Configuration

The project is built using the Maven compiler plugin, configured to compile using Java 17:

```xml
<java.version>17</java.version>
```

The Spring Boot Maven plugin is used to package the application into an executable JAR file.

## Closing remarks

I had a lot of fun making this project, it helped me strengthen my skills in web development and also because I was working on a topic that's close to my area of expertise which is networking.
