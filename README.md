# Gamesbox

This Spring Boot application allows you to keep track of the games you have played. It uses Thymeleaf for the front end, MySQL for the database, and the IGDB API to fetch game data. This service is designed to be self-hosted, making it easy for you to run on your own server.

## Getting Started with Docker

1. Create a Twitch account and generate a **Client Secret & Client ID** (as described in [IGDB API](https://api-docs.igdb.com/?java#getting-started)).
2. Clone the repository.
3. Navigate to the project directory.
4. Build the docker image with:
```bash
docker build . --no-cache -t gamesbox:latest
```

5. You can create docker-compose as presented below:
```yaml
version: '3'
services:
  gamesbox:
    image: gamesbox:latest
    environment:
      - DB_HOST=mysql
      - DB_PORT=3306
      - IGDB_CLIENT=<YOUR CLIENT ID>
      - IGDB_SECRET=<YOUR CLIENT SECRET>
      - DB_USER=root
      - DB_PASSWORD=password
    depends_on:
      - mysql
    ports:
      - "8080:8080"

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_PASSWORD=password
      - MYSQL_USER=gamesboxuser
      - MYSQL_DATABASE=gamesbox
      - MYSQL_ROOT_PASSWORD=password
```
6. Run with:
```bash
docker-compose up
```

## Roadmap
- Add reset password feature
- Add status of the added games
- Improve performance for the game list

## Changelog
### 1.0.0
Initial release of the Gamesbox.

- Integration with IGDB API to fetch game data.
- User interface with Thymeleaf.
- Added user authentication and user registration.
- Multiuser support.
- Added view and add games functionality.


