#
# Docker that hosts a microservice based on Dropwizard
# that checks which servers are up & running
#

FROM java:7

ADD target/dropangular-0.0.1-SNAPSHOT.jar /app/dropangular.jar
ADD configuration.yml /app/configuration.yml

WORKDIR /app

CMD ["java", "-jar", "/app/dropangular.jar", "server", "configuration.yml"]


# docker run -it --rm -p 8080:8080 ipedrazas/dropangular:latest
