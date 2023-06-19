# Mediscreen History Notes

## Description
Ce projet est une application Java Spring Boot développée à l'aide de Maven. Il constutue un des trois micro-services backend composants l'application Mediscreen. Ce service propose une API permettant de gérer les notes des patients de l'application. Elle doit être couplée avec une base de donnée NoSql MongoDB.

## Configuration requise

- [Mediscreen Democraphic](https://github.com/AimeMalcorps/P9_Mediscreen_demographic/tree/DEV)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 4.0.0](https://maven.apache.org/download.cgi)

## Installation

1. Clonez le projet depuis le référentiel Git :

``` bash
git clone https://github.com/AimeMalcorps/P9_Mediscreen_history.git
```

2. Accédez au répertoire du projet :

``` bash
cd P9_Mediscreen_history
```

3. Exécutez la commande Maven pour construire le projet :

``` bash
mvn clean install
```

## Utilisation

1. Exécutez l'application en utilisant la commande Maven :

``` bash
mvn spring-boot:run
```

2. Requêtez à l'application à l'adresse suivante :

```
http://localhost:8082
```

## Dockerisé

1. Build image

```bash
docker image build -t history_notes .
```

2. Run container

```bash
docker container run --name history_notes -p 8082:8082 -d history_notes
```

3. Requêtez à l'application à l'adresse suivante :

```bash
http://localhost:8082
```

## Auteurs
Aimé Malcorps
