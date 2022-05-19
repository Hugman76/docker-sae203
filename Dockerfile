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
COPY java/src/juegos/common java/src/juegos/common
COPY java/src/juegos/server java/src/juegos/server

# Exposer Apache
EXPOSE 3306
EXPOSE 80

#Configuration de l'environnement CLASSPATH
ADD java/lib/mysql-connector.jar /srv/app/mysql-connector.jar
ENV CLASSPATH=/srv/app/mysql-connector.jar:${CLASSPATH}

#Ajout des droits d'execution pour le script de démarrage
RUN chmod +x /root/start-script.sh 

# Déplacement dans le dossier java
WORKDIR /java

# On trouve puis compile tous les fichiers Java
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt -encoding UTF-8

WORKDIR ../

#Configuration de la commande d'execution
CMD ["/bin/bash", "/root/start-script.sh"]