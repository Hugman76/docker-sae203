# 🐳 Explication du Dockerfile

[Revenir sur la page principale](./index.md)

Vous pourrez trouver le contenu entier du Dockerfile sur
[le dépôt GitHub](https://github.com/Hugman76/docker-sae203/blob/juegos/Dockerfile).

## Ligne par ligne

Dans cette section, nous expliquons le dockerfile ligne par ligne.

---

```dockerfile
FROM debian:latest
```
On utilise la dernière version de Debian comme base pour l'exécution du docker.

---

```dockerfile
RUN apt-get update && \
    apt-get -y install  \
    apache2 \
    mariadb-server \
    mariadb-client \
    openjdk-17-jdk
```
On met à jour les librairies du système, puis on installe Apache, MariaDB (serveur et client) ainsi que la version
17 de Java, d'après le OpenJDK

---

```dockerfile
RUN mkdir /data
RUN mkdir /java

COPY ./start-script.sh /root/
COPY ./data /data
COPY ./java/src/juegos/common java/src/juegos/common
COPY ./java/src/juegos/server java/src/juegos/server
```
Ensuite, on crée les dossiers dans lesquels seront stockés le code Java et les autres fichiers utilisés par nos
algorithmes.

Puis, on copie tous nos fichiers sur le conteneur. Le script de démarrage `start-script.sh`, que le conteneur va lancer
dès son démarrage. On copie le dossier `data`, qui contient les fichiers de données, comme les bases de données. 
Enfin, on copie la partie commune et la partie serveur du code Java.

---

```dockerfile
EXPOSE 3306
EXPOSE 80
EXPOSE 8000
```
On définit les ports utilisés par le serveur : 
- 3306 pour la base de données MySQL.
- 80 pour Apache.
- 8000 pour le serveur du programme Java.

---

```dockerfile
ADD java/lib/mysql-connector.jar /srv/app/mysql-connector.jar
ENV CLASSPATH=/srv/app/mysql-connector.jar:${CLASSPATH}
```
On ajoute la librairie `mysql-connector.jar` dans le conteneur, et on l'ajoute dans la variable d'environnement. 
Cette librairie est nécessaire pour utiliser la base de données dans notre code Java.

---

```dockerfile
RUN chmod +x /root/start-script.sh 
```
On rend le script de démarrage executable.

---

```dockerfile
WORKDIR /java

RUN find -name "*.java" > sources.txt
RUN javac @sources.txt -encoding UTF-8

WORKDIR ../
```
On déplace notre script docker dans le dossier `java`. On crée un fichier `sources.txt` qui contient la liste des
fichiers
Java (donc il n'y aura que la partie commune et la partie serveur de notre code originale). Ensuite, on compile tous
ces fichiers à l'aide de ce fichier. Enfin, on re-déplace le script docker à la racine du conteneur.

---

```dockerfile
CMD ["/bin/bash", "/root/start-script.sh"]
```
Enfin, on définit le script de démarrage qui sera éxecuté lorsque le conteneur va commencer à tourner (et non quand 
il sera construit).

---

`start-script.sh`:
```shell
service mariadb start
mysql < /data/bases/create_db.sql
cd java/src
java juegos/server/JuegosServer
```
Le script démarre le service MariaDB, lance la base de données de notre dossier `data`, puis lance le serveur Java.

---