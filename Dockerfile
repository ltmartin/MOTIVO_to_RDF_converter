FROM debian:stable
MAINTAINER Leandro Tabares Martín <ltmartin198@gmail.com>
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y openjdk-17-jdk
RUN mkdir converter
COPY target/MOTIVO_to_RDF_converter-1.0.jar /converter/
COPY src/main/resources/application.properties /converter/
WORKDIR /converter
RUN mkdir data
VOLUME ["/converter/data"]
#CMD ["java", "-jar", "MOTIVO_to_RDF_converter-1.0.jar"]