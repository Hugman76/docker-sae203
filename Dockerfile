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

#Configuration de l'environnement CLASSPATH
ADD     ./java/server/mysql-connector.jar /srv/app/mysql-connector.jar
ENV CLASSPATH=/srv/app/mysql-connector.jar:${CLASSPATH}

#Ajout des droits d'execution pour le script de démarrage
RUN chmod +x /root/start-script.sh 

# Déplacement dans le dossier java
WORKDIR /java

# On trouve puis compile tous les fichiers Java
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt -encoding UTF-8

#Configuration de la commande d'execution
CMD ["/bin/bash", "/root/start-script.sh"]