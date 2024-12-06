# Makefile to simplify running the Spring Boot app

run:
	./mvnw spring-boot:run

build:
	./mvnw clean install

test:
	./mvnw test

startmem:
	brew services start memcached

stopmem:
	brew services stop memcached
