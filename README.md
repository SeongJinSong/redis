## 환경설정

## 스프링 서비스 배포

- gradlew build

## 환경 설정을 위한 yml 파일

### spring-server-cluster.yml

```
version: "3.0"
services:
  ins1:
    build:
      context: ./
      dockerfile: Dockerfile1
    ports:
      - 9001:9001
  ins2:
    build:
      context: ./
      dockerfile: Dockerfile2
    ports:
      - 9002:9002

```

### redis-replica.yml

```
version: '2'

networks:
  app-tier:
    driver: bridge

services:
  redis:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=master
      - ALLOW_EMPTY_PASSWORD=yes
    networks:
      - app-tier
    ports:
      - 6379:6379
  redis-slave-1:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=slave
      - REDIS_MASTER_HOST=redis
      - ALLOW_EMPTY_PASSWORD=yes
    ports:
      - 6479:6379
    depends_on:
      - redis
    networks:
      - app-tier
  redis-slave-2:
    image: 'bitnami/redis:latest'
    environment:
      - REDIS_REPLICATION_MODE=slave
      - REDIS_MASTER_HOST=redis
      - ALLOW_EMPTY_PASSWORD=yes
    ports:
      - 6579:6379
    depends_on:
      - redis
    networks:
      - app-tier

```

### Dockerfile1

```
FROM openjdk:11
ENV SERVER_PORT=9001
ENV DB_URL='jdbc:h2:tcp://192.168.151.41/~/redis'
ENV REDIS_URL='192.168.151.42'
ENV REDIS_PORT=6379
RUN mkdir /app
WORKDIR /app

COPY redis-0.0.1-SNAPSHOT.jar /app

CMD [ "java", "-jar" ,"/app/redis-0.0.1-SNAPSHOT.jar" ]
```

### Dockerfile2

```
FROM openjdk:11
ENV SERVER_PORT=9002
ENV DB_URL='jdbc:h2:tcp://192.168.151.41/~/redis'
ENV REDIS_URL='192.168.151.42'
ENV REDIS_PORT=6379
RUN mkdir /app
WORKDIR /app

COPY redis-0.0.1-SNAPSHOT.jar /app

CMD [ "java", "-jar" ,"/app/redis-0.0.1-SNAPSHOT.jar" ]
```
