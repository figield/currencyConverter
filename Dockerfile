FROM openjdk:8-jdk-alpine

WORKDIR /opt
ADD build/libs/currencyExchangeService-0.1.1.jar currencyExchangeService.jar
CMD java -jar currencyExchangeService.jar

EXPOSE 8080

