# 🗿 Juegos

Ce dépôt contient un docker qui lance un serveur multijoueur pour jouer à plusieurs mini-jeux.

## 🎮 Jeux disponibles
- Puissance 4

## En cours de développement
- Morpion
- Bataille
- UNO

## ⚙️ Usage

### Serveur

- Construction du conteneur :
```shell
docker build -t juegos .
```
- Lancer le service :
```shell
docker run -d -p 8000:8000 juegos
```

La console devrait prévenir que le serveur est démarré.

### Clients

// En construction //