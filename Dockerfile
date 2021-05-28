FROM centos
MAINTAINER Leandro Tabares Martín <ltmartin198@gmail.com>
RUN yum update -y && yum upgrade -y
RUN yum install -y java-11-openjdk.x86_64
RUN mkdir converter
COPY target/MOTIVO_to_RDF_converter-1.0.jar /converter/
COPY src/main/resources/application.properties /converter/
WORKDIR /converter