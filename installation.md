# ⚙️ Installation

[Revenir sur la page principale](./index.md)

## <a name="serveur"></a> Serveur

- Cloner le dépôt : 
```shell
git clone git@github.com:Hugman76/docker-sae203.git
```

- Se déplacer dans le dossier 
```shell
cd docker-sae203
```

- Construire le conteneur :
```shell
docker build -t juegos .
```

- Lancer le service :
```shell
docker run -d -p 8000:8000 juegos
```

La console devrait prévenir que le serveur est démarré.

## <a name="client"></a> Client

1. Cloner le dépôt : 
```shell
git clone git@github.com:Hugman76/docker-sae203.git
```

2. Se déplacer dans le dossier 
```shell
cd docker-sae203/java/src
```

3. Lister tous les fichiers Java pour la compilation
    - Sous *Linux*
```shell
find -name "*.java" > sources.txt
```
    - Sous *Windows*
```shell
dir /s /B *.java > sources.txt
```

4. Compiler tous les fichiers Java :
```shell
javac @sources.txt -encoding UTF-8
```
