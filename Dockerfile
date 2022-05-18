FROM debian:latest

RUN apt-get update && \
    apt-get -y install  \
    apache2 \
    mariadb-server \
    mariadb-client \
    default-jdk

RUN mkdir /data
RUN mkdir /java

# Copie des fichiers
COPY ./start-script.sh /root/
COPY ./data /data
COPY ./java/server /java

# Exposer Apache
EXPOSE 3306
EXPOSE 80

CMD ["service", "mariadb", "start"]
CMD ["mysql", "<", "/data/create_db.sql"]

# Déplacement dans le dossier java
WORKDIR /java
# On trouve puis compile tous les fichiers Java
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt -encoding UTF-8

CMD ["java", "-cp", "./lib/mariadb-connector.jar:.", "JuegosServer"]