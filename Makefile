.PHONY: run build test image

run:


build:


docker:
	sudo docker-compose up -d

image:
	./gradlew clean :service:eureka:jibDockerBuild
	./gradlew clean :service:gateway:gateway_server:jibDockerBuild
	./gradlew clean :service:user:user_server:jibDockerBuild
	./gradlew clean :service:auth:jibDockerBuild
	./gradlew clean :service:delivery:delivery_server:jibDockerBuild
	./gradlew clean :service:company:company_server:jibDockerBuild
	./gradlew clean :service:hub:hub-service:jibDockerBuild
	./gradlew clean :service:order:order_server:jibDockerBuild